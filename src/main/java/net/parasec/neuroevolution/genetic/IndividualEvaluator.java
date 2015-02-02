package net.parasec.neuroevolution.genetic;


public interface IndividualEvaluator<T extends Individual> {
  
  void eval(T t);
  
}
 
