package org.wallerlab.yoink.adaptive.domain;

import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.math.Vector;


/**
 * Created by waller on 13/07/16.
 */
public class AdaptiveMolecule {

    private final Molecule molecule;

    private final Double lambda;

    private Vector<Double> qmmmForce;

    private Vector<Double> mmForce;

    public AdaptiveMolecule(Molecule molecule, Double lambda) {
        this.molecule = molecule;
        this.lambda = lambda;
    }

    public Double getLambda() {
        return lambda;
    }

    public Vector<Double> getQmmmForce() {
        return qmmmForce;
    }

    public void setQmmmForce(Vector<Double> qmmmForce) {
        this.qmmmForce = qmmmForce;
    }

    public Vector<Double> getMmForce() {
        return mmForce;
    }

    public void setMmForce(Vector<Double> mmForce) {
        this.mmForce = mmForce;
    }
}
