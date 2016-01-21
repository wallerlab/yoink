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
package org.wallerlab.yoink.molecular.domain;

import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.CommonsVector3D;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * this domain model of coordinate stores the information of coordinate in the
 * format of 3D vector. The unit of Coord is Bohr.
 *
 * 
 */

public class SimpleCoord implements Coord {

	private Vector coords;

	/**
	 * construct a new simple coord with its coordinate vector
	 * 
	 * @param coordVector
	 *            {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	public SimpleCoord(Vector coordVector) {
		this.coords = coordVector;
	}

	public SimpleCoord() {
	}

	/**
	 * get the value of coordinates
	 * 
	 * @return coords {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	@Override
	public Vector getCoords() {
		return coords;
	}

	/**
	 * set the value of the coordinates
	 * 
	 * @param coords
	 *            {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	@Override
	public void setCoords(Vector coords) {
		this.coords = coords;
	}

}
