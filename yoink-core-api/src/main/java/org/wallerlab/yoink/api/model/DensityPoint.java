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
package org.wallerlab.yoink.api.model;

import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this interface is for a point involved in density properties calculation
 * 
 * @author Min Zheng
 *
 */
public interface DensityPoint {

	/**
	 * get the electronic density of this point
	 * 
	 * @return density value -{@link java.lang.Double}
	 */
	double getDensity();

	/**
	 * get the density gradient vector at this point
	 * 
	 * @return density gradient vector -
	 *         {@link Vector}
	 */
	Vector getGradientVector();

	/**
	 * get the hessian matrix at this point
	 * 
	 * @return hessian matrix -
	 *         {@link Matrix}
	 */
	Matrix getHessian();

	/**
	 * get the density gradient value at this point
	 * 
	 * @return density gradient -{@link java.lang.Double}
	 */
	double getGradient();

	/**
	 * get the coordinate of the point
	 * 
	 * @return current coordinate -{@link Coord}
	 */
	 Coord getCoord();

	enum DensityType {
		DORI, RDG, DENSITY, SEDD, ELECTRONIC;
	}

}
