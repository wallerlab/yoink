package org.wallerlab.yoink.adaptive.domain;

import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;

import java.util.List;

public class SimpleAdaptiveMolecularSystem implements AdaptiveMolecularSystem{

    private Double qmWeight;

    private Double bufferWeight;

    private Double mmWeight;

    private final Double energy;

    private final List<Vector> forces;

    private final MolecularSystem molecularSystem;

    public SimpleAdaptiveMolecularSystem(Double energy, List<Vector> forces, MolecularSystem molecularSystem) {
        this.energy = energy;
        this.forces = forces;
        this.molecularSystem = molecularSystem;
    }

    public Double getEnergy() {
        return energy;
    }

    public List<Vector> getForces() {
        return forces;
    }

    public MolecularSystem getMolecularSystem() {
        return molecularSystem;
    }


    public Double getQmWeight() {
        return qmWeight;
    }

    public void setQmWeight(Double qmWeight) {
        this.qmWeight = qmWeight;
    }


    public Double getBufferWeight() {
        return bufferWeight;
    }

    public void setBufferWeight(Double bufferWeight) {
        this.bufferWeight = bufferWeight;
    }

    public Double getMmWeight() {
        return mmWeight;
    }

    public void setMmWeight(Double mmWeight) {
        this.mmWeight = mmWeight;
    }

}
