package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;


public interface Combiner<T extends Individual> {

  ArrayList<T> mate(T parent1, T parent2, int offspring);
  
}

