package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.IndividualEvaluator;
import net.parasec.neuroevolution.genetic.PopulationEvaluator;
import net.parasec.neuroevolution.genetic.PopulationFitness;

import java.util.ArrayList;


public class FixedTopoPopSupervised implements PopulationEvaluator<FixedTopo> {

  private final IndividualEvaluator<FixedTopo> ie; 

  public FixedTopoPopSupervised(final IndividualEvaluator<FixedTopo> ie) {
    this.ie = ie;
  }

  public PopulationFitness eval(final ArrayList<FixedTopo> pop) {

    double min = Double.MAX_VALUE, max = -Double.MIN_VALUE, sum = 0;    

    for(final FixedTopo ft : pop) {
      
      ie.eval(ft);

      final double fitness = ft.getFitness();

      if(fitness < min) min = fitness;
      if(fitness > max) max = fitness;

      sum += fitness;     
 
    }
    return new PopulationFitness(min, max, sum/pop.size(), sum);   
  }
  
}

