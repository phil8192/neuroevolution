package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;
import java.util.Random;


public class RankSelector<T extends Individual> implements Selector<T> {

  private final Random prng;


  public RankSelector(final Random prng) {
    this.prng = prng;
  }

  public T select(final ArrayList<T> pop, 
      final PopulationFitness pf) {

    final double fitnessSum = pf.getSum(); 
    final double r = prng.nextDouble();
    double nSum = 1;      
    int n = pop.size();
    final int last = n-1;
        
    for(int i = 0; i < last; i++) {

      nSum -= (n/fitnessSum); 
      n--;                

      if(r >= nSum)
        return pop.get(i);
    }
    return pop.get(last);
  }

}

