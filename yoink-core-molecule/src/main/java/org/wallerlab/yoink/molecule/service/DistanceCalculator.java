/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.molecule.service;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.math.MapOps;
import org.wallerlab.yoink.molecule.domain.SimpleCoord;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

import java.util.*;
import org.springframework.stereotype.Service;

/**
 * This class is to calculate various distances for a molecular system.
 * 
 * @author Min Zheng
 *
 */
@Service
public class DistanceCalculator implements IDistanceCalculator {

    /**
     * distance between a coordinate and an atom.
     *
     * @param gridCoord
     *            -{@link Coord}
     * @param atom
     *            -{@link MolecularSystem.Molecule.Atom}
     * @return density -{@link java.lang.Double}
     *
     */
    @Override
	public double distance(Coord gridCoord, MolecularSystem.Molecule.Atom atom) {
        return atom.getCoordinate().distance(gridCoord.getCoords());
    }

	/**
	 * minimum distance between a coordinate and a molecule.
	 *
	 * @param coord
	 *            -{@link Coord}
	 * @param molecule
	 *            -{@link MolecularSystem.Molecule}
	 * @return density -{@link java.lang.Double}
	 *
	 */
	@Override
	public double closest(Coord coord, MolecularSystem.Molecule molecule){
		return molecule.getAtoms()
					   .stream()
				       .mapToDouble(atom ->coord.getCoords().distance(atom.getCoordinate()))
				 	   .min()
				       .getAsDouble();
	}

    /**
	 * Calculate the coordinates of mass of molecules.
	 *
	 * @param molecules
	 *            - a set of Molecules
	 * @return centerCoord {@link Coord}
	 *
	 */
	@Override
	public Coord centerOfMass(Set<MolecularSystem.Molecule> molecules) {
		double massOfMolecule = 0;
		Vector massWeightedCoordinate = SimpleVector3DFactory.staticCreate(0, 0, 0);
		for (MolecularSystem.Molecule molecule : molecules) {
			for (MolecularSystem.Molecule.Atom atom : molecule.getAtoms()) {
				double atomMass = atom.getElement().atomMass();
				massOfMolecule += atomMass;
				Vector matrix = atom.getCoordinate().scalarMultiply(atomMass);
				massWeightedCoordinate = matrix.add(massWeightedCoordinate);
			}
		}
		Vector com = massWeightedCoordinate.scalarMultiply(1.0 / massOfMolecule);
		return new SimpleCoord(com);
	}


    //Re-write this to return a sorted set
    /**
     * This method to compute distances between molecules and a point,
     * make a map(sortedDistances) for molecule(as key) and corresponding
     * distance(as value), and sort the map by distance.
     *
     * @param centerCoord
     *            -{@link Coord}
     * @param molecules
     *            - a Set of molecules
     * @return sortedDistance - a Map, molecule as key and distance as value
     */
    @Override
	public Map<MolecularSystem.Molecule, Double> sortByDistance(Coord centerCoord, Set<MolecularSystem.Molecule> molecules) {
	Map<MolecularSystem.Molecule, Double> distances = new HashMap<MolecularSystem.Molecule, Double>();
	for (MolecularSystem.Molecule molecule : molecules) {
		List<Double> atomDistances = new ArrayList<Double>();
		for (MolecularSystem.Molecule.Atom atom : molecule.getAtoms())
			atomDistances.add(distance(centerCoord, atom));
		// sort the distances for given atoms in a molecule
		double distanceMin = Collections.min(atomDistances);
		// store the molecule with its closest atom
		distances.put(molecule, distanceMin);
	}
	// now sort the list of molecules based on their closest atoms
	Map<MolecularSystem.Molecule, Double> sortedDistances = MapOps.sortByValue(distances);
	//ALTERNATIVE
	/*Comparator<Molecule> distance = (Molecule m1, Molecule m2) -> m1.getDistanceToPoint().compareTo(m2.getDistanceToPoint())
	molecules.stream()
		 .map(molecule -> molecule.setDistanceToPoint(closest(centerCoord, molecule)))
		 .sorted(distance)
			 .collect(toSet());*/
	return sortedDistances;
    }

}
