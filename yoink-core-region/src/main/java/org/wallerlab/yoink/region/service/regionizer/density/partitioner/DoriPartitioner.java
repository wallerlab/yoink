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
package org.wallerlab.yoink.region.service.regionizer.density.partitioner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
/**
 * This class is to analyze Density Overlap Regions Indicator(DORI) for those
 * grid points in the intersection between QM core region and non-QM core region
 * based on Voronoi partitioning. If a grid point satisfies the DORI criteria,
 * the corresponding non-QM molecule will be switched into QM region. then we
 * can get qm region and adaptive qm region.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class DoriPartitioner extends InteractionPartitioner {

	@Resource
	private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	@Autowired
	private Computer<Double, DensityPoint> doriComputer;

	protected Region initialize(Map<Region.Name, Region> regions) {
		regionName = QM;
		adaptiveRegionName = QM_ADAPTIVE;
		Region region = simpleRegionFactory.create(regionName);
		region.addAll(regions.get(QM_CORE).getMolecularMap());
		return region;
	}

	/**
	 * for a grid point which satisfies the criteria: density is larger than
	 * densityThreshold and dori value is the range[doriThreshold, 1] ,the
	 * non-QM molecule of the grid point will be in QM region.
	 *
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link Region.Name }
	 *            as key, Region
	 *            {@link Region} as
	 *            value
	 * @param gridPoint
	 *            -{@link GridPoint }
	 * @param neighbours
	 *            - a Set of two molecules
	 *            {@link Molecule }
	 * @param parameters
	 *            - a Map, JobParameter
	 *            {@link JobParameter}
	 *            as Key, Object {@link java.lang.Object} as value
	 * @return region {@link Region}
	 */

	protected Region checkCriteria(Map<Region.Name, Region> regions,
			Region region, GridPoint gridPoint, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters) {
		Region.Name cubeRegionName = (Region.Name) parameters.get(REGION_CUBE);
		Set<Atom> atomsInCube = (new HashSet<Atom>(regions.get(cubeRegionName).getAtoms()));
		Set<Molecule> moleculesInCube = (Set<Molecule>) regions.get(cubeRegionName).getMolecules();
		calculateAndCheckDensity(atomsInCube, region, gridPoint, neighbours, parameters, moleculesInCube);
		return region;
	}

	private void calculateAndCheckDensity(Set<Atom> atomsInCube, Region region,
			GridPoint gridPoint, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters, Set<Molecule> moleculesInCube) {
		double density = calculateDensity(moleculesInCube, gridPoint);
		checkDensity(atomsInCube, region, gridPoint, neighbours, parameters,
				density);
	}

	private double calculateDensity(Set<Molecule> moleculesInCube, GridPoint gridPoint) {
		return densityCalculator.calculate(gridPoint.getCoordinate(), moleculesInCube);
	}

	private void checkDensity(Set<Atom> atomsInCube,
							  Region region,
							  GridPoint gridPoint,
							  Set<Molecule> neighbours,
							  Map<JobParameter, Object> parameters,
							  double density) {
		if (density >= (double) parameters.get(DENSITY_DORI)) {
			calculateAndCheckDori(atomsInCube, region, gridPoint, neighbours, parameters);
		}
	}

	private void calculateAndCheckDori(Set<Atom> atomsInCube, Region region,
			GridPoint gridPoint, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters) {
		double doriTemp = getDoriValue(atomsInCube, gridPoint);
		checkDoriValue(region, neighbours, parameters, doriTemp);
	}

	private double getDoriValue(Set<Atom> atomsInCube, GridPoint gridPoint) {
		DensityPoint densityPoint = densityPropertiesCalculator.calculate(atomsInCube, gridPoint.getCoordinate());
		return doriComputer.calculate(densityPoint);
	}

	private void checkDoriValue(Region region,
								Set<Molecule> neighbours,
								Map<JobParameter, Object> parameters,
								double dori) {
		if (1 >= dori && dori >= (double) parameters.get(DORI)) {
			for (Molecule molecule : neighbours) {
				region.addMolecule(molecule, molecule.getIndex());
			}

		}
	}

}