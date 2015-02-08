package net.parasec.neuroevolution.genetic;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;


public final class ParallelPopulationEvaluator<T extends Individual> 
    implements PopulationEvaluator<T> {

  private final int threads;

  // outgoing work to do queue
  private final LinkedBlockingQueue<ArrayList<T>> jobs 
      = new LinkedBlockingQueue<ArrayList<T>>();

  // incoming job complete ack queue
  private final LinkedBlockingQueue<PopulationFitness> completed
      = new LinkedBlockingQueue<PopulationFitness>();
  

  public ParallelPopulationEvaluator(final int threads, 
      final PopulationEvaluator<T> e) {
    if(threads <= 0) throw new IllegalArgumentException();
    this.threads = threads;
   
    initWorkers(threads, e);
  }

  private void initWorkers(final int threads, final PopulationEvaluator<T> e) {
    for(int i = threads; --i >= 0; ) {  
      Executors.defaultThreadFactory().newThread(new Runnable() {
        public final void run() {
          try {

            while(!Thread.currentThread().interrupted())
              completed.offer(e.eval(jobs.take()));

          } catch(final InterruptedException ie) {
            Thread.currentThread().interrupt();
          } 
        }
      }).start();
    }
  }

  /**
   * split up an array lists into shards.
   *
   * @param pop popularion array list
   * @param n   number of shards
   * @return    an array list of array lists (shards)
   */ 
  private ArrayList<ArrayList<T>> split(final ArrayList<T> pop, final int n) {
    final int popSize = pop.size();
    final int segmentSize = (int) Math.ceil(popSize / (double) n);

    final ArrayList<ArrayList<T>> split = new ArrayList<ArrayList<T>>(n);

    for(int i = 0; i < n; i++)
      split.add(i, new ArrayList<T>(segmentSize));

    for(int i = 0, j = 0, len = pop.size(); i < len; i++, j = i % n)
      split.get(j).add(pop.get(i)); 

    return split;
  }

  public PopulationFitness eval(final ArrayList<T> pop) {

    // split the population into shards.
    final ArrayList<ArrayList<T>> split = split(pop, threads);

    // send each shard to be evaluated
    for(final ArrayList<T> shard : split)
      jobs.offer(shard);
     
    // collect and aggregate completed jobs
    String minReport = null, maxReport = null;
    double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, sum = 0;
    try {

      for(int i = threads; --i >= 0; ) {

        final PopulationFitness pf = completed.take();
        
        final double fitMin = pf.getMin();
        final double fitMax = pf.getMax();

        if(fitMin < min) {
          min = fitMin;
          minReport = pf.getMinReport();
        }
        if(fitMax > max) {
          max = fitMax;
          maxReport = pf.getMaxReport();
        }

        sum += pf.getSum();

      }
    } catch(final InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    
    return new PopulationFitness(min, max, sum/pop.size(), sum, 
        minReport, maxReport);
  }
  
}
 
