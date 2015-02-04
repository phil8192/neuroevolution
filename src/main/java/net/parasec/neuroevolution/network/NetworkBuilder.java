package net.parasec.neuroevolution.network;

import java.util.Random;


public final class NetworkBuilder {

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
    return null; // todo 
  }

}
