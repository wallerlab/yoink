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
package org.wallerlab.yoink.region.partitioner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.api.service.region.RegionizerMath;
import org.wallerlab.yoink.region.domain.SimpleRegion;
import org.wallerlab.yoink.math.set.SetDifference;

/**
 * This class is to do density interaction analysis (dori/sedd ) for those grid
 * points in the intersection between QM core region and non-QM core region
 * based on Voronoi partition.
 * 
 * 
 * @author Min Zheng
 *
 */

public class InteractionPartitioner implements
		Partitioner<Map<Region.Name, Region>, List<GridPoint>> {

	@Resource
	protected RegionizerMath<Region, Region.Name> singleRegionizerService;

	@Resource
	protected Calculator<DensityPoint, Set<Atom>, Coord> densityPropertiesCalculator;

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	protected Region.Name regionName;

	protected Region.Name adaptiveRegionName;

	public Map<Region.Name, Region> partition(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, List<GridPoint> gridPoints) {
		findRegion(regions, parameters, gridPoints);
		findAdaptiveRegion(regions);
		return regions;
	}

	protected void findRegion(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, List<GridPoint> gridPoints) {
		Region region = initialize(regions);
		loopOverGridPoints(regions, region, gridPoints, parameters);
		regions.put(region.getName(), region);
	}

	protected void findAdaptiveRegion(Map<Region.Name, Region> regions) {
		Region adaptiveRegion = singleRegionizerService.regionize(regions,
				adaptiveRegionName);
		regions.put(adaptiveRegion.getName(), adaptiveRegion);
	}

	// rewrite in child class
	protected Region initialize(Map<Region.Name, Region> regions) {
		Region region = simpleRegionFactory.create();
		return region;
	}

	protected Region loopOverGridPoints(Map<Region.Name, Region> regions,
			Region region, List<GridPoint> gridPoints,
			Map<JobParameter, Object> parameters) {
		//Set<Molecule> neighboursets = new HashSet<Molecule>();
		//Set<Molecule> synneighboursets = Collections.synchronizedSet(neighboursets);
		
		gridPoints.parallelStream().forEach(gridPoint -> {
			Set<Molecule> neighbours = gridPoint.getTwoClosestMolecules();
			//synneighboursets.addAll(neighbours);

				if (!region.containsAll(neighbours)) {
					checkCriteria(regions, region, gridPoint, neighbours,
							parameters);
				}
			});
	//Set<Molecule>	neighbour_molecules = SetDifference.diff(synneighboursets,regions.get(Region.Name.QM_CORE).getMolecules());
	//Set<Molecule> interacting_molecules = SetDifference.diff(region.getMolecules(),regions.get(Region.Name.QM_CORE).getMolecules());

	//System.out.println("interacting_molecules/neighbour_molecules  "+ interacting_molecules.size()  + "/"+neighbour_molecules.size() );

		return region;
	}

	// rewrite in child class
	protected Region checkCriteria(Map<Region.Name, Region> regions,
			Region region, GridPoint gridPoint, Set<Molecule> neighbours,
			Map<JobParameter, Object> parameters) {
		return region;
	}

}
