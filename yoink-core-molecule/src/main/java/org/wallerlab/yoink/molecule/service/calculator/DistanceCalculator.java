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
package org.wallerlab.yoink.molecule.service.calculator;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.math.map.MapSorter;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecule.domain.SimpleCoordFactory;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * This class is to calculate the various distances for a molecular system.
 * 
 * @author Min Zheng
 *
 */
@Service
public class DistanceCalculator  {

	@Resource
	private SimpleVector3DFactory vectorFactory;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	/**
	 * calculate the distance between two coordinates
	 *
	 * @param c1 -{@link Coord}
	 * @param c2 -{@link Coord}
	 * @return the distance between gridCoord1 and gridCoord2
	 */
	Calculator<Double, Coord, Coord> twoCoordsDistance = ( c1, c2) -> c2.getCoords().distance(c1.getCoords());


	Calculator<Double, Atom, Atom> twoAtomsDistance = ( a1,  a2) ->  a1.getCoordinate().getCoords()
																	   .distance(a2.getCoordinate().getCoords());
	/**
	 * calculate the distance between a coordinate and an atom.
	 *
	 * @param gridCoord
	 *            -{@link Coord}
	 * @param atom
	 *            -{@link Atom}
	 * @return density -{@link java.lang.Double}
	 *
	 */
	public double calculate(Coord gridCoord, Atom atom) {
		return atom.getCoordinate()
				   .getCoords()
				   .distance(gridCoord.getCoords());
	}

	/**
	 * calculate the distance minimum between a coordinate and a molecule.
	 *
	 * @param coord
	 *            -{@link Coord}
	 * @param molecule
	 *            -{@link Molecule}
	 * @return density -{@link java.lang.Double}
	 *
	 */
	public double closest(Coord coord, Molecule molecule){
		return molecule.getAtoms()
					   .stream()
				       .mapToDouble(atom ->
						 		coord.getCoords().distance(atom.getCoordinate().getCoords()))
				 	   .min()
				       .getAsDouble();
	};

	/**
	 * calculate the center of mass of molecules.
	 *
	 * @param molecules
	 *            - a set of Molecules
	 * @return centerCoord {@link Coord}
	 *
	 */
	public Coord calculate(Set<Molecule> molecules) {
		double massOfMolecule = 0;
		Vector massWeightedCoordinate = SimpleVector3DFactory.staticCreate(0, 0, 0);
		for (Molecule molecule : molecules) {
			for (Atom atom : molecule.getAtoms()) {
				double atomMass = atom.getElementType().atomMass();
				massOfMolecule += atomMass;
				Vector matrix = atom.getCoordinate().getCoords().scalarMultiply(atomMass);
				massWeightedCoordinate = matrix.add(massWeightedCoordinate);
			}
		}
		Coord centerCoord = SimpleCoordFactory.createStatic();
		centerCoord.setCoords( massWeightedCoordinate.scalarMultiply(1.0 / massOfMolecule));
		return centerCoord;
	}

	//Re-write this to return a sorted set

	/**
	 * This method is to calculate the distances between molecules and a point,
	 * make a map(sortedDistances) for molecule(as key) and corresponding
	 * distance(as value), and sort the map by distance.
	 *
	 * @param centerCoord
	 *            -{@link Coord}
	 * @param molecules
	 *            - a Set of molecules
	 * @return sortedDistance - a Map, molecule as key and distance as value
	 */
	public Map<Molecule, Double> calculate(Coord centerCoord, Set<Molecule> molecules) {
		Map<Molecule, Double> distances = new HashMap<Molecule, Double>();
		for (Molecule molecule : molecules) {
			List<Double> atomDistances = new ArrayList<Double>();
			for (Atom atom : molecule.getAtoms()) {
				double distance = calculate(centerCoord, atom);
				atomDistances.add(distance);
			}
			// sort the distances for given atoms in a molecule
			double distanceMin = Collections.min(atomDistances);
			// store the molecule with its closest atom
			distances.put(molecule, distanceMin);
		}
		// now sort the list of molecules based on their closest atoms
		Map<Molecule, Double> sortedDistances = MapSorter.sortByValue(distances);


		//ALTERNATIVE
		/*Comparator<Molecule> distance = (Molecule m1, Molecule m2) -> m1.getDistanceToPoint().compareTo(m2.getDistanceToPoint())

		molecules.stream()
				 .map(molecule -> molecule.setDistanceToPoint(closest(centerCoord, molecule)))
				 .sorted(distance)
				 .collect(toSet());*/

		return sortedDistances;
	}

}
