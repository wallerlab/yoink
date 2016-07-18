package org.wallerlab.yoink.api.model.adaptive;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;

import java.util.List;

public interface AdaptiveMolecularSystem {

    Double getEnergy();

    List<Vector> getForces();

    MolecularSystem getMolecularSystem() ;

}
