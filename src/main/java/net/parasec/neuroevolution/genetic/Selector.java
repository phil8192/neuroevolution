package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;


public interface Selector<T extends Individual> {

  T select(ArrayList<T> pop, PopulationFitness pf);
  
}

