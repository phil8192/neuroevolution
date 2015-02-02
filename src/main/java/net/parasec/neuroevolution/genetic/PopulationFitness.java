package net.parasec.neuroevolution.genetic;

public final class PopulationFitness {

  private final double min, max, avg, sum;  

  
  public PopulationFitness(final double min, final double max, final double avg, 
      final double sum) {
    this.min = min;
    this.max = max;
    this.avg = avg;
    this.sum = sum;
  }

  public double getMin() {
    return min;
  }

  public double getMax() {
    return max;
  }

  public double getAvg() {
    return avg;
  }

  public double getSum() {
    return sum;
  }

}

