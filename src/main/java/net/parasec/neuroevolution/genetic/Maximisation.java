package net.parasec.neuroevolution.genetic;


public final class Maximisation implements OptDir {
  public boolean improvement(final double candidate, final double comp) {
    return candidate > comp;   
  }
}

