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
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.region.Partitioner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;


/**
 * This class is to analyze SEDD for those grid points in the intersection
 * between QM core region and non-QM core region based on Voronoi partitioning.
 * If a grid point satisfies the SEDD criteria, the corresponding non-QM
 * molecule will treated as QM core.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SeddPartitioner implements Partitioner<Map<Region.Name, Region>, List<GridPoint>> {

	@Resource
	private Calculator<Double, Coord, Atom[]> atomicDensityRatioCalculator;

	@Resource
	private Calculator<Double, Coord, Atom> atomDensityCalculator;

	@Resource
	protected Calculator<DensityPoint, Set<Atom>, Coord> densityPropertiesCalculator;

	@Resource
	private Computer<Double, DensityPoint> seddComputer;

	/**
	 * This method is to get QM core region and QM core adaptive region . for a
	 * grid point, if its SEDD from molecules in cube is larger than
	 * seddThreshold, and the sedd value from its two closest atoms is larger
	 * than seddThreshold and densityRatio is the range
	 * [densityRatioMin,densityRatioMax] ,then the non-QM molecule of the grid
	 * point should be in QM core region.
	 *
	 */
	@Override
	public List<Region> partition(List<Region> regions,
								  List<JobParameter> parameters,
								  List<GridPoint> gridPoints) {

		Region region = regions.get(QM);

		gridPoints.stream()
				.filter(gridPoint -> region.containsAll(gridPoint.getTwoClosestMolecules()))
				.filter(gridPoint -> {
							double densityOfTwoAtoms = 0;
							for (Atom atom : gridPoint.) {
								densityOfTwoAtoms += atomDensityCalculator.calculate(gridPoint.getCoordinate(), atom);
							}
							return (densityOfTwoAtoms > (double) parameters.get(JobParameter.DENSITY_SEDD));
						})
				.filter(gridPoint -> {
							Atom[] twoAtoms = {(Atom)gridPoint.getNearestAtoms().get(0),(Atom) gridPoint.getNearestAtoms().get(1)};
							double densityRatioOfTwoAtoms = atomicDensityRatioCalculator.calculate(gridPoint.getCoordinate(), twoAtoms);
							return ((densityRatioOfTwoAtoms >= (double) parameters.get(DENSITY_RATIO_MIN)
									&& densityRatioOfTwoAtoms <= (double) parameters.get(DENSITY_RATIO_MAX)));
						})
				.filter(gridPoint -> {
					DensityPoint densityPoint = densityPropertiesCalculator.calculate(neighbourAtoms, coordinate);
					double seddAtom = seddComputer.calculate(densityPoint);
					return (seddAtom <= (double) parameters.get(JobParameter.SEDD));
				})
				.filter(gridPoint -> {
					Set<Atom> atomsInCube = (new HashSet<Atom>(regions.get((Region.Name) parameters.get(REGION_CUBE)).getAtoms()));
					DensityPoint densityPoint = densityPropertiesCalculator.calculate(atomsInCube, coordinate);
					double seddMoleculeTemp = seddComputer.calculate(densityPoint);
					// check sedd value from all molecules in cube
					return (seddMoleculeTemp <= (double) parameters.get(SEDD));
				})
				.forEach(region.addAll(neighbours));

	}


}
