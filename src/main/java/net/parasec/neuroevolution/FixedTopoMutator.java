package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.Mutator;
import net.parasec.neuroevolution.genetic.Individual;

import net.parasec.neuroevolution.util.MathUtil;

import net.parasec.neuroevolution.network.Edge;

import java.util.Random;


public class FixedTopoMutator implements Mutator<FixedTopo> {

  private final Random prng;

  private int lastIdx;
  private double lastValue;

  
  public FixedTopoMutator(final Random prng) {
    this.prng = prng;
  }

  public void mutate(final FixedTopo t) {

    final int randomIdx = prng.nextInt(t.size());
    final Edge randomEdge = t.getEdge(randomIdx);

    lastIdx = randomIdx;
    lastValue = randomEdge.getWeight();

    randomEdge.setWeight(MathUtil.getRandom(prng, lastValue-1, lastValue+1)); 
    //randomEdge.setWeight(lastValue + prng.nextGaussian()); 
  }

  public void revert(final FixedTopo t) {
    t.getEdge(lastIdx).setWeight(lastValue);  
  }

}

