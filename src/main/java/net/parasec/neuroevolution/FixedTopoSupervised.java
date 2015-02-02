package net.parasec.neuroevolution;

import net.parasec.neuroevolution.util.MathUtil;
import net.parasec.neuroevolution.network.Network;
import net.parasec.neuroevolution.network.TransferNode;
import net.parasec.neuroevolution.genetic.IndividualEvaluator;


public class FixedTopoSupervised implements IndividualEvaluator<FixedTopo> {

  private final double[][][] instances; 
 

  public FixedTopoSupervised(final double[][][] instances) {
    this.instances = instances;
  }

  public void eval(final FixedTopo t) {

    final double[][][] instances = this.instances;

    final Network net = t.getNet(); 
    final TransferNode[] outputNodes = net.getOutputs();
    final int nOutputs = outputNodes.length;

    double sse = 0;

    for(int i = 0, len = instances.length; i < len; i++) {

      final double[][] instance = instances[i];
      final double[] input = instance[0], desiredOutput = instance[1];

      net.propagate(input);
 
      for(int j = 0; j < nOutputs; j++) {
        final double error = desiredOutput[j]-outputNodes[j].value();
        sse += error*error;
      }           
     
    }
    t.setFitness(MathUtil.fastSqrt(sse*(1.0/nOutputs))); 
  }
  
}

