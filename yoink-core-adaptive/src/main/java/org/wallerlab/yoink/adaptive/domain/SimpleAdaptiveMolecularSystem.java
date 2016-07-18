package org.wallerlab.yoink.adaptive.domain;

import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;

import java.util.List;

public class SimpleAdaptiveMolecularSystem implements AdaptiveMolecularSystem {

    final Double energy;

    final List<Vector> forces;

    final MolecularSystem molecularSystem;

    public SimpleAdaptiveMolecularSystem(Double energy, List<Vector> forces, MolecularSystem molecularSystem) {
        this.energy = energy;
        this.forces = forces;
        this.molecularSystem = molecularSystem;
    }

    @Override
    public Double getEnergy() {
        return energy;
    }

    @Override
    public List<Vector> getForces() {
        return forces;
    }

    @Override
    public MolecularSystem getMolecularSystem() {
        return molecularSystem;
    }
}
