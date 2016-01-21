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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * This class is to calculate the distance between one point in space and an
 * atom.
 * 
 * @author Min Zheng
 *
 */

@Service
public class DistanceCalculator implements Calculator<Double, Coord, Atom> {

	/**
	 * calculate the distance between a coordinate and an atom.
	 * 
	 * @param gridCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param atom
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @return density -{@link java.lang.Double}
	 * 
	 */
	public Double calculate(Coord gridCoord, Atom atom) {
		Vector atomCoordMatrix = atom.getCoordinate().getCoords();
		return atomCoordMatrix.distance(gridCoord.getCoords());
	}

}
