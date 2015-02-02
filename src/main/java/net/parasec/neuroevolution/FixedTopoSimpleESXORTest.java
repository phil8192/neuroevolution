package net.parasec.neuroevolution;

import net.parasec.neuroevolution.logging.Logger;

import net.parasec.neuroevolution.util.MathUtil;

import net.parasec.neuroevolution.network.Network;
import net.parasec.neuroevolution.network.TransferNode;
import net.parasec.neuroevolution.network.Sigmoid;
import net.parasec.neuroevolution.network.Edge;
import net.parasec.neuroevolution.network.Node;

import net.parasec.neuroevolution.genetic.SimpleES;
import net.parasec.neuroevolution.genetic.IndividualEvaluator;
import net.parasec.neuroevolution.genetic.Minimisation;
import net.parasec.neuroevolution.genetic.OptDir;

import java.util.ArrayList;
import java.util.Random;


/**
 * optimise the weights of a fixed structure neural network using a simple 
 * 1+1 evolutionary strategy to solve the XOR problem.
 */
public final class FixedTopoSimpleESXORTest {

  private static final Logger LOG = Logger.getLogger(FixedTopoSimpleESXORTest.class);

  
  public static void main(String[] args) {

    // XOR instances
    double[][][] instances = new double[][][] {
      {{0.9, 0.9}, {0.1}},
      {{0.9, 0.1}, {0.9}},
      {{0.1, 0.9}, {0.9}},
      {{0.1, 0.1}, {0.1}}  
    };

    Random prng = new Random();

    // build a fixed network with 2 input nodes, 2 hidden nodes and 1 output 
    // nodes. each node has an incomming bias weight. weights are initialised
    // randomly within a [minr, maxr] interval.
    double minr = -0.1, maxr = 0.1;
    Node bias = new Node("0", 1);
    Node[] in = new Node[] { new Node("1"), new Node("2") };
    ArrayList<TransferNode> hidden = new ArrayList<>();
    hidden.add(new TransferNode("3", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("4", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    TransferNode out =
       new TransferNode("5", new Edge[] {
       new Edge(bias,          MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(0), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(1), MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid());

    Network net = new Network(bias, in, hidden, new TransferNode[]{out});

    // this is an error minimisation problem.
    OptDir optDir = new Minimisation();

    // wrap the network up as an individual
    // for maximisation problem, set initial fitness to
    // -Double.MAX_VALUE
    FixedTopo idv = new FixedTopo(net, optDir);
    
    // custom evaluator
    IndividualEvaluator<FixedTopo> eva = new FixedTopoSupervised(instances); 

    // custom mutator 
    FixedTopoMutator mut = new FixedTopoMutator(prng);    

    // optimise the network using the (1+1)-ES strategy for a maximum of maxit 
    // iterations.
    int maxit = 10000000;
    SimpleES<FixedTopo> ses = new SimpleES<FixedTopo>(idv, eva, mut, optDir);
    ses.optimise(maxit); 

    // output a final run over the instances
    for(int i = 0, len = instances.length; i < len; i++) {
      net.propagate(instances[i][0]);
      LOG.info(String.format("%.4f", out.value()));
    }

  }

}

