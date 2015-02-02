package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.Individual;
import net.parasec.neuroevolution.genetic.OptDir;
import net.parasec.neuroevolution.network.Network;
import net.parasec.neuroevolution.network.Edge;

import java.util.ArrayList;


public final class FixedTopo extends Individual {

  private final Network net;
  private final ArrayList<Edge> edges;


  public FixedTopo(final Network net, final OptDir optDir) {
    super(optDir);
    this.net = net;
    this.edges = net.getAllEdges();
  }

  public FixedTopo(final FixedTopo ft) {
    super(ft.getFitness(), ft.getOptDir());
    net = new Network(ft.getNet());
    edges = net.getAllEdges();        
  }

  public Network getNet() {
    return net;
  }

  public Edge getEdge(final int i) {
    return edges.get(i);
  }

  public int size() {
    return edges.size();
  }

}

