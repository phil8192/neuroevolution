package net.parasec.neuroevolution.genetic;


public final class Maximisation implements OptDir {
  
  // &gt; 0 if fitness1 &gt; fitness2
  public double fitDiff(final double fitness1, final double fitness2) {
    return fitness1 - fitness2;
  }

  public Type getType() {
    return Type.MAXIMISATION;
  }

}

