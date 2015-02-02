package net.parasec.neuroevolution.genetic;

import net.parasec.neuroevolution.logging.Logger;

import java.util.Collections;
import java.util.ArrayList;


public final class Population<T extends Individual> {

  private final static Logger LOG = Logger.getLogger(Population.class);

  private ArrayList<T>                 pop; 
  private final PopulationEvaluator<T> e; 
  private final Procreator<T>          p;

 
  public Population(final ArrayList<T> pop, final PopulationEvaluator<T> e, 
      final Procreator<T> p) {
    this.pop = pop;
    this.e   = e;
    this.p   = p;
  }

  /**
   * perform a single round of evalution, procreatation 
   */ 
  public PopulationFitness epoch() {

    //LOG.info("evaluating " + pop.size() + " individuals");
    final PopulationFitness pf = e.eval(pop);

    Collections.sort(pop);
  
    //LOG.info("procreating");
    pop = p.procreate(pop, pf); 

    return pf;
  }

}

