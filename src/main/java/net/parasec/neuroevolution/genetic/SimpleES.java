package net.parasec.neuroevolution.genetic;

import net.parasec.neuroevolution.logging.Logger;

/**
 * (1 + 1)-ES. 
 *
 * see http://en.wikipedia.org/wiki/Evolution_strategy:
 * "The simplest evolution strategy operates on a population of size two: the 
 *  current point (parent) and the result of its mutation. Only if the mutant's 
 *  fitness is at least as good as the parent one, it becomes the parent of the 
 *  next generation. Otherwise the mutant is disregarded.
 */
public final class SimpleES<T extends Individual> {

  private static final Logger LOG = Logger.getLogger(SimpleES.class);

  private final T idv;
  private final IndividualEvaluator<T> ie;
  private final Mutator<T> m;
  private final OptDir optDir; 
 

  public SimpleES(final T idv, final IndividualEvaluator<T> ie, 
      final Mutator<T> m, final OptDir optDir) {
    this.idv = idv;
    this.ie = ie;
    this.m = m;
    this.optDir = optDir;
  }
 
  public void optimise(final int max) {
    double best = idv.getFitness();
    for(int i = 0; i < max; i++) {
      m.mutate(idv);     
      ie.eval(idv);    
      final double fitness = idv.getFitness();
      if(!optDir.improvement(fitness, best)) {
        m.revert(idv);
        idv.setFitness(best);
      } else {
        best = fitness;
        LOG.info(String.format("%010d %.6f", i, best));
      }
    }
  }
}

