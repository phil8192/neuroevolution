package net.parasec.neuroevolution.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public final class Network {

  private final Node bias;
  private final Node[] in;
  private final ArrayList<TransferNode> hidden;
  private final TransferNode[] out;


  /**
   * construct a network from (pre-wired) input, hidden and output nodes.
   *
   *
   * the arraylist of hidden nodes defines the order in which nodes will be
   * activated.
   *
   * @param in     network input nodes (matching the number of input variables)
   * @param hidden activation ordering of hidden nodes 
   * @param out    activation ordering of output nodes (matching the number of
   *               output variables.
   */
  public Network(final Node bias, final Node[] in, 
      final ArrayList<TransferNode> hidden, final TransferNode[] out) {
    this.bias = bias;
    this.in = in;
    this.hidden = hidden;
    this.out = out;
  }

  /**
   * deep copy constructor.
   *
   * todo: deep copying of the phenotype (the network) is a pain. 
   * neuroevolution makes extensive use of cloning, so get rid of this design
   * and:
   * 1) seperate genotype (graph description) from phenotype (network).
   * 2) make it easy to clone the genotype.
   * 3) introduce a genotype to phenotype mapper.
   *
   * code below is nasty...
   */ 
  public Network(final Network net) {

    final Node bias = net.getBias();
    final Node[] in = net.getInputs();
    final ArrayList<TransferNode> hidden = net.getHidden();
    final TransferNode[] out = net.getOutputs();

    final int inputLen = in.length;
    final int hiddenLen = hidden.size();
    final int outLen = out.length;

    final Node biasCopy = new Node(bias);
    final Node[] inCopy = new Node[inputLen];
    final ArrayList<TransferNode> hiddenCopy 
        = new ArrayList<TransferNode>(hiddenLen);
    final TransferNode[] outCopy = new TransferNode[outLen];

    // ref cloned nodes
    final HashMap<String, Node> clonedRef = new HashMap<String, Node>();
    clonedRef.put(bias.getId(), bias);

    // input layer nodes
    for(int i = 0; i < inputLen; i++)
      inCopy[i] = cloneIncomingNode(in[i], clonedRef); 

    // hidden layer nodes
    for(int i = 0; i < hiddenLen; i++) 
      hiddenCopy.add(i, cloneTransferNode(hidden.get(i), clonedRef));

    // output layer nodes
    for(int i = 0; i < outLen; i++) 
      outCopy[i] = cloneTransferNode(out[i], clonedRef); 

    // now nodes have been cloned, can connect them with cloned edges...
    // (this is in a separate loop since there may be recurrent connections).
    // hidden:
    for(int i = 0; i < hiddenLen; i++)
      hiddenCopy.get(i).setEdges(cloneIncomingEdges(hidden.get(i), clonedRef));

    // output:
    for(int i = 0; i < outLen; i++)
      outCopy[i].setEdges(cloneIncomingEdges(out[i], clonedRef));

    this.bias = biasCopy;
    this.in = inCopy;
    this.hidden = hiddenCopy;
    this.out = outCopy;    
  }

  private Node cloneIncomingNode(final Node n, 
      final HashMap<String, Node> refs) {
    final Node cloned = new Node(n);
    refs.put(cloned.getId(), cloned);
    return cloned; 
  }

  private TransferNode cloneTransferNode(final TransferNode tn,
      final HashMap<String, Node> refs) {
    final String id = tn.getId();
    final double value = tn.value();
    final TransferFunction tf = tn.getTransferFunction().copy();
    final TransferNode cloned = new TransferNode(id, value, null, tf);
    refs.put(id, cloned);
    return cloned;
  }

  private Edge[] cloneIncomingEdges(final TransferNode tn, 
      final HashMap<String, Node> refs) {
    final Edge[] incomingEdges = tn.getIncomingEdges();
    final int incomingEdgesLen = incomingEdges.length;
    final Edge[] incomingEdgesCopy = new Edge[incomingEdgesLen];
    for(int i = 0; i < incomingEdgesLen; i++) {
      final Edge edge = incomingEdges[i];
      final double weight = edge.getWeight();
      final String incomingId = edge.getIncomingNode().getId();
      incomingEdgesCopy[i] = new Edge(refs.get(incomingId), weight);
    }
    return incomingEdgesCopy;
  }

  public void propagate(final double[] x) {
    assert x.length == in.length;
    
    for(int i = 0, len = x.length; i < len; i++) 
      in[i].setValue(x[i]);
    for(final TransferNode tn : hidden) 
      tn.activate();
    for(int i = 0, len = out.length; i < len; i++) 
      out[i].activate();
  }

  public Node getBias() {
    return bias;
  }

  public Node[] getInputs() {
    return in;
  }

  public ArrayList<TransferNode> getHidden() {
    return hidden;
  }

  public TransferNode[] getOutputs() {
    return out;
  } 

  public ArrayList<Edge> getAllEdges() {
    final ArrayList<Edge> weights = new ArrayList<Edge>();
    for(final TransferNode tn : hidden)
      weights.addAll(Arrays.asList(tn.getIncomingEdges()));
    for(int i = 0, len = out.length; i < len; i++)
      weights.addAll(Arrays.asList(out[i].getIncomingEdges()));
    return weights;
  }

  /**
   * example/test. 
   */
  public static void main(String[] args) {

    // basic 2-2-1 feed-foward network using pre-learned weights for xor 
    // problem.
    
    // bias node (connected to all neurons)
    Node bias = new Node("0", 1);
  
    // input layer
    Node[] in = new Node[] { new Node("1"), new Node("2") };

    // hidden layer
    ArrayList<TransferNode> hidden = new ArrayList<>();

    // 1st hidden neuron (inputs from bias and 2 input nodes)
    hidden.add(new TransferNode("3", new Edge[] { 
       new Edge(bias,  2.6184), 
       new Edge(in[0], 2.9041), 
       new Edge(in[1], 2.9042)
    }, new Sigmoid()));

    // 2nd hidden neuron (inputs from bias and 2 input nodes)
    hidden.add(new TransferNode("4", new Edge[] { 
       new Edge(bias,  -3.0541),
       new Edge(in[0],  2.9651), 
       new Edge(in[1],  2.9654)
    }, new Sigmoid()));
  
    // output layer 
    TransferNode out = 
       new TransferNode("5", new Edge[] {
       new Edge(bias,          -2.3282),
       new Edge(hidden.get(0),  5.0174),
       new Edge(hidden.get(1), -5.1333)
    }, new Sigmoid());

    // the network
    Network net = new Network(bias, in, hidden, new TransferNode[]{out});

    // feed-forward input vectors
    net.propagate(new double[]{1,1});  System.out.println(out.value());
    net.propagate(new double[]{1,-1}); System.out.println(out.value());
    net.propagate(new double[]{-1,1}); System.out.println(out.value());
    net.propagate(new double[]{-1,-1}); System.out.println(out.value());

  }

}

