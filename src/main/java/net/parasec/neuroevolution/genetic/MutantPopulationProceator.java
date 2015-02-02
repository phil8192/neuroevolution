package net.parasec.neuroevolution.genetic;

import net.parasec.neuroevolution.logging.Logger;

import java.util.Collections;
import java.util.ArrayList;


/**
 * mutation only ga. 
 */
public final class MutantPopulationProceator<T extends Individual>
    implements Procreator<T> {

  private final static Logger LOG 
      = Logger.getLogger(MutantPopulationProceator.class);

  private final Selector<T>   s;
  private final Replicator<T> r;
  private final Mutator<T>    m;
  
  private final boolean eliteism;


  public MutantPopulationProceator(
      final Selector<T>   s, 
      final Replicator<T> r,
      final Mutator<T>    m, 
      final boolean eliteism) {
    this.s = s;
    this.r = r;
    this.m = m;
    this.eliteism = eliteism;
  }
 
  public ArrayList<T> procreate(final ArrayList<T> pop, 
      final PopulationFitness pf) {

    final int popSize = pop.size();

    final ArrayList<T> nextGeneration 
        = new ArrayList<T>(popSize); 

    //Collections.sort(pop);
    //LOG.info(String.format("fitest = %.4f weakest = %.4f", 
    //    pop.get(0).getFitness(), pop.get(pop.size()-1).getFitness()));

    if(eliteism)
      nextGeneration.add(pop.get(0)); 

    while(nextGeneration.size() < popSize) {

      final T parent = s.select(pop, pf); 
      final T c = r.replicate(parent);
      m.mutate(c);

      nextGeneration.add(c);
    }
    return nextGeneration;
  }
  
}

