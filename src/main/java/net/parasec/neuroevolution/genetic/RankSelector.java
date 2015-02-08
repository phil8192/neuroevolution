package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;
import java.util.Random;


public class RankSelector<T extends Individual> implements Selector<T> {

  private final Random prng;


  public RankSelector(final Random prng) {
    this.prng = prng;
  }

  /**
   * use rank selection to select an individual from the population.
   * 
   * the probability of selecting an individual with rank Rnk from a population 
   * of size N is equal to:
   * 
   * Rnk/((N*(N+1))/2)
   *
   * in R, this can be implemented as:
   *
   * individual &lt;- population[sample(rep(1:Rnk, Rnk:1), 1)]
   *
   * here, a random integer I is generated 0 &lte; I &lt; ((N*(N+1))/2).
   * each rank is allocated Rnk number of possible integers such that the rank
   * maps to a segment of a routlette wheel where the segment size is 
   * proportionate to the probability of selection:
   *
   * rank 4 = [6, 7, 8, 9] selection prob = r &gte; 10-4
   * rank 3 = [3, 4, 5]    selection prob = r &gte; ..6-3
   * rank 2 = [1, 2]       selection prob = r &gte; ....3-2
   * rank 1 = [0]          selection prob = r &gte; ......1-1.
   *
   * an alternative implementation could pre-compute a roulette wheel, in the 
   * same way as the R version: rep(1:4, 4:1) = [1 1 1 1 2 2 2 3 3 4] such that
   * each position maps to the rank index, then select any value at random.
   *
   * @param pop the population of individuals pre-sorted by fitness in ascending 
   *            order, such that the fittest individual is in position 1.
   * @param pf  the population fitness (this is not used in rank selection)
   * @return    an individual
   */ 
  public T select(final ArrayList<T> pop, final PopulationFitness pf) {
  
    final int N = pop.size();
    int n = (N*(N+1))/2;
    final int r = prng.nextInt(n);

    for(int i = N, j = n-N; i > 0; i--, j -= i)
      if(r >= j) return pop.get(N-i);
      
    assert false; 
    return null;
  }

}

