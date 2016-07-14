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
package org.wallerlab.yoink.region.service.partitioners;

import org.wallerlab.yoink.api.model.batch.Job;

import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Molecule;

import org.wallerlab.yoink.api.model.cube.VoronoiPoint;
import org.wallerlab.yoink.api.service.cube.Voronoizer;

import org.wallerlab.yoink.api.service.density.DensityCalculator;
import static org.wallerlab.yoink.api.model.density.DensityPoint.DensityType.*;

import static org.wallerlab.yoink.math.set.SetOps.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.stream.Collectors.toSet;

/**
 * This class is to use the density of qm core to define the region (adaptive
 * search region) to look for the potential QM core or QM adaptive molecules.
 * Also we can get NON_QM_CORE_ADAPTIVE_SEARCH region and buffer region
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityPartitioner implements Partitioner{

	@Autowired private Voronoizer voronoizer;

	@Autowired private DensityCalculator densityCalculator;

	public Map<Region.Name,Set<Molecule>> partition(Job job) {

		Map<Region.Name,Set<Molecule>> qmAdaptiveAndBuffer = new HashMap<>();

		MolecularSystem molecularSystem = job.getMolecularSystem();
		Set<Molecule> qmCoreFixed   = job.getMolecularSystem().getMolecules("QM_CORE");

		List<Set<Molecule>> partitionedMolecules  = densityPartitioner(molecularSystem);
		Set<Molecule> moleculesInCoreSearch 	  = partitionedMolecules.get(0);
		Set<Molecule> moleculesInAdaptiveSearch   = partitionedMolecules.get(1);
		Set<Molecule> buffer					  = partitionedMolecules.get(2);

		Set<Molecule> stronglyBound = stronglyBound(qmCoreFixed, moleculesInCoreSearch, molecularSystem);
		Set<Molecule> qmCore        = union(qmCoreFixed, stronglyBound);

		Set<Molecule> weaklyBound   = weaklyBound(qmCore, moleculesInAdaptiveSearch, molecularSystem);
		Set<Molecule> qmAdaptive    = union(qmCore, weaklyBound);

		qmAdaptiveAndBuffer.put(QM_ADAPTIVE, qmAdaptive);
		qmAdaptiveAndBuffer.put(BUFFER,buffer);
		return qmAdaptiveAndBuffer;
	}

	/**
	 * based on the density of QM core:
	 * - adaptive search region
	 * - non-QM core molecules in adaptive search region
	 * - and buffer region.
	 *
	 * @param job
	 *            - a Map, RegionEnum.Name
	 *            {@link Region.Name }
	 *            as key, RegionEnum
	 *            {@link Region} as
	 *            value

	 * @return regions - a Map, RegionEnum.Name
	 *         {@link Region.Name } as
	 *         key, RegionEnum
	 *         {@link Region} as value
	 */
	private static final double asrQmCoreThreshold = 0.1d;
	private static final double asrQmThreshold	   = 0.0001d;
	private static final double asrThreshold 	   = 0.000001d;

	public List<Set<Molecule>> densityPartitioner(MolecularSystem molecularSystem) {

		Set<Molecule> asrQmCore = new HashSet<>();
		Set<Molecule> asrQm     = new HashSet<>();
		Set<Molecule> buffer    = new HashSet<>();

		//Careful I am assuming here that it is only the fixed. I think that is wrong.
		Set<Molecule> moleculesInQmCoreFixed = molecularSystem.getMolecules("QM_CORE");
		Set<Molecule> moleculesInNonQmCore = new HashSet<Molecule>( molecularSystem.getMolecules());
				      moleculesInNonQmCore.removeAll(moleculesInQmCoreFixed);

		for(Molecule molecule: moleculesInNonQmCore){
			//Evaluate the density of the QM Core fixed at the center of mass for every other molecule.
			double density = densityCalculator.electronic(molecule.getCenterOfMass(), moleculesInQmCoreFixed);
			if (density >= asrQmCoreThreshold) asrQmCore.add(molecule);
			if (density >= asrQmThreshold) asrQm.add(molecule);
			if (density >= asrThreshold) buffer.add(molecule);
		}

		List<Set<Molecule>> asrs = new ArrayList<>();
		asrs.add(asrQmCore);
		asrs.add(asrQm);
		asrs.add(buffer);
		return asrs;
	}

	/**
	 * This class is to analyze SEDD for those grid points in the intersection
	 * between QM core region and non-QM core region based on Voronoi partitioning.
	 * If a grid point satisfies the SEDD criteria, the corresponding non-QM
	 * molecule will treated as QM core.
	 *
	 * @author Min Zheng
	 *
	 */
	/**
	 * This method is to get QM core region and QM core adaptive region . for a
	 * grid point, if its SEDD from molecules in cube is larger than
	 * seddThreshold, and the sedd value from its two closest atoms is larger
	 * than seddThreshold and densityRatio is the range
	 * [densityRatioMin,densityRatioMax] ,then the non-QM molecule of the grid
	 * point should be in QM core region.
	 */
	private static final double densitySedd      = 0.1d;
	private static final double densityRationMin = 0.064d;
	private static final double densityRatioMax  = 15.67;
	private static final double seddThreshold  	 = 2;

	@SuppressWarnings("unchecked")
	public Set<Molecule> stronglyBound(Set<Molecule> qmFixedMolecules, Set<Molecule> searchMolecules, MolecularSystem molecularSystem) {

		Predicate<VoronoiPoint> bothNotInQmFixed = gridPoint -> qmFixedMolecules.containsAll(gridPoint.getNearestMolecules());

		Predicate<VoronoiPoint> density = gridPoint ->
			 densityCalculator.atomPair(gridPoint.getCoordinate(), gridPoint.getNearestAtoms()) > densitySedd;

		Predicate<VoronoiPoint> densityRatio = gridPoint -> {
			double atomPairDensityRatio = densityCalculator.electronic(gridPoint.getCoordinate(), gridPoint.getNearestAtoms());
			return ((atomPairDensityRatio >= densityRationMin && atomPairDensityRatio <= densityRatioMax));
		};

		Predicate<VoronoiPoint> atomicSedd = gridPoint ->
			densityCalculator.sedd(gridPoint.getCoordinate(),gridPoint.getNearestAtoms()) <= seddThreshold;

		Predicate<VoronoiPoint> molecularSedd = gridPoint ->
			densityCalculator.sedd(gridPoint.getCoordinate(),mapToAtoms(searchMolecules)) <= seddThreshold;


		List<VoronoiPoint> gridPoints = voronoizer.voronoize(SEDD, searchMolecules, molecularSystem);

		return (Set<Molecule>) gridPoints.stream()
									 	 .filter(bothNotInQmFixed)
										 .filter(density)
						 			     .filter(densityRatio)
									     .filter(atomicSedd)
				  			 		     .filter(molecularSedd)
						 				 .flatMap(gridPoint -> gridPoint.getNearestMolecules().stream())
						 				 .collect(toSet());
	}

	/**
	 * This class is to analyze Density Overlap RegionEnum Indicator(DORI) for those
	 * grid points in the intersection between QM core region and non-QM core region
	 * based on Voronoi partitioning. If a grid point satisfies the DORI criteria,
	 * the corresponding non-QM molecule will be switched into QM region. then we
	 * can get qm region and adaptive qm region.
	 *
	 * for a grid point which satisfies the criteria:
	 * - density is larger than densityThreshold
	 * - dori value is the range[doriThreshold, 1],
	 * the non-QM molecule of this grid point
	 * will be added to a set, and returned.
	 * This is used to define the QM_ADAPTIVE region.
	 *
	 *
	 * @param qmCore, moleculesInAdaptiveSearch, and the molecularSystem
	 *
	 * @return set of molecules that are weakly bound
	 */
	private static final double doriDensityThreshold = 0.0001d;
	private static final double doriThreshold 		 = 0.9d;

	@SuppressWarnings("unchecked")
	public Set<Molecule> weaklyBound(Set<Molecule> qmCore, Set<Molecule> searchMolecules, MolecularSystem molecularSystem) {

		List<VoronoiPoint> gridPoints = voronoizer.voronoize(DORI, searchMolecules, molecularSystem );

		return (Set<Molecule>) gridPoints.stream()
				.filter(gridPoint -> qmCore.containsAll(gridPoint.getNearestMolecules()))
				.filter(gridPoint -> {
					double density = this.densityCalculator.electronic(gridPoint.getCoordinate(), searchMolecules);
					return (density >= doriDensityThreshold);
				})
				.filter(gridPoint -> {
					double dori = this.densityCalculator.dori(gridPoint.getCoordinate(),mapToAtoms(searchMolecules));
					return (1 >= dori && dori >= doriThreshold);
				})
				.flatMap(gridPoint -> gridPoint.getNearestMolecules().stream())
				.collect(toSet());
	}

	//convenience method
	private Set<Atom> mapToAtoms(Set<Molecule> molecules){
		return molecules.stream().flatMap(molecule -> molecule.getAtoms().stream()).collect(Collectors.toSet());
	}

}