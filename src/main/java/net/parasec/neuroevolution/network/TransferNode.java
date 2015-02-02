package net.parasec.neuroevolution.network;

public class TransferNode extends Node {

  private Edge[] in;
  private TransferFunction tf;
  

  public TransferNode(final String id, Edge[] in, TransferFunction tf) {
    super(id);
    this.in = in;
    this.tf = tf;
  }

  public TransferNode(final String id, double value, Edge[] in, 
      TransferFunction tf) {
    super(id, value);
    this.in = in;
    this.tf = tf;
  }

  public void setEdges(Edge[] in) {
    this.in = in;
  }

  public TransferFunction getTransferFunction() {
    return tf;
  }

  public void setTransferFunction(TransferFunction tf) {
    this.tf = tf;
  }

  public Edge[] getIncomingEdges() {
    return in;
  }

  public void activate() {
    double net = 0d;
    for(int i = 0, len = in.length; i < len; i++)
      net += in[i].value();
    setValue(tf.activate(net));
  }

}

