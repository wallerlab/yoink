package org.wallerlab.yoink.molecule.service;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Map;
import java.util.Set;

/**
 * Created by waller on 11/08/16.
 */
public interface IDistanceCalculator {
    double distance(Coord gridCoord, MolecularSystem.Molecule.Atom atom);

    double closest(Coord coord, MolecularSystem.Molecule molecule);

    Coord centerOfMass(Set<MolecularSystem.Molecule> molecules);

    Map<MolecularSystem.Molecule, Double> sortByDistance(Coord centerCoord, Set<MolecularSystem.Molecule> molecules);
}
