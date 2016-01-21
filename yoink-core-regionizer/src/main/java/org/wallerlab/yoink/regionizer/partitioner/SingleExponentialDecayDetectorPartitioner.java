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
package org.wallerlab.yoink.regionizer.partitioner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;

/**
 * This class is to analyze SEDD for those grid points in the intersection
 * between QM core region and non-QM core region based on Voronoi partitioning.
 * If a grid point satisfies the SEDD criteria, the corresponding non-QM
 * molecule will treated as QM core.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SingleExponentialDecayDetectorPartitioner extends
		InteractionPartitioner {

	@Resource
	private Calculator<Double, Coord, Atom[]> atomicDensityRatioCalculator;

	@Resource
	private Calculator<Double, Coord, Atom> atomDensityCalculator;

	@Resource
	private Computer<Double, DensityPoint> singleExponentialDecayDetectorComputer;

	protected Region initialize(Map<Region.Name, Region> regions) {
		regionName = Region.Name.QM_CORE;
		adaptiveRegionName = Region.Name.QM_CORE_ADAPTIVE;
		Region region = regions.get(regionName);
		return region;
	}

	/**
	 * This method is to get QM core region and QM core adaptive region . for a
	 * grid point, if its SEDD from molecules in cube is larger than
	 * seddThreshold, and the sedd value from its two closest atoms is larger
	 * than seddThreshold and densityRatio is the range
	 * [densityRatioMin,densityRatioMax] ,then the non-QM molecule of the grid
	 * point should be in QM core region.
	 *
	 * 
	 * 
	 * 
	 */
	protected Region checkCriteria(Map<Region.Name, Region> regions,
			Region region, GridPoint gridPoint, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters) {

		Set<Atom> neighbourAtoms = gridPoint.getTwoClosestAtoms();
		Coord coordinate = gridPoint.getCoordinate();
		double densityOfTwoAtoms = getTwoAtomsDensity(neighbourAtoms,
				coordinate);
		checkTwoAtomsDensity(regions, region, neighbours, parameters,
				neighbourAtoms, coordinate, densityOfTwoAtoms);
		return region;
	}

	private double getTwoAtomsDensity(Set<Atom> neighbourAtoms, Coord coordinate) {
		// calculate density from two atoms
		double densityOfTwoAtoms = 0;
		for (Atom atom : neighbourAtoms) {
			densityOfTwoAtoms = atomDensityCalculator.calculate(coordinate,
					atom) + densityOfTwoAtoms;
		}
		return densityOfTwoAtoms;
	}

	private void checkTwoAtomsDensity(Map<Region.Name, Region> regions,
			Region region, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters, Set<Atom> neighbourAtoms,
			Coord coordinate, double densityOfTwoAtoms) {
		// ignore those grid points where the density of two closest atoms are
		// quite low
		if (densityOfTwoAtoms >= (double) parameters
				.get(JobParameter.DENSITY_SEDD)) {
			double densityRatioOfTwoAtoms = calculateTwoAtomsDensityRatio(
					neighbourAtoms, coordinate);
			checkTwoAtomsDensityRatio(regions, region, neighbours, parameters,
					neighbourAtoms, coordinate, densityRatioOfTwoAtoms);
		}
	}

	private double calculateTwoAtomsDensityRatio(Set<Atom> neighbourAtoms,
			Coord coordinate) {
		// calculate density ratio of two closest atoms of a grid point
		Atom[] twoAtoms = neighbourAtoms.toArray(new Atom[2]);
		double densityRatioOfTwoAtoms = atomicDensityRatioCalculator.calculate(
				coordinate, twoAtoms);
		return densityRatioOfTwoAtoms;
	}

	private void checkTwoAtomsDensityRatio(Map<Region.Name, Region> regions,
			Region region, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters, Set<Atom> neighbourAtoms,
			Coord coordinate, double densityRatioOfTwoAtoms) {
		// check density ratio of two closest atoms of a grid point
		if (densityRatioOfTwoAtoms >= (double) parameters
				.get(JobParameter.DENSITY_RATIO_MIN)
				&& densityRatioOfTwoAtoms <= (double) parameters
						.get(JobParameter.DENSITY_RATIO_MAX)) {
			double seddAtom = calculateTwoAtomsSedd(neighbourAtoms, coordinate);
			checkTwoAtomsSedd(regions, region, neighbours, parameters,
					coordinate, seddAtom);
		}
	}

	private double calculateTwoAtomsSedd(Set<Atom> neighbourAtoms,
			Coord coordinate) {
		// calculate sedd value of two closest atoms of a grid point
		DensityPoint densityPoint = densityPropertiesCalculator.calculate(
				neighbourAtoms, coordinate);
		double seddAtom = singleExponentialDecayDetectorComputer
				.calculate(densityPoint);
		return seddAtom;
	}

	private void checkTwoAtomsSedd(Map<Region.Name, Region> regions,
			Region region, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters, Coord coordinate,
			double seddAtom) {
		// check sedd value from two closest atoms
		if (seddAtom <= (double) parameters.get(JobParameter.SEDD)) {
			double seddMoleculeTemp = calculateSeddFromMoleculesInCube(regions,
					parameters, coordinate);
			checkSeddFromMoleculesInCube(region, neighbours, parameters,
					seddMoleculeTemp);
		}
	}

	private double calculateSeddFromMoleculesInCube(
			Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, Coord coordinate) {
		// calculate sedd from all molecules in cube
		Set<Atom> atomsInCube = (new HashSet<Atom>(regions.get(
				(Region.Name) parameters.get(JobParameter.REGION_CUBE))
				.getAtoms()));
		DensityPoint densityPoint = densityPropertiesCalculator.calculate(
				atomsInCube, coordinate);
		double seddMoleculeTemp = singleExponentialDecayDetectorComputer
				.calculate(densityPoint);
		return seddMoleculeTemp;
	}

	private void checkSeddFromMoleculesInCube(Region region,
			Set<Molecule> neighbours, Map<JobParameter, Object> parameters,
			double seddMoleculeTemp) {
		// check sedd value from all molecules in cube
		if (seddMoleculeTemp <= (double) parameters.get(JobParameter.SEDD)) {
			for (Molecule molecule : neighbours) {
				region.addMolecule(molecule, molecule.getIndex());
			}

		}
	}

}
