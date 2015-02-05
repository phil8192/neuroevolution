package net.parasec.neuroevolution.network;

import net.parasec.neuroevolution.util.MathUtil;
import net.parasec.neuroevolution.util.RandomUtil;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public final class NetworkBuilder {

  private static final AtomicInteger nodeId = new AtomicInteger(); 

  /**
   * initialise incoming edges from bias + all input nodes to a neuron
   */ 
  private static Edge[] createIncomingEdges(final Node[] in, final Node bias,
      final double minr, final double maxr, final Random prng) {

    final int incoming = in.length;
    final Edge[] incomingEdges = new Edge[incoming+1];
    // bias
    incomingEdges[0] = new Edge(bias, RandomUtil.getRandom(prng, minr, maxr));

    // incomming edges from all nodes in previous layer
    for(int i = 0; i < incoming; i++)
      incomingEdges[i+1] 
          = new Edge(in[i], RandomUtil.getRandom(prng, minr, maxr));

    return incomingEdges;
  }

  /**
   * create a layer and wire up to previous (Node[] in) layer
   */     
  private static TransferNode[] createLayer(final int nodes, final Node[] in,
      final Node bias, final double minr, final double maxr, 
      final Random prng) {

    final TransferNode[] layer = new TransferNode[nodes];
   
    // add nodes to this layer 
    for(int i = 0; i < nodes; i++)
      layer[i] = new TransferNode(Integer.toString(nodeId.incrementAndGet()),
          createIncomingEdges(in, bias, minr, maxr, prng), new Sigmoid());
    
    return layer;
  }

  /**
   * construct a fully connected feed-forward network based on the layer-nodes
   * convention. for example, 4-6-3 = a fully connected neural network with 4
   * input nodes, 6 hidden nodes and 3 output nodes.
   *
   * @param scheme the network scheme. e.g., [4,6,3]
   * @param minr   the minimum random weight for initial assignment
   * @param maxr   the maximum random weight for initial assignment
   * @param prng   the random number generator to be use.
   *
   * @return @see Network
   */ 
  public static Network buildFeedForward(final int[] scheme, final double minr,
      final double maxr, final Random prng) {

    // bias node is connected to all other nodes.
    final Node bias = new Node(Integer.toString(nodeId.incrementAndGet()), 1);

    // input layer
    final int inputNodes = scheme[0];
    final Node[] in = new Node[inputNodes]; 
    for(int i = 0; i < inputNodes; i++)
      in[i] = new Node(Integer.toString(nodeId.incrementAndGet()));
    
    // hidden layers
    final ArrayList<TransferNode> hidden = new ArrayList<TransferNode>();
    Node[] last = in; // ref. 
    for(int i = 1, len = scheme.length-1; i < len; i++) {
      final TransferNode[] layer 
          = createLayer(scheme[i], last, bias, minr, maxr, prng);
      hidden.addAll(Arrays.asList(layer));
      last = layer;
    }

    // output layer
    final TransferNode[] output 
        = createLayer(scheme[scheme.length-1], last, bias, minr, maxr, prng);

    return new Network(bias, in, hidden, output);
  }

}

