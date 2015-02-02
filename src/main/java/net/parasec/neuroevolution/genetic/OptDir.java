package net.parasec.neuroevolution.genetic;

import java.util.Comparator;


/**
 * optimisation direciton:
 * for maximisation problem true if candidate &gt; comp
 * for minimisation problem true if candidate &lt; comp.
 * 
 * interface allows for thresholding etc.
 */ 
public interface OptDir<T extends Individual> {

  boolean improvement(double candidate, double comp);

  Comparator<T> getComparator();
  
}

