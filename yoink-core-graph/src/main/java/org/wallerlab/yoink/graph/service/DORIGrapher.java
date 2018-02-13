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
package org.wallerlab.yoink.graph.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.graph.Graph;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.graph.Grapher;
import org.wallerlab.yoink.api.service.molecule.FilesWriter;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.region.partitioner.DensityPartitioner;

/**
 * This class is to get all pairs having interaction(yes or no) base on DORI
 * analysis.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class DORIGrapher implements Grapher {
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

	@Autowired
	private DensityPartitioner densityPartitioner;

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	@Resource
	private Graph simpleGraph;

	@Value("${yoink.job.debug}")
	private boolean debug = false;

	@Value("${yoink.job.dis_cutoff}")
	private double dis_cutoff = 10.0;

	protected static final Log log = LogFactory.getLog(DORIGrapher.class);

	public DORIGrapher() {

	}

	public void graphing(Job job) {

		Map<JobParameter, Object> parameters = job.getParameters();
		Map<Region.Name, Region> regions = job.getRegions();

		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		simpleGraph.setEdges(new ArrayList<List<Integer>>());
		simpleGraph.setWeights(new ArrayList<Double>());
		if (partitionType == Partitioner.Type.INTERACTION) {
			List<List> interactionPairsAndWeightsList = getEdgesAndWeights(
					parameters, regions);

			simpleGraph.setEdges(interactionPairsAndWeightsList.get(0));

			simpleGraph.setWeights(interactionPairsAndWeightsList.get(1));

		}

		job.setGraph(simpleGraph);

	}

	private List<List> getEdgesAndWeights(Map<JobParameter, Object> parameters,
			Map<Region.Name, Region> regions) {
		List<List> interactionPairsAndWeightsList = new ArrayList<List>();
		Region.Name cubeRegionName = (Region.Name) parameters
				.get(JobParameter.REGION_CUBE);

		densityPartitioner.readWFC(parameters, regions
				.get((Region.Name) parameters.get(JobParameter.REGION_CUBE)));
		List<GridPoint> gridPoints = cubePartitioner.partition(regions,
				parameters, DensityType.DORI);

		interactionPairsAndWeightsList = calculateInteractionPairList(regions,
				parameters, gridPoints);

		List<Double> weightList = new ArrayList<Double>();
		if ((Boolean) parameters.get(JobParameter.INTERACTION_WEIGHT)) {
			weightList = interactionPairsAndWeightsList.get(1);
			double weightMin = Collections.min(weightList);
			double weightMax = Collections.max(weightList);
			double normal = 1.0 / (weightMax - weightMin);
			for (int i = 0; i < weightList.size(); i++) {

				weightList.set(i, weightList.get(i) * normal);
			}

		} else {

			Double[] weightArray = new Double[interactionPairsAndWeightsList
					.get(0).size()];
			Arrays.fill(weightArray, 1.0);

			weightList.addAll(Arrays.asList(weightArray));

		}
		interactionPairsAndWeightsList.set(1, weightList);
		return interactionPairsAndWeightsList;
	}

	private List<List> calculateInteractionPairList(
			Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, List<GridPoint> gridPoints) {
		
		Map<List<Integer>, Double> pairDensityMapTemp = new HashMap<List<Integer>, Double>();
		
		Map<List<Integer>, Double> pairDensityMap = Collections
				.synchronizedMap(pairDensityMapTemp);

		Region.Name cubeRegionName = (Region.Name) parameters
				.get(JobParameter.REGION_CUBE);

		Set<Molecule> moleculesInCube = (Set<Molecule>) regions.get(
				cubeRegionName).getMolecules();

		log.debug("before: DORIGrapher gridPoints.parallelStream().forEach"
				+ System.currentTimeMillis());
		log.debug("gridPoints size: " + gridPoints.size());

		gridPoints.parallelStream()
				.forEach(
						gridPoint -> {
							Set<Molecule> moleculesInCubeEff = gridPoint
									.getMolecules();

							Set<Atom> atomsInCubeEff = gridPoint.getAtoms();

							Set<Molecule> neighbours = gridPoint
									.getTwoClosestMolecules();

							List<Integer> pair = new ArrayList<Integer>();
							for (Molecule m : neighbours) {
								pair.add(m.getIndex());

							}
							Collections.sort(pair);
							Set<Integer> pairTemp = new HashSet<Integer>(pair);
							checkCriteria(atomsInCubeEff, 
									gridPoint,  pair, parameters,
									moleculesInCubeEff, pairDensityMap);

						});

		log.debug("after: DORIGrapher system.currentTimeMillis( )"
				+ System.currentTimeMillis());

		List<List> interactionAndWeightLists = new ArrayList<List>();
		List<List<Integer>> interactionList= new ArrayList<List<Integer>>();
		
		List<Double> weightList = new ArrayList<Double>();
	
		for(List<Integer> pair : pairDensityMap.keySet()){
			interactionList.add(pair);
			weightList.add(pairDensityMap.get(pair));
		}
		interactionAndWeightLists.add(interactionList);
		interactionAndWeightLists.add(weightList);
		return interactionAndWeightLists;
	}

	protected void checkCriteria(Set<Atom> atomsInCube,
			 GridPoint gridPoint, List<Integer> pair,
			Map<JobParameter, Object> parameters,
			Set<Molecule> moleculesInCube, 
			Map<List<Integer>, Double> pairDensityMap) {

		calculateAndCheckDensity(atomsInCube, gridPoint, 
				pair, parameters, moleculesInCube, 
				pairDensityMap);

	}

	private void calculateAndCheckDensity(Set<Atom> atomsInCube,
			 GridPoint gridPoint,List<Integer> pair,
			Map<JobParameter, Object> parameters,
			Set<Molecule> moleculesInCube, 
			Map<List<Integer>, Double> pairDensityMap) {
		double density = getDensity(moleculesInCube, gridPoint);
		checkDensity(atomsInCube,  gridPoint,  pair,
				parameters, density, pairDensityMap);
	}

	private double getDensity(Set<Molecule> moleculesInCube, GridPoint gridPoint) {
		// calculate density
		double density = densityCalculator.calculate(gridPoint.getCoordinate(),
				moleculesInCube);
		return density;
	}

	private void checkDensity(Set<Atom> atomsInCube,
			GridPoint gridPoint,List<Integer> pair,
			Map<JobParameter, Object> parameters, double density,
			Map<List<Integer>, Double> pairDensityMap) {
		// check density
		if (density >= (double) parameters.get(JobParameter.DENSITY_DORI)) {

			calculateAndCheckDori(atomsInCube,  gridPoint, 
					pair, parameters, density,
					pairDensityMap);
		}
	}

	private void calculateAndCheckDori(Set<Atom> atomsInCube,
			 GridPoint gridPoint,
			 List<Integer> pair,
			Map<JobParameter, Object> parameters, double density,
			Map<List<Integer>, Double> pairDensityMap) {
		double doriTemp = getDoriValue(atomsInCube, gridPoint);
		checkDoriValue(  pair, parameters, doriTemp,
				density, pairDensityMap);
	}

	private double getDoriValue(Set<Atom> atomsInCube, GridPoint gridPoint) {
		// calculate dori
		DensityPoint densityPoint = densityPropertiesCalculator.calculate(
				atomsInCube, gridPoint.getCoordinate());
		double doriTemp = densityOverlapRegionsIndicatorComputer
				.calculate(densityPoint);
		return doriTemp;
	}

	private void checkDoriValue(
			 List<Integer> pair,
			Map<JobParameter, Object> parameters, double doriTemp,
			double density, 
			Map<List<Integer>, Double> pairDensityMap) {

		// check dori

		if (1 >= doriTemp
				&& doriTemp >= (double) parameters.get(JobParameter.DORI)) {
			if (!pairDensityMap.containsKey(pair)) {
				log.debug("pair: "+pair+"  keys: "+pairDensityMap.keySet() + System.currentTimeMillis());
				pairDensityMap.put(pair, density);
				
			} else if ((Boolean) parameters
					.get(JobParameter.INTERACTION_WEIGHT)) {
				pairDensityMap.put(pair,
						density + pairDensityMap.get(pair));
				log.debug("calculating weight " + System.currentTimeMillis());
			}
			

		}

	}

}
