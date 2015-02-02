package net.parasec.neuroevolution.genetic;


public class Individual implements Comparable<Individual> {

  private double fitness;
  private final OptDir optDir;

  
  public Individual(final double fitness, final OptDir optDir) {
    this.fitness = fitness;
    this.optDir = optDir;
  }

  public Individual(final OptDir optDir) {
    this(optDir.getType().equals(OptDir.Type.MINIMISATION) 
        ? Double.MAX_VALUE : -Double.MAX_VALUE, optDir);
  }

  public void setFitness(double fitness) {
    this.fitness = fitness;
  }
  
  public double getFitness() {
    return fitness;
  }

  public OptDir getOptDir() {
    return optDir;
  }

  public int compareTo(final Individual other) {
    final double diff = optDir.fitDiff(this.fitness, other.getFitness());
    if(diff > 0) return -1; // fitter (we will be sorted in ascending order.
    if(diff < 0) return  1;
    return 0;
  }

}
 
