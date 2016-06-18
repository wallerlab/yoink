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
package org.wallerlab.yoink.molecule.service.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.molecular.Atom;
import org.wallerlab.yoink.batch.api.model.molecular.Coord;
import org.wallerlab.yoink.batch.api.model.molecular.Molecule;
import org.wallerlab.yoink.batch.api.service.Calculator;

/**
 * This class is to calculate the closest distance between one point in space and a molecule
 * 
 * @author Min Zheng
 *
 */

@Service
public class ClosestDistanceToMoleculeCalculator implements Calculator<Double, Coord, Molecule> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * calculate the distance minimum between a coordinate and a molecule.
	 * 
	 * @param gridCoord
	 *            -{@link Coord}
	 * @param molecule
	 *            -{@link Molecule}
	 * @return density -{@link java.lang.Double}
	 * 
	 */
	public Double calculate(Coord gridCoord, Molecule molecule) {
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : molecule.getAtoms()) {
			double tempdistance = distanceCalculator.calculate(gridCoord,
					atom);
			distances.add(tempdistance);
		}
		double distance = Collections.min(distances);
		return distance;
	}
}
