package net.parasec.neuroevolution.genetic;

import net.parasec.neuroevolution.logging.Logger;

import java.util.List;
import java.util.LinkedList;


/**
 * population based evolution.
 */
public final class Evolution {

  private final static Logger LOG = Logger.getLogger(Evolution.class);


  public static List<PopulationFitness> evolve(final Population pop, 
      final int epochs) {

    final List<PopulationFitness> fitnessHistory 
        = new LinkedList<PopulationFitness>();

    for(int i = 1; i <= epochs; i++) {

      final PopulationFitness pf = pop.epoch();

      LOG.info("epoch " + String.format("%010d", i) +
          " complete. fitness min|max|avg = " +
          String.format("%.4f", pf.getMin()) + " | " +
          String.format("%.4f", pf.getMax()) + " | " +
          String.format("%.4f", pf.getAvg()));      

      fitnessHistory.add(pf);
    }

    LOG.info("evolution complete.");

    return fitnessHistory;
  }

}

