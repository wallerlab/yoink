package org.wallerlab.yoink.api.service.density;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;

import java.util.Set;

public interface DensityCalculator {

	Double electronic(Coord currentCoord, Set<Molecule> molecules);

	Double atomPair(Coord currentCoord, Set<Atom> atoms);

	Double rdg(Coord currentCoord, Set<Molecule> molecules);

	Double sedd(Coord currentCoord, Set<Atom> atoms);

	Double dori(Coord currentCoord, Set<Atom> atoms);

	//AtomicRatio
	Double calculate(Coord coordinate, Atom[] neighbours);

	//MoleculeRatio
	Double calculate(Coord coordinate, Molecule[] neighbours);

}
