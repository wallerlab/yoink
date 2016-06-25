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

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.*;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.region.RegionizerMath;

import javax.annotation.Resource;
import java.util.*;

import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

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
public class DoriPartitioner implements Partitioner<Map<Region.Name, Region>, List<GridPoint>> {

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	@Resource
	private RegionizerMath<Region, Region.Name> singleRegionizerService;

	@Resource
	private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

	@Resource
	protected Calculator<DensityPoint, Set<Atom>, Coord> densityPropertiesCalculator;

	@Resource
	private Computer<Double, DensityPoint> doriComputer;

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

	@Override
	public List<Region> partition(List<Region> regions,
								  List<JobParameter> parameters) {



		//STEP 2. cube partitioner finds those grid points for the following density interaction analysis.
		gridPoints = voronoiPartitioner.partition(regions, parameters, densityType); // SEDD or DORI

		Region qmRegion = simpleRegionFactory.create(QM);
		qmRegion.addAll(regions.get(QM_CORE).getMolecularMap());

		Region.Name cubeRegionName = (Region.Name) parameters.get(REGION_CUBE);
		Set<Atom> atomsInCube =  (new HashSet<Atom>(regions.get(cubeRegionName).getAtoms()));
		Set<Molecule> moleculesInCube = (Set<Molecule>) regions.get(cubeRegionName).getMolecules();

		gridPoints.stream()
                  .filter(gridPoint -> qmRegion.containsAll(gridPoint.getTwoClosestMolecules()))
				  .filter(gridPoint -> {
					double density = densityCalculator.calculate(gridPoint.getCoordinate(), moleculesInCube);
					return(density >= (double) parameters.get(DENSITY_DORI))
				   })
			 	  .filter(gridPoint -> {
						DensityPoint densityPoint = densityPropertiesCalculator.calculate(atomsInCube, gridPoint.getCoordinate());
						double dori = doriComputer.calculate(densityPoint);
						return (1 >= dori && dori >= (double) parameters.get(DORI));
			       })
				.forEach(gridPoint -> qmRegion.addMolecule(gridPoint.getTwoClosestMolecules()));

		regions.add(qmRegion);
		regions.add(singleRegionizerService.regionize(regions, QM_ADAPTIVE));

		return regions;
	}


}
