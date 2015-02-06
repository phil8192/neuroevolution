package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.Mutator;
import net.parasec.neuroevolution.genetic.Individual;

import net.parasec.neuroevolution.util.MathUtil;
import net.parasec.neuroevolution.util.RandomUtil;

import net.parasec.neuroevolution.network.Edge;

import java.util.Random;
import java.util.Stack;


public final class FixedTopoMultipleMutatorOneOff 
    implements Mutator<FixedTopo> {

  private final Random prng;


  public FixedTopoMultipleMutatorOneOff(final Random prng) {
    this.prng = prng;
  }

  public void mutate(final FixedTopo t) {

    final int len = 1+prng.nextInt(t.size());
    final int[] index = RandomUtil.sequence(len, prng); 

    for(int i = 0; i < len; i++) {
      final Edge e = t.getEdge(index[i]);
      final double weight = e.getWeight();
      e.setWeight(RandomUtil.getRandom(prng, weight-1, weight+1));
    }

  }

  public void revert(final FixedTopo t) {} // todo: remove

}

