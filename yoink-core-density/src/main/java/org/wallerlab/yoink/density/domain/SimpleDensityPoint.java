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
package org.wallerlab.yoink.density.domain;

import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * This class is to store all density based properties on a grid point.
 * These properties will be used during DORI/SEDD/RDG calculation.
 * 
 * @author Min Zheng
 *
 */
public class SimpleDensityPoint implements DensityPoint {

	private final Coord coord;

	private final double density;

	private final double gradient;

	private final Vector gradientVector;

	private final Matrix hessian;

	public SimpleDensityPoint(final Coord coord,
							  final double density,
							  final double gradient,
							  final Vector gradientVector,
							  final Matrix hessian){
		this.coord         = coord;
		this.density       = density;
		this.gradient      = gradient;
		this.gradientVector= gradientVector;
		this.hessian       = hessian;
	}

	/**
	 * get the coordinate
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * get density
	 */
	@Override
	public double getDensity() {
		return density;
	}

	/**
	 * get gradient
	 */
	@Override
	public double getGradient() {
		return gradient;
	}

	/**
	 * get gradient vector.
	 */
	@Override
	public Vector getGradientVector() {
		return gradientVector;
	}

	/**
	 * get hessian
	 */
	@Override
	public Matrix getHessian() {
		return hessian;
	}

}