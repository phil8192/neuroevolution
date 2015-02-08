package net.parasec.neuroevolution.genetic;

public final class PopulationFitness {

  private final double min, max, avg, sum;  
  private final String minReport, maxReport;

  
  public PopulationFitness(final double min, final double max, final double avg,
      final double sum) {
    this(min, max, avg, sum, null, null);
  }

  public PopulationFitness(final double min, final double max, final double avg, 
      final double sum, final String minReport, final String maxReport) {
    this.min = min;
    this.max = max;
    this.avg = avg;
    this.sum = sum;
    this.minReport = minReport;
    this.maxReport = maxReport;
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

  public String getMinReport() {
    return minReport;
  }

  public String getMaxReport() {
    return maxReport;
  }

}

