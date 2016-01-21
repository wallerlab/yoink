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
package org.wallerlab.yoink.cube.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * This class assigns a grid point to two molecules and two atoms it is closest
 * to based on Voronoi partitioning and returns two molecules and two atoms in a
 * map.
 */
@Service
public class VoronoiCalculator implements
		Calculator<Map<String, Object>, Coord, Set<Molecule>> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * find two closest atoms and molecules for a grid point
	 * 
	 * @param tempCoord
	 *            -the coordinate of the grid point,
	 *            {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param molecules
	 *            - a Set of molecules,
	 *            {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 * @return properties, a Map, String as key, Object as value
	 */
	@Override
	public Map<String, Object> calculate(Coord tempCoord,
			Set<Molecule> molecules) {
		// initialize
		List<Double> neighbourDistances = new ArrayList<Double>();
		List<Atom> twoAtoms = new ArrayList<Atom>();
		List<Molecule> twoMolecules = new ArrayList<Molecule>();
		loopOverAllMoleculesInRegion(twoAtoms, neighbourDistances, tempCoord,
				molecules, twoMolecules);
		// put two closest atoms and moleucles in a list
		Map<String, Object> properties = packResults(twoAtoms, twoMolecules);
		return properties;
	}

	private Map packResults(List<Atom> twoAtoms, List<Molecule> twoMolecules) {
		// put two molecules in the result into a Set<Molecule> moleculeSet
		Set<Molecule> moleculeSet = new HashSet<>();
		moleculeSet.addAll(twoMolecules);
		// put two atoms in the result into a Set<Atom> atomSet
		Set<Atom> atomSet = new HashSet<>();
		atomSet.addAll(twoAtoms);
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("twoClosestAtoms", atomSet);
		properties.put("twoClosestMolecules", moleculeSet);
		return properties;
	}

	/*
	 * Find the distances between a grid point and atoms, then get two atoms
	 * corresponding to the first and second lowest distances. Next get two
	 * molecules corresponding to the first and second lowest distances.
	 */
	private void loopOverAllMoleculesInRegion(List<Atom> twoNeighbours,
			List<Double> neighbourDistances, Coord tempCoord,
			Set<Molecule> molecules, List<Molecule> twoMolecules) {
		for (Molecule molecule : molecules) {
			for (Atom atom : molecule.getAtoms()) {
				double distance = distanceBetweenGridPointToAtom(tempCoord,
						atom);
				getTwoClosestNeighbours(twoNeighbours, neighbourDistances,
						atom, distance, twoMolecules, molecule);
			}
		}
	}

	private double distanceBetweenGridPointToAtom(Coord tempCoord, Atom atom) {
		double distance = distanceCalculator.calculate(tempCoord, atom);
		return distance;
	}

	private void getTwoClosestNeighbours(List<Atom> twoNeighbours,
			List<Double> neighbourDistances, Atom atom, double distance,
			List<Molecule> twoMolecules, Molecule molecule) {
		int size = neighbourDistances.size();
		switch (size) {
		case 0:
			// add one neighbour
			neighbourDistances.add(0, distance);
			twoNeighbours.add(0, atom);
			twoMolecules.add(0, molecule);
			break;
		case 1:
			// add one more neighbour.the first neighbour is the closer one
			neighbourDistances.add(1, distance);
			twoNeighbours.add(1, atom);
			twoMolecules.add(1, molecule);
			if (neighbourDistances.get(0) >= neighbourDistances.get(1)) {
				Collections.reverse(neighbourDistances);
				Collections.reverse(twoNeighbours);
				Collections.reverse(twoMolecules);
			}
			break;
		case 2:// compare current distance with the distance of second
				// neighbour, if true, replace second neighbor and compare with
				// the first neighbour
			if (neighbourDistances.get(1) >= distance) {
				neighbourDistances.set(1, distance);
				twoNeighbours.set(1, atom);
				twoMolecules.set(1, molecule);
				if (neighbourDistances.get(0) >= neighbourDistances.get(1)) {
					Collections.reverse(neighbourDistances);
					Collections.reverse(twoNeighbours);
					Collections.reverse(twoMolecules);
				}
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid type of size: " + size);
		}
	}

}
