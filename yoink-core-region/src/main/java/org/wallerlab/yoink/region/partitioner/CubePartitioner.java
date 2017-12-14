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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.cube.Assigner;
import org.wallerlab.yoink.api.service.cube.CubeBuilder;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.cube.domain.SimpleCube;
import org.wallerlab.yoink.cube.domain.SimpleGridPoint;
import org.wallerlab.yoink.molecule.domain.SimpleCoord;
import org.wallerlab.yoink.molecule.domain.SimpleMolecule;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * This class is to get those grid points that belong to the intersection region
 * of QM core region and non-QM core region. Then further calculation(dori and
 * sedd) will just be carried out on those grid points.
 * 
 * 
 */
@Service
public class CubePartitioner implements
		Partitioner<List<GridPoint>, DensityType> {

	@Resource
	private CubeBuilder<Set<Molecule>> cubeBuilder;

	@Resource
	private Assigner<GridPoint, Map<Region.Name, Region>, Region.Name> gridPointAssigner;

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	@Value("${yoink.job.debug}")
	private boolean debug = false;

	@Value("${yoink.job.dis_cutoff}")
	private double dis_cutoff = 10.0;

	/**
	 * loop over all grid points in the cube, find the grid points whose two
	 * closest neighbours are from different regions(QM core region and non-QM
	 * core region ).
	 * 
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link org.wallerlab.yoink.api.model.region.Region.Name } as
	 *            key, Region
	 *            {@link org.wallerlab.yoink.api.model.region.Region} as value
	 * @param parameters
	 *            - a Map, JobParameter
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter}
	 *            as Key, Object {@link java.lang.Object} as value
	 * @param densityType
	 *            -
	 *            {@link org.wallerlab.yoink.api.model.density.DensityPoint.DensityType}
	 * @return gridPointsForFurtherAnalysis - a list of gridPoint
	 *         {@link org.wallerlab.yoink.api.model.cube.GridPoint }
	 */
	public List<GridPoint> partition(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, DensityType densityType) {
		// initialize
		double[] xyzStepSize = getXyzStepSizeByDensityType(parameters,
				densityType);
		// Cube cube = new SimpleCube(xyzStepSize);
		Region.Name cubeRegionName = (Region.Name) parameters
				.get(JobParameter.REGION_CUBE);

		Set<Molecule> moleculesInCube = regions.get(cubeRegionName)
				.getMolecules();
		Cube cube = cubeBuilder.build(xyzStepSize, moleculesInCube);
		// gridPointsForFurtherAnalysis
		List<GridPoint> gridPointsForFurtherAnalysis = getGridPointsWillBeCalculated(
				regions, cube, cubeRegionName);
		return gridPointsForFurtherAnalysis;
	}

	/*
	 * DORI and SEDD calculations use different xyz step sizes.
	 */
	private double[] getXyzStepSizeByDensityType(
			Map<JobParameter, Object> parameters, DensityType densityType) {
		double[] xyzStepSize;
		// get xyz step size
		switch (densityType) {
		case SEDD:
			xyzStepSize = (double[]) parameters.get(JobParameter.SEDD_STEPSIZE);
			break;
		case DORI:
			xyzStepSize = (double[]) parameters.get(JobParameter.DORI_STEPSIZE);
			break;
		default:
			throw new IllegalArgumentException("invalid  name");
		}
		return xyzStepSize;
	}

	private List<GridPoint> getGridPointsWillBeCalculated(
			Map<Region.Name, Region> regions, Cube cube,
			Region.Name cubeRegionName) {

		List<Coord> coordinates = cube.getCoordinates();
		List<GridPoint> gridPointsTemp = checkEveryGridPoint(regions,
				cubeRegionName, coordinates);
		return gridPointsTemp;
	}

	private List<GridPoint> checkEveryGridPoint(
			Map<Region.Name, Region> regions, Region.Name cubeRegionName,
			List<Coord> coordinates) {
		if (debug) {
			System.out.println("before: coordinates.parallelStream().forEach"
					+ System.currentTimeMillis());

		}
	    // get molecules within dis_cutoff for each coordinate
		Set<Molecule> molecules = regions.get(cubeRegionName).getMolecules();
		Set[] initialValues = new Set[coordinates.size()];
		List<Set> msl = Arrays.asList(initialValues);
		IntStream
				.range(0, coordinates.size())
				.parallel()
				.forEach(

						nCoord -> {		
							Set<Molecule> ms = Collections
									.synchronizedSet(new HashSet<Molecule>());

							molecules
									.parallelStream()
									.forEach(
											m -> {
												double distance = closestDistanceToMoleculeCalculator.calculate(
														coordinates.get(nCoord),
														m);
												if (distance < dis_cutoff) {
													ms.add(m);
												}

											});
							
							msl.set(nCoord, ms);

						});

		if (debug) {
			System.out
					.println("start: cube partitioner ridPointsTemp.parallelStream().forEach("
							+ System.currentTimeMillis());

		}
		// generate grid point for each coordinate

		List<GridPoint> gridPointsTemp = Collections
				.synchronizedList(new ArrayList<GridPoint>());
		IntStream.range(0, coordinates.size()).parallel().forEach(

		nCoord -> {
			if (msl.get(nCoord).size() > 1) {

				SimpleGridPoint gridPoint = new SimpleGridPoint();

				gridPoint.setCoordinate(coordinates.get(nCoord));
				gridPoint.setIndexInCube(nCoord);
				gridPoint.setMolecules(msl.get(nCoord));

				gridPointsTemp.add(gridPoint);

			}
		});
		if (debug) {
			System.out
					.println("middle: cube partitioner gridPointsTemp.parallelStream().forEach("
							+ System.currentTimeMillis());

		}
       // get gridPoints needing further analysis
		List<GridPoint> gridPoints = Collections
				.synchronizedList(new ArrayList<GridPoint>());
		gridPointsTemp.parallelStream().forEach(
				gpt -> {
					Map<String, Object> properties = gridPointAssigner.assign(
							gpt, regions, cubeRegionName);
					if (!properties.isEmpty()) {
						gpt.setProperties(properties);

						gridPoints.add(gpt);

					}
				});

		if (debug) {
			System.out
					.println("after: cube partitioner ridPointsTemp.parallelStream().forEach("
							+ System.currentTimeMillis());
			System.out.println("grid points for analysis"+gridPointsTemp.size());

		}
		return gridPoints;
	}
}
