package net.parasec.neuroevolution.genetic;

public class Individual implements Comparable<Individual> {

  private double fitness;

  
  public Individual(double fitness) {
    this.fitness = fitness;
  }

  public void setFitness(double fitness) {
    this.fitness = fitness;
  }
  
  public double getFitness() {
    return fitness;
  }

  public int compareTo(final Individual i) {
    final double diff = fitness-i.getFitness();
    if(diff < 0) return -1;
    if(diff > 0) return  1;
    return 0;
  }

}
 
