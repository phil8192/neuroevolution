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
 * 1+1 evolutionary strategy to classify iris data. 
 */
public final class FixedTopoSimpleESIrisTest {

  private static final Logger LOG = Logger.getLogger(FixedTopoSimpleESIrisTest.class);

  
  public static void main(String[] args) {

    // XOR instances
    double[][][] instances = new double[][][] {
{{5.1,3.5,1.4,0.2},{0.9,0.1,0.1}},
{{4.9,3.0,1.4,0.2},{0.9,0.1,0.1}},
{{4.7,3.2,1.3,0.2},{0.9,0.1,0.1}},
{{4.6,3.1,1.5,0.2},{0.9,0.1,0.1}},
{{5.0,3.6,1.4,0.2},{0.9,0.1,0.1}},
{{5.4,3.9,1.7,0.4},{0.9,0.1,0.1}},
{{4.6,3.4,1.4,0.3},{0.9,0.1,0.1}},
{{5.0,3.4,1.5,0.2},{0.9,0.1,0.1}},
{{4.4,2.9,1.4,0.2},{0.9,0.1,0.1}},
{{4.9,3.1,1.5,0.1},{0.9,0.1,0.1}},
{{5.4,3.7,1.5,0.2},{0.9,0.1,0.1}},
{{4.8,3.4,1.6,0.2},{0.9,0.1,0.1}},
{{4.8,3.0,1.4,0.1},{0.9,0.1,0.1}},
{{4.3,3.0,1.1,0.1},{0.9,0.1,0.1}},
{{5.8,4.0,1.2,0.2},{0.9,0.1,0.1}},
{{5.7,4.4,1.5,0.4},{0.9,0.1,0.1}},
{{5.4,3.9,1.3,0.4},{0.9,0.1,0.1}},
{{5.1,3.5,1.4,0.3},{0.9,0.1,0.1}},
{{5.7,3.8,1.7,0.3},{0.9,0.1,0.1}},
{{5.1,3.8,1.5,0.3},{0.9,0.1,0.1}},
{{5.4,3.4,1.7,0.2},{0.9,0.1,0.1}},
{{5.1,3.7,1.5,0.4},{0.9,0.1,0.1}},
{{4.6,3.6,1.0,0.2},{0.9,0.1,0.1}},
{{5.1,3.3,1.7,0.5},{0.9,0.1,0.1}},
{{4.8,3.4,1.9,0.2},{0.9,0.1,0.1}},
{{5.0,3.0,1.6,0.2},{0.9,0.1,0.1}},
{{5.0,3.4,1.6,0.4},{0.9,0.1,0.1}},
{{5.2,3.5,1.5,0.2},{0.9,0.1,0.1}},
{{5.2,3.4,1.4,0.2},{0.9,0.1,0.1}},
{{4.7,3.2,1.6,0.2},{0.9,0.1,0.1}},
{{4.8,3.1,1.6,0.2},{0.9,0.1,0.1}},
{{5.4,3.4,1.5,0.4},{0.9,0.1,0.1}},
{{5.2,4.1,1.5,0.1},{0.9,0.1,0.1}},
{{5.5,4.2,1.4,0.2},{0.9,0.1,0.1}},
{{4.9,3.1,1.5,0.1},{0.9,0.1,0.1}},
{{5.0,3.2,1.2,0.2},{0.9,0.1,0.1}},
{{5.5,3.5,1.3,0.2},{0.9,0.1,0.1}},
{{4.9,3.1,1.5,0.1},{0.9,0.1,0.1}},
{{4.4,3.0,1.3,0.2},{0.9,0.1,0.1}},
{{5.1,3.4,1.5,0.2},{0.9,0.1,0.1}},
{{5.0,3.5,1.3,0.3},{0.9,0.1,0.1}},
{{4.5,2.3,1.3,0.3},{0.9,0.1,0.1}},
{{4.4,3.2,1.3,0.2},{0.9,0.1,0.1}},
{{5.0,3.5,1.6,0.6},{0.9,0.1,0.1}},
{{5.1,3.8,1.9,0.4},{0.9,0.1,0.1}},
{{4.8,3.0,1.4,0.3},{0.9,0.1,0.1}},
{{5.1,3.8,1.6,0.2},{0.9,0.1,0.1}},
{{4.6,3.2,1.4,0.2},{0.9,0.1,0.1}},
{{5.3,3.7,1.5,0.2},{0.9,0.1,0.1}},
{{5.0,3.3,1.4,0.2},{0.9,0.1,0.1}},
{{7.0,3.2,4.7,1.4},{0.1,0.9,0.1}},
{{6.4,3.2,4.5,1.5},{0.1,0.9,0.1}},
{{6.9,3.1,4.9,1.5},{0.1,0.9,0.1}},
{{5.5,2.3,4.0,1.3},{0.1,0.9,0.1}},
{{6.5,2.8,4.6,1.5},{0.1,0.9,0.1}},
{{5.7,2.8,4.5,1.3},{0.1,0.9,0.1}},
{{6.3,3.3,4.7,1.6},{0.1,0.9,0.1}},
{{4.9,2.4,3.3,1.0},{0.1,0.9,0.1}},
{{6.6,2.9,4.6,1.3},{0.1,0.9,0.1}},
{{5.2,2.7,3.9,1.4},{0.1,0.9,0.1}},
{{5.0,2.0,3.5,1.0},{0.1,0.9,0.1}},
{{5.9,3.0,4.2,1.5},{0.1,0.9,0.1}},
{{6.0,2.2,4.0,1.0},{0.1,0.9,0.1}},
{{6.1,2.9,4.7,1.4},{0.1,0.9,0.1}},
{{5.6,2.9,3.6,1.3},{0.1,0.9,0.1}},
{{6.7,3.1,4.4,1.4},{0.1,0.9,0.1}},
{{5.6,3.0,4.5,1.5},{0.1,0.9,0.1}},
{{5.8,2.7,4.1,1.0},{0.1,0.9,0.1}},
{{6.2,2.2,4.5,1.5},{0.1,0.9,0.1}},
{{5.6,2.5,3.9,1.1},{0.1,0.9,0.1}},
{{5.9,3.2,4.8,1.8},{0.1,0.9,0.1}},
{{6.1,2.8,4.0,1.3},{0.1,0.9,0.1}},
{{6.3,2.5,4.9,1.5},{0.1,0.9,0.1}},
{{6.1,2.8,4.7,1.2},{0.1,0.9,0.1}},
{{6.4,2.9,4.3,1.3},{0.1,0.9,0.1}},
{{6.6,3.0,4.4,1.4},{0.1,0.9,0.1}},
{{6.8,2.8,4.8,1.4},{0.1,0.9,0.1}},
{{6.7,3.0,5.0,1.7},{0.1,0.9,0.1}},
{{6.0,2.9,4.5,1.5},{0.1,0.9,0.1}},
{{5.7,2.6,3.5,1.0},{0.1,0.9,0.1}},
{{5.5,2.4,3.8,1.1},{0.1,0.9,0.1}},
{{5.5,2.4,3.7,1.0},{0.1,0.9,0.1}},
{{5.8,2.7,3.9,1.2},{0.1,0.9,0.1}},
{{6.0,2.7,5.1,1.6},{0.1,0.9,0.1}},
{{5.4,3.0,4.5,1.5},{0.1,0.9,0.1}},
{{6.0,3.4,4.5,1.6},{0.1,0.9,0.1}},
{{6.7,3.1,4.7,1.5},{0.1,0.9,0.1}},
{{6.3,2.3,4.4,1.3},{0.1,0.9,0.1}},
{{5.6,3.0,4.1,1.3},{0.1,0.9,0.1}},
{{5.5,2.5,4.0,1.3},{0.1,0.9,0.1}},
{{5.5,2.6,4.4,1.2},{0.1,0.9,0.1}},
{{6.1,3.0,4.6,1.4},{0.1,0.9,0.1}},
{{5.8,2.6,4.0,1.2},{0.1,0.9,0.1}},
{{5.0,2.3,3.3,1.0},{0.1,0.9,0.1}},
{{5.6,2.7,4.2,1.3},{0.1,0.9,0.1}},
{{5.7,3.0,4.2,1.2},{0.1,0.9,0.1}},
{{5.7,2.9,4.2,1.3},{0.1,0.9,0.1}},
{{6.2,2.9,4.3,1.3},{0.1,0.9,0.1}},
{{5.1,2.5,3.0,1.1},{0.1,0.9,0.1}},
{{5.7,2.8,4.1,1.3},{0.1,0.9,0.1}},
{{6.3,3.3,6.0,2.5},{0.1,0.1,0.9}},
{{5.8,2.7,5.1,1.9},{0.1,0.1,0.9}},
{{7.1,3.0,5.9,2.1},{0.1,0.1,0.9}},
{{6.3,2.9,5.6,1.8},{0.1,0.1,0.9}},
{{6.5,3.0,5.8,2.2},{0.1,0.1,0.9}},
{{7.6,3.0,6.6,2.1},{0.1,0.1,0.9}},
{{4.9,2.5,4.5,1.7},{0.1,0.1,0.9}},
{{7.3,2.9,6.3,1.8},{0.1,0.1,0.9}},
{{6.7,2.5,5.8,1.8},{0.1,0.1,0.9}},
{{7.2,3.6,6.1,2.5},{0.1,0.1,0.9}},
{{6.5,3.2,5.1,2.0},{0.1,0.1,0.9}},
{{6.4,2.7,5.3,1.9},{0.1,0.1,0.9}},
{{6.8,3.0,5.5,2.1},{0.1,0.1,0.9}},
{{5.7,2.5,5.0,2.0},{0.1,0.1,0.9}},
{{5.8,2.8,5.1,2.4},{0.1,0.1,0.9}},
{{6.4,3.2,5.3,2.3},{0.1,0.1,0.9}},
{{6.5,3.0,5.5,1.8},{0.1,0.1,0.9}},
{{7.7,3.8,6.7,2.2},{0.1,0.1,0.9}},
{{7.7,2.6,6.9,2.3},{0.1,0.1,0.9}},
{{6.0,2.2,5.0,1.5},{0.1,0.1,0.9}},
{{6.9,3.2,5.7,2.3},{0.1,0.1,0.9}},
{{5.6,2.8,4.9,2.0},{0.1,0.1,0.9}},
{{7.7,2.8,6.7,2.0},{0.1,0.1,0.9}},
{{6.3,2.7,4.9,1.8},{0.1,0.1,0.9}},
{{6.7,3.3,5.7,2.1},{0.1,0.1,0.9}},
{{7.2,3.2,6.0,1.8},{0.1,0.1,0.9}},
{{6.2,2.8,4.8,1.8},{0.1,0.1,0.9}},
{{6.1,3.0,4.9,1.8},{0.1,0.1,0.9}},
{{6.4,2.8,5.6,2.1},{0.1,0.1,0.9}},
{{7.2,3.0,5.8,1.6},{0.1,0.1,0.9}},
{{7.4,2.8,6.1,1.9},{0.1,0.1,0.9}},
{{7.9,3.8,6.4,2.0},{0.1,0.1,0.9}},
{{6.4,2.8,5.6,2.2},{0.1,0.1,0.9}},
{{6.3,2.8,5.1,1.5},{0.1,0.1,0.9}},
{{6.1,2.6,5.6,1.4},{0.1,0.1,0.9}},
{{7.7,3.0,6.1,2.3},{0.1,0.1,0.9}},
{{6.3,3.4,5.6,2.4},{0.1,0.1,0.9}},
{{6.4,3.1,5.5,1.8},{0.1,0.1,0.9}},
{{6.0,3.0,4.8,1.8},{0.1,0.1,0.9}},
{{6.9,3.1,5.4,2.1},{0.1,0.1,0.9}},
{{6.7,3.1,5.6,2.4},{0.1,0.1,0.9}},
{{6.9,3.1,5.1,2.3},{0.1,0.1,0.9}},
{{5.8,2.7,5.1,1.9},{0.1,0.1,0.9}},
{{6.8,3.2,5.9,2.3},{0.1,0.1,0.9}},
{{6.7,3.3,5.7,2.5},{0.1,0.1,0.9}},
{{6.7,3.0,5.2,2.3},{0.1,0.1,0.9}},
{{6.3,2.5,5.0,1.9},{0.1,0.1,0.9}},
{{6.5,3.0,5.2,2.0},{0.1,0.1,0.9}},
{{6.2,3.4,5.4,2.3},{0.1,0.1,0.9}},
{{5.9,3.0,5.1,1.8},{0.1,0.1,0.9}}
};

    Random prng = new Random();

    double minr = -0.1, maxr = 0.1;

    // 4-6-3
    Node bias = new Node("0", 1);
    Node[] in = new Node[] { new Node("1"), new Node("2"), 
                             new Node("3"), new Node("4") };
    ArrayList<TransferNode> hidden = new ArrayList<>();
    hidden.add(new TransferNode("5", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)), 
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("6", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("7", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("8", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("9", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    hidden.add(new TransferNode("10", new Edge[] {
       new Edge(bias,  MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[0], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[1], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[2], MathUtil.getRandom(prng, minr, maxr)),
       new Edge(in[3], MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid()));
    TransferNode out1 =
       new TransferNode("11", new Edge[] {
       new Edge(bias,          MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(0), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(1), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(2), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(3), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(4), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(5), MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid());
    TransferNode out2 =
       new TransferNode("12", new Edge[] {
       new Edge(bias,          MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(0), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(1), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(2), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(3), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(4), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(5), MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid());
    TransferNode out3 =
       new TransferNode("13", new Edge[] {
       new Edge(bias,          MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(0), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(1), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(2), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(3), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(4), MathUtil.getRandom(prng, minr, maxr)),
       new Edge(hidden.get(5), MathUtil.getRandom(prng, minr, maxr))
    }, new Sigmoid());

    Network net = new Network(bias, in, hidden, new TransferNode[] { 
        out1, out2, out3 
    });

    // wrap the network up as an individual
    // for maximisation problem, set initial fitness to
    // -Double.MAX_VALUE
    FixedTopo idv = new FixedTopo(net, Double.MAX_VALUE);
    
    // custom evaluator
    IndividualEvaluator<FixedTopo> eva = new FixedTopoSupervised(instances); 

    // custom mutator 
    FixedTopoMutator mut = new FixedTopoMutator(prng);    

    // this is an error minimisation problem.
    OptDir optDir = new Minimisation(); 

    // optimise the network using the (1+1)-ES strategy for a maximum of maxit 
    // iterations.
    int maxit = 1000000;
    SimpleES<FixedTopo> ses = new SimpleES<FixedTopo>(idv, eva, mut, optDir);
    ses.optimise(maxit); 

    // output a final run over the instances
    for(int i = 0, len = instances.length; i < len; i++) {
      net.propagate(instances[i][0]);
      LOG.info(String.format("%.4f %.4f %.4f", out1.value(), out2.value(), 
          out3.value()));
    }

  }

}

