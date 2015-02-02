package net.parasec.neuroevolution.network;

public interface TransferFunction {
  double activate(double net);
  TransferFunction copy();
}

