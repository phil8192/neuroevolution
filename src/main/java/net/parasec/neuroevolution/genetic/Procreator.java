package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;


public interface Procreator<T extends Individual> {

  ArrayList<T> procreate(ArrayList<T> pop, PopulationFitness pf);

}

