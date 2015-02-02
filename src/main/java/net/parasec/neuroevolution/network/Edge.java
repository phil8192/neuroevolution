package net.parasec.neuroevolution.network;

public class Edge {

  private Node in;
  private double weight;

  
  public Edge(Node in, double weight) {
    this.in = in;
    this.weight = weight;
  }

  public double value() {
    return weight*in.value();
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public Node getIncomingNode() {
    return in;
  }

}

