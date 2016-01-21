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
package org.wallerlab.yoink.density.service.densityProperties;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.density.domain.SimpleDensityPoint;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * This class is to calculate the Properties of a Density point from atoms.
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityPropertiesCalculator implements
		Calculator<DensityPoint, Set<Atom>, Coord> {

	@Resource
	private Calculator<DensityPoint, DensityPoint, Atom> atomicDensityPropertiesCalculator;

	@Resource
	private SimpleMatrixFactory myMatrix;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	private Factory<DensityPoint, Coord> simpleDensityPointFactory;

	/**
	 * calculate a density point's properties from atoms
	 * 
	 * @param atoms
	 *            -a Set of atoms
	 *            {@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @param currentCoord
	 *            , the coordinate of the density point,
	 *            {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @return densityPoint -
	 *         {@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 */
	public DensityPoint calculate(Set<Atom> atoms, Coord currentCoord) {
		// initialize
		Matrix hessian = myMatrix.matrix3x3();
		Vector gradientVector = myVector3D.create(0, 0, 0);
		double density = 0;
		DensityPoint densityPoint = loopOverAllAtoms(atoms, currentCoord,
				hessian, gradientVector, density);
		return densityPoint;
	}

	private DensityPoint loopOverAllAtoms(Set<Atom> atoms, Coord currentCoord,
			Matrix hessian, Vector gradientVector, double density) {
		DensityPoint densityPoint = simpleDensityPointFactory
				.create(currentCoord);
		for (Atom atom : atoms) {
			DensityPoint densityPointTemp = atomicDensityPropertiesCalculator
					.calculate(densityPoint, atom);
			density += densityPointTemp.getDensity();
			gradientVector = gradientVector.add(densityPointTemp
					.getGradientVector());
			hessian = hessian.add(densityPointTemp.getHessian());
		}
		density = checkDensity(density);
		setDensityValues(densityPoint, density, gradientVector, hessian);
		return densityPoint;
	}

	// if the density is too small, zero or close to zero, take the default
	// density value.
	private double checkDensity(double density) {
		density = Math.max(density, Constants.DENSITY_DEFAULT);
		return density;
	}

	private void setDensityValues(DensityPoint densityPoint, double density,
			Vector gradientVector, Matrix hessian) {
		densityPoint.setDensity(density);
		densityPoint.setGradientVector(gradientVector);
		densityPoint.setGradient(gradientVector.dotProduct());
		densityPoint.setHessian(hessian);
	}

}
