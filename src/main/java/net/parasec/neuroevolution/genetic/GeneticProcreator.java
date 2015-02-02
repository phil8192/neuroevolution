package net.parasec.neuroevolution.genetic;

import java.util.Collections;
import java.util.ArrayList;


/**
 * a genetic-procreator: standard select, mate, mutate, population based ga.
 */
public final class GeneticProcreator<T extends Individual> 
    implements Procreator<T> {

  private final Selector<T> s;
  private final Combiner<T> c;
  private final Mutator<T>  m;
  
  private final boolean eliteism;


  public GeneticProcreator(final Selector<T> s, final Combiner<T> c, 
      final Mutator<T> m, final boolean eliteism) {
    this.s = s;
    this.c = c;
    this.m = m;
    this.eliteism = eliteism;
  }
 
  public ArrayList<T> procreate(final ArrayList<T> pop, 
      final PopulationFitness pf) {

    final int popSize = pop.size();
    assert (eliteism && popSize >= 3) || popSize >= 2;

    // next generation (same size)
    final ArrayList<T> nextGeneration 
        = new ArrayList<T>(popSize); 

    // sort the current population because selectors assume ordered collection.
    //Collections.sort(pop, comp);

    // if eliteism, ensure highest fitness individual is in next generation.
    if(eliteism)
      nextGeneration.add(pop.get(0)); 

    while(nextGeneration.size() < popSize) {
      
      // use the selector to select 2 individuals from the current population
      // based on fitness proportionate selection.
      final T parent1 = s.select(pop, pf); 
      final T parent2 = s.select(pop, pf);

      // use the combiner to create (2) new offspring by mating the selected
      // individuals.
      final ArrayList<T> offspring = c.mate(parent1, parent2, 2);

      // offspring are subject to mutation.
      for(final T i : offspring)
        m.mutate(i);

      // add the 2 new individuals to the next generation
      nextGeneration.addAll(offspring);
    }
    return nextGeneration;
  }
  
}

