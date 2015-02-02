package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;


public interface PopulationEvaluator<T extends Individual> {
  
  PopulationFitness eval(ArrayList<T> pop);
  
}
 
