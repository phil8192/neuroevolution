package net.parasec.neuroevolution.genetic;

/**
 * this is actually a workaround for object cloning.
 */
public interface Replicator<T extends Individual> {

  T replicate(T t);
}

