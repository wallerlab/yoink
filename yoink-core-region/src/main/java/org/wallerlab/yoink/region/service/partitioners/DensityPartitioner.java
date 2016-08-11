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

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.VoronoiPoint;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.api.service.density.DensityCalculator;

import static org.wallerlab.yoink.math.SetOps.*;
import static org.wallerlab.yoink.api.model.DensityPoint.DensityType.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;

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

	public Map<Region.Name,Set<MolecularSystem.Molecule>> partition(Job job) {
		Map<Region.Name,Set<MolecularSystem.Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		MolecularSystem molecularSystem = job.getMolecularSystem();
		Set<MolecularSystem.Molecule> qmCoreFixed   = job.getMolecularSystem().getMolecules("QM_CORE");

		List<Set<MolecularSystem.Molecule>> partitionedMolecules  = densityPartitioner(molecularSystem);
		Set<MolecularSystem.Molecule> moleculesInCoreSearch 	  = partitionedMolecules.get(0);
		Set<MolecularSystem.Molecule> moleculesInAdaptiveSearch   = partitionedMolecules.get(1);
		Set<MolecularSystem.Molecule> buffer			          = partitionedMolecules.get(2);
		Set<MolecularSystem.Molecule> stronglyBound = new HashSet<>();

		if(moleculesInCoreSearch.size() > 0)
			stronglyBound.addAll(stronglyBound(qmCoreFixed, moleculesInCoreSearch, molecularSystem));

		Set<MolecularSystem.Molecule> qmCore = union(qmCoreFixed, stronglyBound);
		Set<MolecularSystem.Molecule> weaklyBound = new HashSet<>();
		if(moleculesInAdaptiveSearch.size() > 0)
			weaklyBound.addAll(weaklyBound(qmCore, moleculesInAdaptiveSearch, molecularSystem));
		Set<MolecularSystem.Molecule> qmAdaptive = union(qmCore, weaklyBound);
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

	public List<Set<MolecularSystem.Molecule>> densityPartitioner(MolecularSystem molecularSystem) {
		Set<MolecularSystem.Molecule> asrQmCore = new HashSet<>();
		Set<MolecularSystem.Molecule> asrQm     = new HashSet<>();
		Set<MolecularSystem.Molecule> buffer    = new HashSet<>();

        //Careful I am assuming here that it is only the fixed. I think that is wrong.
		Set<MolecularSystem.Molecule> moleculesInQmCoreFixed = molecularSystem.getMolecules("QM_CORE_FIXED");
		Set<MolecularSystem.Molecule> moleculesInNonQmCore =new HashSet<MolecularSystem.Molecule>( molecularSystem.getMolecules());
        moleculesInNonQmCore.removeAll(moleculesInQmCoreFixed);

        //Molecular Density
		for(MolecularSystem.Molecule molecule: moleculesInNonQmCore){
			//Evaluate the density of the QM Core fixed at the center of mass for every other molecule.
			double density = densityCalculator.electronic(molecule.getCenterOfMass(), moleculesInQmCoreFixed);
			if (density >= asrQmCoreThreshold) asrQmCore.add(molecule);
			if (density >= asrQmThreshold) asrQm.add(molecule);
			if (density >= asrThreshold) buffer.add(molecule);
		}
		List<Set<MolecularSystem.Molecule>> asrs = new ArrayList<>();
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
	private Set<MolecularSystem.Molecule> stronglyBound(Set<MolecularSystem.Molecule> qmFixedMolecules,
							   Set<MolecularSystem.Molecule> searchMolecules,
							   MolecularSystem molecularSystem) {


        //This is ugly with all the predicates separate
        Predicate<VoronoiPoint> bothNotInQmFixed = gridPoint ->
                !qmFixedMolecules.containsAll(gridPoint.getNearestMolecules());

        Predicate<VoronoiPoint> density = gridPoint ->
                densityCalculator.atomic(gridPoint.getCoordinate(), gridPoint.getNearestAtoms()) > densitySedd;

        Predicate<VoronoiPoint> densityRatio = gridPoint -> {
            double atomPairDensityRatio = densityCalculator.electronic(gridPoint.getCoordinate(),
                                                                       gridPoint.getNearestAtoms());
            return ((atomPairDensityRatio >= densityRationMin && atomPairDensityRatio <= densityRatioMax));
        };

        Predicate<VoronoiPoint> atomicSedd = gridPoint ->
                densityCalculator.sedd(gridPoint.getCoordinate(),gridPoint.getNearestAtoms()) <= seddThreshold;

        Predicate<VoronoiPoint> molecularSedd = gridPoint ->
                densityCalculator.sedd(gridPoint.getCoordinate(),mapToAtoms(searchMolecules)) <= seddThreshold;

        List<VoronoiPoint> gridPoints = voronoizer.voronoize(SEDD, searchMolecules, molecularSystem);

        return (Set<MolecularSystem.Molecule>) gridPoints.stream()
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
	private static final double doriThreshold 	 = 0.9d;

	@SuppressWarnings("unchecked")
	private Set<MolecularSystem.Molecule> weaklyBound(Set<MolecularSystem.Molecule> qmCore,
							 Set<MolecularSystem.Molecule> searchMolecules,
							 MolecularSystem molecularSystem) {
		List<VoronoiPoint> gridPoints = voronoizer.voronoize(DORI, searchMolecules, molecularSystem );
        return (Set<MolecularSystem.Molecule>) gridPoints.stream()
				.filter(gridPoint -> !qmCore.containsAll(gridPoint.getNearestMolecules()))
				.filter(gridPoint -> {
                    double density = this.densityCalculator.electronic(gridPoint.getCoordinate(), searchMolecules);
					return (density >= doriDensityThreshold);
				})
				.filter(gridPoint -> {
                    double dori = this.densityCalculator.dori(gridPoint.getCoordinate(),mapToAtoms(searchMolecules));
					return (dori <= 1  && dori >= doriThreshold);
				})
				.flatMap(gridPoint -> gridPoint.getNearestMolecules().stream())
				.collect(toSet());
	}

	//convenience method
	private Set<MolecularSystem.Molecule.Atom> mapToAtoms(Set<MolecularSystem.Molecule> molecules){
		return molecules.stream()
		                .flatMap(molecule -> molecule.getAtoms().stream())
		                .collect(Collectors.toSet());
	}

}
