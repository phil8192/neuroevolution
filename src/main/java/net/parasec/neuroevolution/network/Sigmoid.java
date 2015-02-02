package net.parasec.neuroevolution.network;

import net.parasec.neuroevolution.util.MathUtil;


public final class Sigmoid implements TransferFunction {

  public double activate(final double net) {
    return MathUtil.fastSigmoid(net);
  }

  public TransferFunction copy() {
    return new Sigmoid();
  }
}


