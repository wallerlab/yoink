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
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * this class is to calculate density from one atom
 * 
 * @author Min Zheng
 *
 */
@Service
public class AtomDensityCalculator implements Calculator<Double, Coord, Atom> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * calculate the density of a point from an atom
	 * 
	 * @param atom
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @param currentCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @return the density of a point from an atom
	 */
	public Double calculate(Coord currentCoord, Atom atom) {
		double distance = distanceCalculator.calculate(currentCoord, atom);
		Element atomType = atom.getElementType();
		double exp1 = Math.exp(-distance / atomType.z1());
		double exp2 = Math.exp(-distance / atomType.z2());
		double exp3 = Math.exp(-distance / atomType.z3());
		double density = atomType.c1() * exp1 + atomType.c2() * exp2
				+ atomType.c3() * exp3;
		return density;
	}

}
