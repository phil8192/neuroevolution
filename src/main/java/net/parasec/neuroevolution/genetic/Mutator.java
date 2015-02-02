package net.parasec.neuroevolution.genetic;


public interface Mutator<T extends Individual> {

  void mutate(T t);

  void revert(T t);  
}

