package org.wallerlab.yoink.api.service.density;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Set;

public interface DensityCalculator {

	Double electronic(Coord coord, Set<MolecularSystem.Molecule> molecules);

	Double atomic(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms);

	Double ratio(Coord coord, MolecularSystem.Molecule.Atom[] neighbours);

	Double ratio(Coord coord, MolecularSystem.Molecule[] neighbours);

	Double rdg(Coord coord, Set<MolecularSystem.Molecule> molecules);

	Double sedd(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms);

	Double dori(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms);


}
