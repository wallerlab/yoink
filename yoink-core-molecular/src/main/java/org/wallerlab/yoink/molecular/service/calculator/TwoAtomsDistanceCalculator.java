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

package org.wallerlab.yoink.molecular.service.calculator;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this class is to get the distance between two atoms
 * 
 * @author Min Zheng
 *
 */
@Service
public class TwoAtomsDistanceCalculator implements
		Calculator<Double, Atom, Atom> {

	/**
	 * calculate the distance between two atoms
	 * 
	 * @param atom1
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @param atom2
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @return the distance between atom1 and atom2
	 * 
	 */
	public Double calculate(Atom atom1, Atom atom2) {
		Vector atomCoordMatrix1 = atom1.getCoordinate().getCoords();
		Vector atomCoordMatrix2 = atom2.getCoordinate().getCoords();
		return atomCoordMatrix1.distance(atomCoordMatrix2);
	}

}
