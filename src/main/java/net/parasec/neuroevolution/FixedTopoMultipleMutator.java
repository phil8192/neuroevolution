package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.Mutator;
import net.parasec.neuroevolution.genetic.Individual;

import net.parasec.neuroevolution.util.MathUtil;
import net.parasec.neuroevolution.util.RandomUtil;

import net.parasec.neuroevolution.network.Edge;

import java.util.Random;
import java.util.Stack;


public class FixedTopoMultipleMutator implements Mutator<FixedTopo> {

  private static final class Last {

    private final int    index;
    private final double value;


    Last(final int index, final double value) {
      this.index = index;
      this.value = value;
    }
 
    public int getIndex() {
      return index;
    }

    public double getValue() {
      return value;
    }
  }

  private final Random prng;

  private final Stack<Last> hist = new Stack<Last>();
 
 
  public FixedTopoMultipleMutator(final Random prng) {
    this.prng = prng;
  }

  public void mutate(final FixedTopo t) {

    hist.clear();

    final int len = 1+prng.nextInt(t.size());
    final int[] index = RandomUtil.sequence(len, prng); 
    for(int i = 0; i < len; i++) {
      final int randomIdx = index[i]; 
      final Edge randomEdge = t.getEdge(randomIdx);
      final double lastValue = randomEdge.getWeight();
      hist.push(new Last(randomIdx, lastValue));
      randomEdge.setWeight(RandomUtil.getRandom(prng, lastValue-1, 
          lastValue+1));
    }
  }

  public void revert(final FixedTopo t) {
    while(!hist.empty()) {
      final Last l = hist.pop();
      t.getEdge(l.getIndex()).setWeight(l.getValue());  
    }
  }

}

