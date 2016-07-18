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

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.cube.domain.Cube;
import org.wallerlab.yoink.api.model.VoronoiPoint;
import org.wallerlab.yoink.api.model.DensityPoint;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.cube.domain.SimpleVoronoiPoint;
import org.wallerlab.yoink.math.SetOps;
import org.wallerlab.yoink.molecule.service.DistanceCalculator;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;

import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;

/**
 * This class is to get those grid points that belong to the intersection region
 * of QM core region and non-QM core region. Then further calculation(dori and
 * sedd) will just be carried out on those grid points.
 *
 * This class assigns a grid point to two molecules and two atoms it is closest
 * to based on Voronoi partitioning and returns two molecules and two atoms in a
 * map.
 */
@Service
public class SimpleVoronoizer implements Voronoizer {

	@Resource
	private CubeBuilder<Set<MolecularSystem.Molecule>> cubeBuilder;

	@Resource
	private DistanceCalculator distanceCalculator;

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
										Set<MolecularSystem.Molecule> moleculesInAdaptiveSearchRegion,
										MolecularSystem molecularSystem){

		Set<MolecularSystem.Molecule> moleculesInSystem = molecularSystem.getMolecules();
		Set<MolecularSystem.Molecule> moleculesInQmCore = molecularSystem.getMolecules("QM_CORE");
		Set<MolecularSystem.Molecule> moleculesInNonQmCore = SetOps.diff(moleculesInSystem,moleculesInQmCore);


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
							Set<MolecularSystem.Molecule> nearestMolecules = nearestMolecules(coord, moleculesInAdaptiveSearchRegion);
					  		boolean isPair = nearestMolecules.size() != 2;
					   		boolean bothInNonQmCore = moleculesInNonQmCore.containsAll(nearestMolecules);
					   		boolean bothInQmCore    = moleculesInQmCore.containsAll(nearestMolecules);
							if (isPair || bothInNonQmCore || bothInQmCore)
								gridPoint = new SimpleVoronoiPoint(coord, nearestMolecules, nearestAtoms(coord,nearestMolecules));
							return gridPoint;
				   })
				   .collect(toList());
	}

	private Set<MolecularSystem.Molecule> nearestMolecules(Coord coord, Set<MolecularSystem.Molecule> molecules ) {
		MolecularSystem.Molecule[] moleculePair = new MolecularSystem.Molecule[2];
		double first = Integer.MAX_VALUE;
		double second = Integer.MAX_VALUE;
		for (MolecularSystem.Molecule molecule: molecules){
			double current = distanceCalculator.closest(coord,molecule);
			if ( current < first ) {
				//switch 1 to 2
				second = first;
				moleculePair[1] = moleculePair[0];
				//Then set 1
				moleculePair[0] = molecule;
				first = current;
			}
			else if ( current < second && second != first ){
				second = current;
				moleculePair[1]= molecule;
			}
		}
		Set<MolecularSystem.Molecule> nearest = new HashSet();
		nearest.add(moleculePair[0]);
		nearest.add(moleculePair[1]);
		return nearest;
	}

	/**
	 * find two closest atoms from a grid point for two molecules
	 *
	 * @param coord -the coordinate of the grid point,
	 *                  {@link Coord}
	 * @param molecules - a Set of molecules,
	 *                  {@link MolecularSystem.Molecule}
	 * @return properties, a Map, String as key, Object as value
	 */
	private Set<MolecularSystem.Molecule.Atom> nearestAtoms(Coord coord, Set<MolecularSystem.Molecule> molecules){
		Set<MolecularSystem.Molecule.Atom> nearestAtoms = new HashSet<>();
		for(MolecularSystem.Molecule molecule :molecules) nearestAtoms.add(getClosestAtom(coord, molecule));
		return nearestAtoms;
	}

	private MolecularSystem.Molecule.Atom getClosestAtom(Coord coord, MolecularSystem.Molecule molecule){
		return molecule.getAtoms()
						.stream()
						.min((atomOne,atomTwo) ->
								((Double)distanceCalculator.distance(coord,atomOne))
										        .compareTo((Double) distanceCalculator.distance(coord,atomTwo)))
						.get();
	}

}
