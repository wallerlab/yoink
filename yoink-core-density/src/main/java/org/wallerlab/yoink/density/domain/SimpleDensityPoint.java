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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * This class is to store all density based properties on a point, those
 * properties will be used during DORI/SEDD/RDG calculation.
 * 
 * @author Min Zheng
 *
 */

public class SimpleDensityPoint implements DensityPoint {

	private Coord currentCoord;

	private Vector gradientVector;

	private Matrix hessian;

	private double gradient;

	private double density;

	public SimpleDensityPoint() {

	}

	public SimpleDensityPoint(Coord currentCoord) {
		this.currentCoord = currentCoord;
	}

	/**
	 * get density
	 */
	@Override
	public double getDensity() {
		return density;
	}

	/**
	 * set density
	 */
	@Override
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	 * get gradient vector.
	 */
	@Override
	public Vector getGradientVector() {
		return gradientVector;
	}

	/**
	 * set gradient vector.
	 */
	@Override
	public void setGradientVector(Vector gradientVector) {
		this.gradientVector = gradientVector;
	}

	/**
	 * get hessian
	 */
	@Override
	public Matrix getHessian() {
		return hessian;
	}

	/**
	 * set hessian
	 */
	@Override
	public void setHessian(Matrix hessian) {
		this.hessian = hessian;
	}

	/**
	 * get gradient
	 */
	@Override
	public double getGradient() {
		return gradient;
	}

	/**
	 * set gradient
	 */
	@Override
	public void setGradient(double gradient) {
		this.gradient = gradient;
	}

	/**
	 * get the coordinate
	 */
	@Override
	public Coord getCurrentCoord() {
		return currentCoord;
	}

}
