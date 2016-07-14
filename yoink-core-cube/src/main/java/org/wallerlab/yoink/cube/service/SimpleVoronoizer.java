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
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.cube.domain.Cube;
import org.wallerlab.yoink.api.model.cube.VoronoiPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.cube.domain.SimpleVoronoiPoint;
import org.wallerlab.yoink.math.set.SetOps;

import javax.annotation.Resource;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;

/**
 * This class is to get those grid points that belong to the intersection region
 * of QM core region and non-QM core region. Then further calculation(dori and
 * sedd) will just be carried out on those grid points.
 *
 *
 */
/**
 * This class assigns a grid point to two molecules and two atoms it is closest
 * to based on Voronoi partitioning and returns two molecules and two atoms in a
 * map.
 */
@Service
public class SimpleVoronoizer implements Voronoizer {

	@Resource
	private CubeBuilder<Set<Molecule>> cubeBuilder;

	@Resource
	private Calculator<Double, Coord, Atom> distance;

	private static final double[] seddStepSize = {0.9d,0.9d,0.9d};
	private static final double[] doriStepSize = {0.5d,0.5d,0.5d};

	/**
	 * Generate a cube for this Molecular System.
	 * Loop over all coordinates in the cube.
	 * find the two closest neighbours moleules.
	 * filter out points where the neighbours are not from different regions
	 * (QM core region and non-QM core region ).
	 * Return the remaining grid points for further analysis.
	 *
	 * @param densityType
	 *            -
	 *            {@link DensityPoint.DensityType}
	 * @return gridPointsForFurtherAnalysis - a list of gridPoint
	 *         {@link VoronoiPoint }
	 */
	public List<VoronoiPoint> voronoize(DensityPoint.DensityType densityType,
										Set<Molecule> moleculesInAdaptiveSearchRegion,
										MolecularSystem molecularSystem){

		Set<Molecule> moleculesInSystem = molecularSystem.getMolecules();
		Set<Molecule> moleculesInQmCore = molecularSystem.getMolecules("QM_CORE");
		Set<Molecule> moleculesInNonQmCore = SetOps.diff(moleculesInSystem,moleculesInQmCore);


		double[] xyzStepSize = (densityType.equals(SEDD)) ? seddStepSize: doriStepSize;

		Cube cube = cubeBuilder.build(xyzStepSize, moleculesInAdaptiveSearchRegion);

	/*
	 * Find the distances between a grid point and atoms, then get two atoms
	 * corresponding to the first and second lowest distances. Next get two
	 * molecules corresponding to the first and second lowest distances.
	 */
		return cube.getCoordinates()
				   .stream()
				   .map(coord -> {
							VoronoiPoint gridPoint = null;
							Set<Molecule> nearestMolecules = nearestMolecules(coord, moleculesInAdaptiveSearchRegion);
					  		boolean isPair = nearestMolecules.size() != 2;
					   		boolean bothInNonQmCore = moleculesInNonQmCore.containsAll(nearestMolecules);
					   		boolean bothInQmCore    = moleculesInQmCore.containsAll(nearestMolecules);
							if (isPair || bothInNonQmCore || bothInQmCore)
								gridPoint = new SimpleVoronoiPoint(coord, nearestMolecules, nearestAtoms(coord,nearestMolecules));
							return gridPoint;
				   })
				   .collect(toList());
	}

	private Set<Molecule> nearestMolecules(Coord coord, Set<Molecule> molecules ) {
		Molecule[] moleculeArray = new Molecule[2];
		for (Molecule molecule: molecules){
			double first = Integer.MAX_VALUE;
			double second = Integer.MAX_VALUE;
			moleculeArray[0] = molecule;
			moleculeArray[1] = molecule;
			double current = shortestDistanceToMolecule(coord,molecule);
			if ( current < first ) {
				second = first;
				moleculeArray[1] = moleculeArray[0];
				first = current;
				moleculeArray[0]  = moleculeArray[1];
			}
			else if ( current < second && second != first ){
				second = current;
				moleculeArray[1]= moleculeArray[0];
			}
		}
		Set<Molecule> nearest = new HashSet();
		nearest.add(moleculeArray[0]);
		nearest.add(moleculeArray[1]);
		return nearest;
	}

	/**
	 * find two closest atoms from a grid point for two molecules
	 *
	 * @param coord -the coordinate of the grid point,
	 *                  {@link Coord}
	 * @param molecules - a Set of molecules,
	 *                  {@link Molecule}
	 * @return properties, a Map, String as key, Object as value
	 */
	private Set<Atom> nearestAtoms(Coord coord, Set<Molecule> molecules){
		Set<Atom> nearestAtoms = new HashSet<>();
		for(Molecule molecule :molecules) nearestAtoms.add(getClosestAtom(coord, molecule));
		return nearestAtoms;
	}


	private double shortestDistanceToMolecule(Coord coord, Molecule molecule) {
		return molecule.getAtoms()
						.stream()
						.mapToDouble(atom -> distance.calculate(coord,atom))
						.min()
						.getAsDouble();
	}


	private Atom getClosestAtom(Coord coord, Molecule molecule){
		return molecule.getAtoms()
						.stream()
						.min((atomOne,atomTwo) -> distance.calculate(coord,atomOne).compareTo(distance.calculate(coord,atomTwo)))
						.get();
	}

}
