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

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.Calculator;

import javax.annotation.Resource;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * This class assigns a grid point to two molecules and two atoms it is closest
 * to based on Voronoi partitioning and returns two molecules and two atoms in a
 * map.
 */
@Service
public class VoronoiFunctionalCalculator implements
		Calculator<Map<String, Object>, Coord, Set<Molecule>> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * find two closest atoms and molecules for a grid point
	 *
	 * @param gridPoint
	 *            -the coordinate of the grid point,
	 *            {@link Coord}
	 * @param molecules
	 *            - a Set of molecules,
	 *            {@link Molecule}
	 * @return properties, a Map, String as key, Object as value
	 */
	/*
	 * Find the distances between a grid point and atoms, then get two atoms
	 * corresponding to the first and second lowest distances. Next get two
	 * molecules corresponding to the first and second lowest distances.
	 */
	@Override
	public Map<String, Object> calculate(Coord gridPoint, Set<Molecule> molecules) {

		int neighbour1index=0;
		int neighbour2index=1;

		List<Double> nearestDistances  = molecules.stream()
												  .map( molecule ->  {return nearestAtomInEachMolecule(gridPoint, molecule);})
												  .collect(toList());


		getTwoClosestNeighbours(nearestDistances,neighbour1index,neighbour2index);

		List<Molecule> moleculeList = new ArrayList<Molecule>();
		moleculeList.addAll(molecules);
		Set<Molecule> moleculeSet = new HashSet<>();
		moleculeSet.add(moleculeList.get(neighbour1index));
		moleculeSet.add(moleculeList.get(neighbour2index));

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("twoClosestMolecules", moleculeSet);
		return properties;
	}



	private Double nearestAtomInEachMolecule(Coord tempCoord, Molecule molecule) {
		return  molecule.getAtoms()
						.stream()
                        .mapToDouble(atom -> {return distanceCalculator.calculate(tempCoord,atom);})
                        .min()
                        .getAsDouble();
	}

	private void getTwoClosestNeighbours(List<Double> nearestDistances, int neighbour1index, int neighbour2index ) {

		Double neighbour1 = nearestDistances.get(0);
		Double neighbour2 = nearestDistances.get(1);

		if (neighbour2 < neighbour1) {
			neighbour1 = nearestDistances.get(1);
			neighbour1index = 1;
			neighbour2 = nearestDistances.get(0);
			neighbour2index = 0;
		}

		for (int index = 2; index < nearestDistances.size(); index++) {
			if (nearestDistances.get(index) < neighbour1) {
				neighbour2 = neighbour1;
				neighbour2index = neighbour1index;
				neighbour1 = nearestDistances.get(index);
				neighbour1index = index;
			} else if (nearestDistances.get(index) < neighbour2) {
				neighbour2 = nearestDistances.get(index);
				neighbour2index = index;
			}
		}
		return;
	}

}
