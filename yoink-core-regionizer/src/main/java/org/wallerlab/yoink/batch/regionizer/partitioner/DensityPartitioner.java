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
package org.wallerlab.yoink.batch.regionizer.partitioner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.batch.api.model.density.DensityPoint;
import org.wallerlab.yoink.batch.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.batch.api.model.molecular.Atom;
import org.wallerlab.yoink.batch.api.model.molecular.Coord;
import org.wallerlab.yoink.batch.api.model.molecular.Molecule;
import org.wallerlab.yoink.batch.api.model.molecular.RadialGrid;
import org.wallerlab.yoink.batch.api.model.regionizer.Region;
import org.wallerlab.yoink.batch.api.service.Calculator;
import org.wallerlab.yoink.batch.api.service.Factory;
import org.wallerlab.yoink.batch.api.service.molecular.FilesReader;
import org.wallerlab.yoink.batch.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.batch.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.batch.molecular.domain.SimpleRadialGrid;

/**
 * This class is to use the density of qm core to define the region (adaptive
 * search region) to look for the potential QM core or QM adaptive molecules.
 * Also we can get NONQM_CORE_ADAPTIVE_SEARCH region and buffer region
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityPartitioner implements
		Partitioner<Map<Region.Name, Region>, DensityType> {

	@Resource
	private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

	@Resource
	private RegionizerMath<Region, Region.Name> singleRegionizerService;

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	@Resource
	protected FilesReader<RadialGrid, String> radialGridReader;

	/**
	 * based on the density of QM core , define adaptive search region , the
	 * non-QM core molecules in adaptive search region, and buffer region.
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
	 *            as Key, Object {@link java.lang.Object} as value
	 * @param densityType
	 *            {@link DensityPoint.DensityType}
	 * @return regions - a Map, Region.Name
	 *         {@link Region.Name } as
	 *         key, Region
	 *         {@link Region} as value
	 */
	public Map<Region.Name, Region> partition(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, DensityType densityType) {
		// the density parameters to define adaptive search region for adaptive
		// QM, adaptive search region for adaptive QM core, and adaptive search
		// region for buffer are different.
		double densityThreshold = getDensityThreshold(parameters, densityType);
		calculateAdaptiveSearchRegion(regions, densityThreshold, parameters);
		calculateNonQMCoreInAdaptiveSearchRegion(regions);
		// define buffer region by density
		calculateBufferRegion(regions, densityType);
		return regions;

	}

	private double getDensityThreshold(Map<JobParameter, Object> parameters,
			DensityType densityType) {
		double densityThreshold;
		switch (densityType) {
		case SEDD:
			densityThreshold = (double) parameters
					.get(JobParameter.DENSITY_ASR_QMCORE);
			break;
		case DORI:
			densityThreshold = (double) parameters
					.get(JobParameter.DENSITY_ASR_QM);
			break;
		case ELECTRONIC:
			// define buffer region by QM core density analysis
			densityThreshold = (double) parameters
					.get(JobParameter.DENSITY_BUFFER);
			break;
		default:
			throw new IllegalArgumentException("invalid  name");
		}
		return densityThreshold;
	}

	private void calculateAdaptiveSearchRegion(
			Map<Region.Name, Region> regions, double densityThreshold,
			Map<JobParameter, Object> parameters) {
		// initialize wfc for qm_core
		readWFC(parameters, regions.get(Region.Name.QM_CORE));
		Region adaptiveSearchRegion = findAdaptiveSearchRegionInNonQmCoreRegion(
				regions, densityThreshold);
		// add QM core molecules
		adaptiveSearchRegion.addAll(regions.get(Region.Name.QM_CORE)
				.getMolecularMap());
		// initialize wfc for adaptive search region
		readWFC(parameters, adaptiveSearchRegion);
		regions.put(adaptiveSearchRegion.getName(), adaptiveSearchRegion);

	}

	public   void readWFC(Map<JobParameter, Object> parameters,
			Region adaptiveSearchRegion) {
		if ((boolean) parameters.get(JobParameter.DGRID) == true) {

			List<Atom> atoms = adaptiveSearchRegion.getAtoms();

			atoms.parallelStream().forEach(
					atom -> {
						if (atom.getRadialGrid() == null) {
							RadialGrid grid = new SimpleRadialGrid();
							String wfc_name = atom.getElementType().toString()
									.toLowerCase();
							if (wfc_name.length() == 1) {
								wfc_name = wfc_name + "_";
							}
							String wfc_file = parameters
									.get(JobParameter.WFC_PATH)
									+ "/"
									+ wfc_name + "_lda.wfc";
							grid = radialGridReader.read(wfc_file, grid);
							atom.setRadialGrid(grid);
						}
					});

		}
	}

	private void calculateNonQMCoreInAdaptiveSearchRegion(
			Map<Region.Name, Region> regions) {
		Region nonQMCoreAdaptiveSearchRegion = singleRegionizerService
				.regionize(regions, Region.Name.NONQM_CORE_ADAPTIVE_SEARCH);
		regions.put(Region.Name.NONQM_CORE_ADAPTIVE_SEARCH,
				nonQMCoreAdaptiveSearchRegion);
	}

	private Region findAdaptiveSearchRegionInNonQmCoreRegion(
			Map<Region.Name, Region> regions, double densityThreshold) {
		Region adaptiveSearchRegion = simpleRegionFactory
				.create(Region.Name.ADAPTIVE_SEARCH);
		List<Molecule> moleculesInAdaptiveSearch = Collections
				.synchronizedList(new ArrayList<Molecule>());
		Set<Molecule> moleculesInNonQmCore = regions
				.get(Region.Name.NONQM_CORE).getMolecules();
		Set<Molecule> moleculesInQmCore = regions.get(Region.Name.QM_CORE)
				.getMolecules();
		checkEveryNonQMCoreMolecule(densityThreshold,
				moleculesInAdaptiveSearch, moleculesInNonQmCore,
				moleculesInQmCore);
		for (Molecule molecule : moleculesInAdaptiveSearch) {
			adaptiveSearchRegion.addMolecule(molecule, molecule.getIndex());
		}
		return adaptiveSearchRegion;
	}

	private void checkEveryNonQMCoreMolecule(double densityThreshold,
			List<Molecule> moleculesInAdaptiveSearch,
			Set<Molecule> moleculesInNonQmCore, Set<Molecule> moleculesInQmCore) {

		moleculesInNonQmCore.parallelStream().forEach(
				molecule -> {
					Coord centerOfMass = molecule.getCenterOfMass();
					double density = densityCalculator.calculate(centerOfMass,
							moleculesInQmCore);
					if (density >= densityThreshold) {

						moleculesInAdaptiveSearch.add(molecule);
					}
				});
	}

	private void calculateBufferRegion(Map<Region.Name, Region> regions,
			DensityType densityType) {
		if (densityType == DensityType.ELECTRONIC) {
			Region bufferRegion = singleRegionizerService.regionize(regions,
					Region.Name.BUFFER);
			regions.put(bufferRegion.getName(), bufferRegion);

		}
	}

}
