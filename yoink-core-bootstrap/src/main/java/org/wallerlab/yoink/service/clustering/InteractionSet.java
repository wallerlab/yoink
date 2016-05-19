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
package org.wallerlab.yoink.service.clustering;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.molecular.RadialGrid;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.clustering.LouvainClusteringFacade;
import org.wallerlab.yoink.molecular.domain.SimpleRadialGrid;
import org.wallerlab.yoink.regionizer.partitioner.DensityPartitioner;

/**
 * This class is to do density interaction analysis (dori/sedd ) for those grid
 * points in the intersection between QM core region and non-QM core region
 * based on Voronoi partition.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class InteractionSet {
	@Resource
	private Partitioner<List<GridPoint>, DensityType> cubePartitioner;

	@Resource
	protected Calculator<DensityPoint, Set<Atom>, Coord> densityPropertiesCalculator;

	@Resource
	private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	@Autowired
	private Computer<Double, DensityPoint> densityOverlapRegionsIndicatorComputer;

	@Resource
	protected FilesReader<RadialGrid, String> radialGridReader;

	@Resource
	private DensityPartitioner densityPartitioner;

	public InteractionSet() {

	}

	public Set<Set<Integer>> getDoriInteractionSet(
			Job job) {
		Map<JobParameter, Object> parameters = job.getParameters();
		Map<Region.Name, Region> regions = job.getRegions();
		Set<Set<Integer>> interactionSet = new HashSet<Set<Integer>>();
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType == Partitioner.Type.CLUSTER) {
			Region.Name cubeRegionName = (Region.Name) parameters
					.get(JobParameter.REGION_CUBE);

			densityPartitioner.readWFC(parameters,
					regions.get((Region.Name) parameters
							.get(JobParameter.REGION_CUBE)));
			List<GridPoint> gridPoints = cubePartitioner.partition(regions,
					parameters, DensityType.DORI);

			interactionSet = calculateInteractionPairSet(regions, parameters,
					gridPoints);
			System.out.println("interaction set: " + interactionSet.size());
			
		}
		job.SetInteractionSet(interactionSet);
		return interactionSet;
	}

	private Set<Set<Integer>> calculateInteractionPairSet(
			Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, List<GridPoint> gridPoints) {
		Set<Set<Integer>> tempSet = new HashSet<Set<Integer>>();
		Set<Set<Integer>> pairSet = Collections.synchronizedSet(tempSet);
		gridPoints.parallelStream().forEach(gridPoint -> {
			Set<Molecule> neighbours = gridPoint.getTwoClosestMolecules();
			Set<Integer> pair = new HashSet<Integer>();
			for (Molecule m : neighbours) {
				pair.add(m.getIndex());

			}
			// if the two closest molecules of a grid point
			// already
			// in the region, skip
			// this grid point
				if (!pairSet.containsAll(pair)) {

					checkCriteria(regions, pairSet, gridPoint, neighbours,
							pair, parameters);
				}
			});

		return pairSet;
	}

	protected void checkCriteria(Map<Region.Name, Region> regions,
			Set<Set<Integer>> pairSet, GridPoint gridPoint,
			Set<Molecule> neighbours, Set<Integer> pair,
			Map<JobParameter, Object> parameters) {
		Region.Name cubeRegionName = (Region.Name) parameters
				.get(JobParameter.REGION_CUBE);

		Set<Atom> atomsInCube = (new HashSet<Atom>(regions.get(cubeRegionName)
				.getAtoms()));
		Set<Molecule> moleculesInCube = (Set<Molecule>) regions.get(
				cubeRegionName).getMolecules();
		calculateAndCheckDensity(atomsInCube, pairSet, gridPoint, neighbours,
				pair, parameters, moleculesInCube);

	}

	private void calculateAndCheckDensity(Set<Atom> atomsInCube,
			Set<Set<Integer>> pairSet, GridPoint gridPoint,
			Set<Molecule> neighbours, Set<Integer> pair,
			Map<JobParameter, Object> parameters, Set<Molecule> moleculesInCube) {
		double density = getDensity(moleculesInCube, gridPoint);
		checkDensity(atomsInCube, pairSet, gridPoint, neighbours, pair,
				parameters, density);
	}

	private double getDensity(Set<Molecule> moleculesInCube, GridPoint gridPoint) {
		// calculate density
		double density = densityCalculator.calculate(gridPoint.getCoordinate(),
				moleculesInCube);
		return density;
	}

	private void checkDensity(Set<Atom> atomsInCube, Set<Set<Integer>> pairSet,
			GridPoint gridPoint, Set<Molecule> neighbours, Set<Integer> pair,
			Map<JobParameter, Object> parameters, double density) {
		// check density
		if (density >= (double) parameters.get(JobParameter.DENSITY_DORI)) {

			calculateAndCheckDori(atomsInCube, pairSet, gridPoint, neighbours,
					pair, parameters);
		}
	}

	private void calculateAndCheckDori(Set<Atom> atomsInCube,
			Set<Set<Integer>> pairSet, GridPoint gridPoint,
			Set<Molecule> neighbours, Set<Integer> pair,
			Map<JobParameter, Object> parameters) {
		double doriTemp = getDoriValue(atomsInCube, gridPoint);
		checkDoriValue(pairSet, neighbours, pair, parameters, doriTemp);
	}

	private double getDoriValue(Set<Atom> atomsInCube, GridPoint gridPoint) {
		// calculate dori
		DensityPoint densityPoint = densityPropertiesCalculator.calculate(
				atomsInCube, gridPoint.getCoordinate());
		double doriTemp = densityOverlapRegionsIndicatorComputer
				.calculate(densityPoint);
		return doriTemp;
	}

	private void checkDoriValue(Set<Set<Integer>> pairSet,
			Set<Molecule> neighbours, Set<Integer> pair,
			Map<JobParameter, Object> parameters, double doriTemp) {

		// check dori
		if (1 >= doriTemp
				&& doriTemp >= (double) parameters.get(JobParameter.DORI)) {
			pairSet.add(pair);

		}
	}

}
