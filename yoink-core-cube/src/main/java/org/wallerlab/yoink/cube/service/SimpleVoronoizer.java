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
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.cube.CubeBuilder;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.cube.domain.SimpleGridPoint;

import javax.annotation.Resource;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

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
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * Generate a cube for this Molecular System
	 * loop over all coordinates in the cube,
	 * find the coordinates whose two closest neighbours are from different regions
	 * (QM core region and non-QM core region ).
	 * Construct grid points for these coordinates.
	 *
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link Region.Name }
	 *            as key, Region
	 *            {@link Region} as
	 *            value
	 * @param parameters
	 *            - a Map, JobParameter
	 *            {@link JobParameter}
	 *            as Key, Object {@link Object} as value
	 * @param densityType
	 *            -
	 *            {@link DensityPoint.DensityType}
	 * @return gridPointsForFurtherAnalysis - a list of gridPoint
	 *         {@link GridPoint }
	 */
	@Override
	public List<GridPoint> voronoize(List<Region> regions,
							  List<JobParameter> parameters,
							  DensityPoint.DensityType densityType){

		//Cube Init
		Region.Name cubeRegionName = (Region.Name) parameters.get(REGION_CUBE);
		Set<Molecule> moleculesInCube = regions.get(cubeRegionName).getMolecules();
		double[] xyzStepSize = getXyzStepSizeByDensityType(parameters, densityType);

		//POTENTIAL BOTTLENECK
		Cube cube = cubeBuilder.build(xyzStepSize, moleculesInCube);

		//BOTTLENECK
		return cube.getCoordinates()
				.stream()
				.map( coord -> {
					GridPoint gridPoint = null;
					Map<String,Set> nearest =  new HashMap();

					nearest = nearest(coord, moleculesInCube);
					if (!filter(nearest, regions, cubeRegionName)){
						gridPoint =  create(coord, nearest);
					}
					return gridPoint;
				})
				.collect(toList());
	}


	/**
	 * find two closest atoms and molecules for a grid point
	 *
	 * @param coord -the coordinate of the grid point,
	 *                  {@link Coord}
	 * @param molecules - a Set of molecules,
	 *                  {@link Molecule}
	 * @return properties, a Map, String as key, Object as value
	 */

	/*
	 * Find the distances between a grid point and atoms, then get two atoms
	 * corresponding to the first and second lowest distances. Next get two
	 * molecules corresponding to the first and second lowest distances.
	 */
	public GridPoint create(Coord coord, Map nearest) {
		GridPoint gridPoint = new SimpleGridPoint();
		gridPoint.setCoordinate(coord);
	//	gridPoint.setIndexInCube(coordinates.indexOf(coord));
		gridPoint.setNearestAtoms((List<Molecule>) nearest.get("molecules"));
		gridPoint.setNearestAtoms((List<Atom>)nearest.get("atomss"));
		return gridPoint;
	}

	private boolean filter(Map<String, Set> nearest,
						   Map<Region.Name, Region> regions,
						   Region.Name cubeRegionName){

		//init
		Region qmCoreRegion = regions.get(QM_CORE);
		Region nonQmCoreRegion = regions.get(NON_QM_CORE_ADAPTIVE_SEARCH);
		Set<Molecule> molecules = regions.get(cubeRegionName).getMolecules();


		boolean notNeighbourPair = nearest.size() != 2;
		if (cubeRegionName != SYSTEM) {
			Set<Molecule> mols = nearest.get("molecules");
			boolean bothNeighboursAreInNonQmCore = nonQmCoreRegion.containsAll(mols);
			boolean bothNeighboursAreInQmCore = qmCoreRegion.containsAll(mols);
			boolean invalid =  notNeighbourPair || bothNeighboursAreInNonQmCore || bothNeighboursAreInQmCore;
			if (invalid) return true;
		}
		 return false;
	}


	private Map nearest(Coord coord, Set<Molecule> molecules){
		Map neighbours = new HashMap();

		Set<Molecule> neighbourMolecules = 	getTwoClosestNeighbours(coord, molecules);


		Set<Atom> neighbourAtoms = new HashSet<>();
			for(Molecule molecule:neighbourMolecules){
			neighbourAtoms.add(getClosestAtom(coord, molecule));
		}

		neighbours.put("molecules",neighbourMolecules);
		neighbours.put("atoms",neighbourAtoms);

		return neighbours;
	}

	private Set<Molecule> getTwoClosestNeighbours(Coord coord, Set<Molecule> molecules ) {

		Molecule[] moleculeArray = new Molecule[2];
		Set<Molecule> moleculeSet = new HashSet<>();

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


		if(moleculeArray[0].getIndex() ==moleculeArray[1].getIndex()){
			moleculeSet.add(moleculeArray[0]);
			moleculeSet.add(moleculeArray[1]);
		}
		return moleculeSet;

	}

	private Double shortestDistanceToMolecule(Coord coord, Molecule molecule) {
		return molecule.getAtoms()
						.stream()
						.mapToDouble(atom -> {return distanceCalculator.calculate(coord,atom);})
						.min()
						.getAsDouble();
	}


	private Atom getClosestAtom(Coord coord, Molecule molecule){
		return molecule.getAtoms()
						.stream()
						.min((a1,a2) -> distanceCalculator.calculate(coord,a1).compareTo(distanceCalculator.calculate(coord,a2)))
						.get();
	}

	/*
	 * DORI and SEDD calculations use different xyz step sizes.
	 *
	 * Externalize this, i.e. grid should not know about DORI or SEDD
	 */
	private double[] getXyzStepSizeByDensityType(Map<JobParameter, Object> parameters, DensityPoint.DensityType densityType) {
		double[] xyzStepSize;
		// get xyz step size
		switch (densityType) {
			case SEDD:
				xyzStepSize = (double[]) parameters.get(SEDD_STEPSIZE);
				break;
			case DORI:
				xyzStepSize = (double[]) parameters.get(DORI_STEPSIZE);
				break;
			default:
				throw new IllegalArgumentException("invalid  name");
		}
		return xyzStepSize;
	}

}
