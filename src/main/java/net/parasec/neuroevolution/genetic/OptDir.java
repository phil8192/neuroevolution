package net.parasec.neuroevolution.genetic;

/**
 * always a maximisation problem...
 * &gt; 0 if fitness1 is better than fitness2.
 */
public interface OptDir {

  enum Type {
    MINIMISATION,
    MAXIMISATION
  }  

  double fitDiff(double fitness1, double fitness2);
  
  Type getType();

}

