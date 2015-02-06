package net.parasec.neuroevolution.genetic;


public interface Mutator<T extends Individual> {

  void mutate(T t);

  // todo: make "ReversibleMutator" interface
  void revert(T t);  
}

