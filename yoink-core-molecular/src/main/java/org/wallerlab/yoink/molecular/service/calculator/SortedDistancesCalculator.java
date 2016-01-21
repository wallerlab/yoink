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
package org.wallerlab.yoink.molecular.service.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.math.map.MapSorter;

/**
 * This class is to get the sortedDistances which is a Map(molecule as key and
 * distance as value) sorted by distance in ascending way. And the distance is
 * between the center mass of a molecule and one point.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SortedDistancesCalculator implements
		Calculator<Map<Molecule, Double>, Coord, Set<Molecule>> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * This method is to calculate the distances between molecules and a point,
	 * make a map(sortedDistances) for molecule(as key) and corresponding
	 * distance(as value), and sort the map by distance.
	 * 
	 * @param centerCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param molecules
	 *            - a Set of molecules
	 * @return sortedDistance - a Map, molecule as key and distance as value
	 */
	public Map<Molecule, Double> calculate(Coord centerCoord,
			Set<Molecule> molecules) {
		// molecule, distance
		Map<Molecule, Double> distances = loopOverMolecules(molecules,
				centerCoord);
		// now sort the list of molecules based on their closest atoms
		Map<Molecule, Double> sortedDistances = MapSorter
				.sortByValue(distances);
		return sortedDistances;
	}

	private Map<Molecule, Double> loopOverMolecules(Set<Molecule> molecules,
			Coord centerCoord) {
		Map<Molecule, Double> distances = new HashMap<Molecule, Double>();
		for (Molecule molecule : molecules) {
			List<Double> atomDistances = new ArrayList<Double>();
			for (Atom atom : molecule.getAtoms()) {
				double distance = distanceCalculator.calculate(centerCoord,
						atom);
				atomDistances.add(distance);
			}
			// sort the distances for given atoms in a molecule
			double distanceMin = Collections.min(atomDistances);
			// store the molecule with its closest atom
			distances.put(molecule, distanceMin);
		}
		return distances;
	}

}
