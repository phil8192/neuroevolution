package net.parasec.neuroevolution.network;

public class Node {

  // id is useful for object copy
  private final String id;

  // the computed value/last output of the this node
  private double value;


  public Node(final String id) {
    this.id = id;
  }

  public Node(final String id, double value) {
    this.id = id;
    this.value = value;
  }

  /**
   * copy.
   */ 
  public Node(final Node n) {
    this.id = n.getId();
    this.value = n.value();
  }

  public void setValue(double value) {
    this.value = value;
  }
  
  public double value() {
    return value;
  }

  public String getId() {
    return id;
  }

}

