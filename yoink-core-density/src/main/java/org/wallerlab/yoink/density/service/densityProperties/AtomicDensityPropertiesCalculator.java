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

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.CommonsMatrix;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;
/**
 * This class is to calculate the Properties of a density point from an atom.
 * 
 * @author Min Zheng
 *
 */
@Service
public class AtomicDensityPropertiesCalculator implements
		Calculator<DensityPoint, DensityPoint, Atom> {
	@Resource
	private SimpleMatrixFactory myMatrix;
	/**
	 * calculate a density point's properties from an atom
	 * 
	 * @param densityPoint
	 *            -{@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 * @param atom
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @return densityPoint -
	 *         {@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 */
	public DensityPoint calculate(DensityPoint densityPoint, Atom atom) {
		// initialize
		Vector distanceVector = getDistanceVectorAndAtomName(densityPoint, atom);
		Element atomName = atom.getElementType();
		double distance = Math.max(distanceVector.getNorm(),
				Constants.DISTANCE_DEFAULT);
		double distanceReciprocal = 1.0 / distance;
		Vector distanceUnitVector = distanceVector
				.scalarMultiply(distanceReciprocal);
		double exp1 = Math.exp(-distance / atomName.z1());
		double exp2 = Math.exp(-distance / atomName.z2());
		double exp3 = Math.exp(-distance / atomName.z3());
		double fac1 = atomName.cz1() * exp1 + atomName.cz2() * exp2
				+ atomName.cz3() * exp3;// first derivative of density
		//get the values of densityPoint
		densityPoint = wrapDensityBasedProperties(densityPoint, distanceVector,
				atomName, distanceReciprocal, distanceUnitVector, exp1, exp2,
				exp3, fac1);
		return densityPoint;
	}

	private Vector getDistanceVectorAndAtomName(DensityPoint densityPoint,
			Atom atom) {
		Coord tempCoord = atom.getCoordinate();
		Coord currentCoord = densityPoint.getCurrentCoord();
		Vector distanceVector = tempCoord.getCoords().subtract(
				currentCoord.getCoords());
		return distanceVector;
	}

	private DensityPoint wrapDensityBasedProperties(DensityPoint densityPoint,
			Vector distanceVector, Element atomName, double distanceReciprocal,
			Vector distanceUnitVector, double exp1, double exp2, double exp3,
			double fac1) {
		Vector gradientVector = getGradientVector(distanceUnitVector, fac1);
		double density = getDensity(atomName, exp1, exp2, exp3);
		Matrix hessian = getHessian(distanceVector, atomName,
				distanceReciprocal, distanceUnitVector, exp1, exp2, exp3, fac1);
		// set densityPoint
		densityPoint.setDensity(density);
		densityPoint.setGradientVector(gradientVector);
		densityPoint.setHessian(hessian);
		return densityPoint;
	}

	private Vector getGradientVector(Vector distanceUnitVector, double fac1) {
		return distanceUnitVector.scalarMultiply(fac1 * -1.0);
	}

	private double getDensity(Element atomName, double exp1, double exp2,
			double exp3) {
		return atomName.c1() * exp1 + atomName.c2() * exp2 + atomName.c3()
				* exp3;
	}

	private Matrix getHessian(Vector distanceVector, Element atomName,
			double distanceReciprocal, Vector distanceUnitVector, double exp1,
			double exp2, double exp3, double fac1) {
		Matrix hessian =  myMatrix.matrix3x3();
		Vector distanceUnitVector2 = distanceUnitVector
				.ebeMultiply(distanceUnitVector);
		double distanceReciprocal2 = distanceReciprocal * distanceReciprocal;
		double fac11 = fac1 * distanceReciprocal;// first derivative of density
													// divided by distance
		double fac2 = atomName.czz1() * exp1 + atomName.czz2() * exp2
				+ atomName.czz3() * exp3;
		double fac3 = fac2 + fac11;// second derivative of density + fac11

		// hessian is a 3x3 symmetrix matrix.get the upper right part at first.
		for (int j = 0; j < 3; j++) {
			hessian.setEntry(j, j, fac3 * distanceUnitVector2.getEntry(j)
					- fac11);
			for (int k = j + 1; k < 3; k++) {
				hessian.setEntry(j, k,
						distanceReciprocal2 * distanceVector.getEntry(j)
								* distanceVector.getEntry(k) * fac3);
			}
		}
		// get the lower left part of hessian
		for (int j = 0; j < 3; j++) {
			for (int k = j + 1; k < 3; k++) {
				hessian.setEntry(k, j, hessian.getEntry(j, k));
			}
		}
		return hessian;
	}

}
