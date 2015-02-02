package net.parasec.neuroevolution.genetic;


public final class Minimisation implements OptDir {

  // &gt; 0 if fitness1 &lt; fitness2
  public double fitDiff(final double fitness1, final double fitness2) {
    return fitness2 - fitness1;
  }

  public Type getType() {
    return Type.MINIMISATION;
  }

}

