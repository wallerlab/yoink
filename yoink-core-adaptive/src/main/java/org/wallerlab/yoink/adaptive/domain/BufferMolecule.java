package org.wallerlab.yoink.adaptive.domain;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Optional;

/**
 * Created by waller on 16/07/16.
 */
public class BufferMolecule  {

    private final double lambda;

    private final MolecularSystem.Molecule molecule;

    private Double distanceToPoint;

    private Optional<Double> qmWeight;

    private Optional<Double> bufferWeight;

    public BufferMolecule(final MolecularSystem.Molecule molecule, final  double lambda) {
        this.molecule = molecule;
        this.lambda = lambda;
    }

    public Double getLambda() {
        return lambda;
    }

    public Double getDistanceToPoint() {
        return distanceToPoint;
    }


    public MolecularSystem.Molecule getMolecule() {return molecule;}

    public void setDistanceToPoint(double distanceToPoint) {this.distanceToPoint = distanceToPoint;}

    public Optional<Double> getQmWeight() {
        return qmWeight;
    }

    public void setQmWeight(double qmWeight) {
        this.qmWeight = Optional.of(qmWeight);
    }

    public Optional<Double> getBufferWeight() {
        return bufferWeight;
    }

    public void setBufferWeight(double bufferWeight) {
        this.bufferWeight = Optional.of(bufferWeight);
    }
}
