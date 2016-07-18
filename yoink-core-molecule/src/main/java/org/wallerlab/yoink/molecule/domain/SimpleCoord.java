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
package org.wallerlab.yoink.molecule.domain;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this domain model of coordinate stores the information of coordinate in the
 * format of 3D vector.
 *
 * 
 */
//TODO deprecate this.
public class SimpleCoord implements Coord {

	private final Vector coords;

	/**
	 * construct a new simple coord with its coordinate vector
	 * 
	 * @param coordVector
	 *            {@link Vector}
	 */
	public SimpleCoord(Vector coordVector) {
		this.coords = coordVector;
	}

	/**
	 * get the value of coordinates
	 * 
	 * @return coords {@link Vector}
	 */
	@Override
	public Vector getCoords() {
		return coords;
	}

	@Override
	public String toString() {
		return "Coords=" + coords.getX() + " " + coords.getY() + " " + coords.getZ() + " " + '}';
	}
}
