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
package org.wallerlab.yoink.density.service.density;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * this class is to get the density ratio between two atoms on a grid point.
 * 
 * @author Min Zheng
 *
 */
@Service
public class AtomicDensityRatioCalculator implements
		Calculator<Double, Coord, Atom[]> {

	@Resource
	private Calculator<Double, Coord, Atom> atomDensityCalculator;

	/**
	 * the density ratio of two atoms on a grid point.
	 * 
	 * @param coordinate
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param neighbours
	 *            - an array of two atoms
	 *            {@link org.wallerlab.yoink.api.model.molecular.Atom}.
	 * @return the density ratio of two atoms in neighbours
	 */
	public Double calculate(Coord coordinate, Atom[] neighbours) {
		double densityA = atomDensityCalculator.calculate(coordinate,
				neighbours[0]);
		double densityB = atomDensityCalculator.calculate(coordinate,
				neighbours[1]);
		return densityA / densityB;
	}

}
