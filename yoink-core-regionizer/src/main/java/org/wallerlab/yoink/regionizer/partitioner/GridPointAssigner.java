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

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.cube.Assigner;

/**
 * This class is to determine if a grid point is in the intersection region
 * between QM core and non-QM core in adaptiveSearch region.
 * 
 * @author Min Zheng
 *
 */
@Component
public class GridPointAssigner implements
		Assigner<Coord, Map<Region.Name, Region>, Region.Name> {

	@Resource
	private Calculator<Map<String, Object>, Coord, Set<Molecule>> voronoiCalculator;

	/**
	 * for a grid point, get its two neighbours from Voronoi partitioning, if it
	 * satisfies the criteria(two neighbours are different, not both in QM core
	 * region or in non-QM core adaptive search region.), it will be used for
	 * following calculation of density interaction analysis(like DORI and
	 * SEDD).
	 * 
	 * @param tempCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}, the
	 *            coordinate of the grid point
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name }
	 *            as key, Region
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *            value
	 * @param cubeRegionName
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 * @return properties - a Map, String {@link java.lang.String} as Key,
	 *         Object {@link java.lang.Object} as value
	 */
	public Map assign(Coord tempCoord, Map<Region.Name, Region> regions,
			Region.Name cubeRegionName) {
		Region qmCoreRegion = regions.get(Region.Name.QM_CORE);
		Region nonQmCoreRegion = regions
				.get(Region.Name.NONQM_CORE_ADAPTIVE_SEARCH);
		Map<String, Object> properties = voronoiCalculator.calculate(tempCoord,
				regions.get(cubeRegionName).getMolecules());
		Set<Molecule> moleculeSet = (Set<Molecule>) properties
				.get("twoClosestMolecules");
		boolean notNeighbourPair = (boolean) (moleculeSet.size() != 2);
		switch (cubeRegionName) {
		case SYSTEM:
			if (notNeighbourPair) {
				properties.clear();
			}
			break;
		default:
			boolean bothNeighboursAreInNonQmCore = nonQmCoreRegion
					.containsAll(moleculeSet);
			boolean bothNeighboursAreInQmCore = qmCoreRegion
					.containsAll(moleculeSet);
			if (notNeighbourPair || bothNeighboursAreInNonQmCore
					|| bothNeighboursAreInQmCore) {
				properties.clear();
			}
		}
		return properties;
	}

}
