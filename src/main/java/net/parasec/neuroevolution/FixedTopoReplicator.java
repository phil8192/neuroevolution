package net.parasec.neuroevolution;

import net.parasec.neuroevolution.genetic.Replicator;


public final class FixedTopoReplicator implements Replicator<FixedTopo> {

  public FixedTopo replicate(final FixedTopo ft) {
    return new FixedTopo(ft);
  }

}

