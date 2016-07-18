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
package org.wallerlab.yoink.cluster.service.interaction;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.density.DensityCalculator;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.density.domain.RadialGrid;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.api.service.molecule.FilesReader;
import org.wallerlab.yoink.region.service.partitioners.DensityPartitioner;

/**
 * This class is to get all pairs having interaction
 * (yes or no) base on DORI analysis.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class InteractionSet {

	@Resource
	private Voronoizer voronoizer;


	@Resource
	private DensityCalculator densityCalculator;


	protected FilesReader<RadialGrid, String> radialGridReader;

	@Resource
	private DensityPartitioner densityPartitioner;

	public InteractionSet() {
	}

	public void getDoriInteractionSet(Job job) {
		Map<Job.JobParameter, Object> parameters = job.getParameters();
		Map<Region.Name, Region> regions = job.getRegions();

		List<List> interactionAndWeightLists = new ArrayList<List>();
		Regionizer.Type partitionType = (Regionizer.Type) parameters
				.get(Job.JobParameter.PARTITIONER);
		if (partitionType == Regionizer.Type.CLUSTER) {
			Region.Name cubeRegionName = (Region.Name) parameters
					.get(Job.JobParameter.REGION_CUBE);

			//densityPartitioner.readWFC(parameters, regions.get((Region.Name) parameters
			//				.get(JobParameter.REGION_CUBE)));
			List<VoronoiPoint> gridPoints = voronoizer.voronoize(DensityType.DORI, job.getRegion(cubeRegionName).getMolecules(), job.getMolecularSystem());
			interactionAndWeightLists = calculateInteractionPairList(regions, parameters, gridPoints);

		

		List<Double> weightList = new ArrayList<Double>();
		if ((Boolean) parameters.get(Job.JobParameter.INTERACTION_WEIGHT)) {
			weightList = interactionAndWeightLists.get(1);
			double weightMin = Collections.min(weightList);
			double weightMax = Collections.max(weightList);
			double normal = 1.0 / (weightMax - weightMin);
			for (int i = 0; i < weightList.size(); i++) {

				weightList.set(i, weightList.get(i) * normal);
			}

		} else {

			Double[] weightArray = new Double[interactionAndWeightLists.get(0)
					.size()];
			Arrays.fill(weightArray, 1.0);

			weightList.addAll(Arrays.asList(weightArray));

		}
		job.SetInteractionList(interactionAndWeightLists.get(0));
		job.SetInteractionWeight(weightList);
		}
	}

	private List<List> calculateInteractionPairList(
			Map<Region.Name, Region> regions,
			Map<Job.JobParameter, Object> parameters, List<VoronoiPoint> gridPoints) {
		List<List<Integer>> interactionListTemp = new ArrayList<List<Integer>>();
		Set<Set<Integer>> interactionSetTemp = new HashSet<Set<Integer>>();
		List<Double> weightListTemp = new ArrayList<Double>();

		List<List<Integer>> interactionList = Collections
				.synchronizedList(interactionListTemp);

		List<Double> weightList = Collections.synchronizedList(weightListTemp);
		Set<Set<Integer>> interactionSet = Collections
				.synchronizedSet(interactionSetTemp);
		gridPoints.parallelStream().forEach(
				gridPoint -> {
					Set<MolecularSystem.Molecule> neighbours = gridPoint
							.getNearestMolecules();

					List<Integer> pair = new ArrayList<Integer>();
					for (MolecularSystem.Molecule m : neighbours) {
						pair.add(m.getIndex());

					}
					Collections.sort(pair);
					Set<Integer> pairTemp = new HashSet<Integer>(pair);

					if ((Boolean) parameters
							.get(Job.JobParameter.INTERACTION_WEIGHT)) {
						checkCriteria(regions, interactionList, gridPoint,
								neighbours, pair, parameters, weightList,
								interactionSet);
					} else {
						if (!interactionSet.contains(pairTemp)) {

							checkCriteria(regions, interactionList, gridPoint,
									neighbours, pair, parameters, weightList,
									interactionSet);
						}
					}
				});
		List<List> interactionAndWeightLists = new ArrayList<List>();
		interactionAndWeightLists.add(interactionList);
		interactionAndWeightLists.add(weightList);
		return interactionAndWeightLists;
	}

	protected void checkCriteria(Map<Region.Name, Region> regions,
								 List<List<Integer>> pairList, VoronoiPoint gridPoint,
								 Set<MolecularSystem.Molecule> neighbours, List<Integer> pair,
								 Map<Job.JobParameter, Object> parameters, List<Double> weightList,
								 Set<Set<Integer>> interactionSet) {
		Region.Name cubeRegionName = (Region.Name) parameters
				.get(Job.JobParameter.REGION_CUBE);

		Set<MolecularSystem.Molecule.Atom> atomsInCube = (new HashSet<MolecularSystem.Molecule.Atom>(regions.get(cubeRegionName)
				.getAtoms()));
		Set<MolecularSystem.Molecule> moleculesInCube = (Set<MolecularSystem.Molecule>) regions.get(
				cubeRegionName).getMolecules();
		calculateAndCheckDensity(atomsInCube, pairList, gridPoint, neighbours,
				pair, parameters, moleculesInCube, weightList, interactionSet);

	}

	private void calculateAndCheckDensity(Set<MolecularSystem.Molecule.Atom> atomsInCube,
										  List<List<Integer>> pairList, VoronoiPoint gridPoint,
										  Set<MolecularSystem.Molecule> neighbours, List<Integer> pair,
										  Map<Job.JobParameter, Object> parameters,
										  Set<MolecularSystem.Molecule> moleculesInCube, List<Double> weightList,
										  Set<Set<Integer>> interactionSet) {
		double density = getDensity(moleculesInCube, gridPoint);
		checkDensity(atomsInCube, pairList, gridPoint, neighbours, pair,
				parameters, density, weightList, interactionSet);
	}

	private double getDensity(Set<MolecularSystem.Molecule> moleculesInCube, VoronoiPoint gridPoint) {
		return  densityCalculator.electronic(gridPoint.getCoordinate(), moleculesInCube);
	}

	private void checkDensity(Set<MolecularSystem.Molecule.Atom> atomsInCube,
							  List<List<Integer>> pairList, VoronoiPoint gridPoint,
							  Set<MolecularSystem.Molecule> neighbours, List<Integer> pair,
							  Map<Job.JobParameter, Object> parameters, double density,
							  List<Double> weightList, Set<Set<Integer>> interactionSet) {
		// check density
		if (density >= (double) parameters.get(Job.JobParameter.DENSITY_DORI)) {
			calculateAndCheckDori(atomsInCube, pairList, gridPoint, neighbours,
					pair, parameters, density, weightList, interactionSet);
		}
	}

	private void calculateAndCheckDori(Set<MolecularSystem.Molecule.Atom> atomsInCube,
									   List<List<Integer>> pairList, VoronoiPoint gridPoint,
									   Set<MolecularSystem.Molecule> neighbours, List<Integer> pair,
									   Map<Job.JobParameter, Object> parameters, double density,
									   List<Double> weightList, Set<Set<Integer>> interactionSet) {
		double doriTemp = getDoriValue(atomsInCube, gridPoint);
		checkDoriValue(pairList, neighbours, pair, parameters, doriTemp,
				density, weightList, interactionSet);
	}

	private double getDoriValue(Set<MolecularSystem.Molecule.Atom> atomsInCube, VoronoiPoint gridPoint) {
		return densityCalculator.dori(gridPoint.getCoordinate(), atomsInCube );
	}

	private void checkDoriValue(List<List<Integer>> pairList,
								Set<MolecularSystem.Molecule> neighbours, List<Integer> pair,
								Map<Job.JobParameter, Object> parameters, double doriTemp,
								double density, List<Double> weightList,
								Set<Set<Integer>> interactionSet) {

		// check dori
		if (1 >= doriTemp
				&& doriTemp >= (double) parameters.get(Job.JobParameter.DORI)) {
			// Boolean itemInList = checkItemInList(pairList, pair);
			Set<Integer> pairTemp = new HashSet<Integer>(pair);
			if (!interactionSet.contains(pairTemp)) {
				interactionSet.add(pairTemp);
				pairList.add(pair);
				weightList.add(density);
			} else {
				int index = pairList.indexOf(pair);
				weightList.set(index, weightList.get(index) + density);
			}

		}
	}

	private Boolean checkItemInList(List<List<Integer>> interactionList,List<Integer> pair) {
		Boolean itemInList = false;
		for (List list : interactionList) {
			if (list.containsAll(pair)) {
				itemInList = true;
				break;
			}
		}
		return itemInList;
	}

}
