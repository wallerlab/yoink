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
import org.wallerlab.yoink.api.model.molecular.RadialGrid;
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
		
		// get the values of densityPoint
		densityPoint = wrapDensityBasedProperties(densityPoint, distanceVector,
				atom);
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
			Vector distanceVector, Atom atom) {
		Element atomName = atom.getElementType();
		double distance = Math.max(distanceVector.getNorm(),
				Constants.DISTANCE_DEFAULT);
		double distanceReciprocal = 1.0 / distance;
		Vector distanceUnitVector = distanceVector
				.scalarMultiply(distanceReciprocal);
		double fac1 = 0;
		double fac2 = 0;
		double density = 0;
		if(atom.getRadialGrid()==null){
		double exp1 = Math.exp(-distance / atomName.z1());
		double exp2 = Math.exp(-distance / atomName.z2());
		double exp3 = Math.exp(-distance / atomName.z3());
		fac1 = atomName.cz1() * exp1 + atomName.cz2() * exp2 + atomName.cz3()
				* exp3;// first derivative of density
		fac2 = atomName.czz1() * exp1 + atomName.czz2() * exp2
				+ atomName.czz3() * exp3;
		density = getPromolecularDensity(atomName, exp1, exp2, exp3);
		}
		else{
			

			double f = 0.0;
			double fp=0.0;
		 double fpp=0.0;
			RadialGrid grid = atom.getRadialGrid();
			if (distance < grid.getPosition_max()) {

				int ir = 0;
				double r = 0;
				double[] grid_positions = grid.getGrid_positions();
				// careful with grid limits.
				if (distance <= grid_positions[0]) {
					
					ir = 1;
					r = grid_positions[0];
				} else {
					ir = (int) (1 + Math.floor(Math.log(distance / grid.getA())
							/ grid.getB()));
					r = distance;
				}
				
				double[] rr = new double[4];
				double[] dr1 = new double[4];
				double[][] x1dr12 = new double[4][4];

				for (int i = 0; i < 4; i++) {
					int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3 + i);
					rr[i] = grid_positions[ii];
					dr1[i] = r - rr[i];
					
					for (int j = 0; j <= i - 1; j++) {
						x1dr12[i][j] = 1.0 / (rr[i] - rr[j]);
						x1dr12[j][i] = -x1dr12[i][j];
						
					}
				}
				// interpolate, lagrange 3rd order, 4 nodes
				for (int i = 0; i < 4; i++) {
					int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3 + i);
					double prod = 1.0;
					for (int j = 0; j < 4; j++) {
						if (i == j) {
							continue;
						}
						
						prod = prod * dr1[j] * x1dr12[i][j];
					}
					// density=density+grid.getGrid_values()[ii]*prod;
					f = f + grid.getGrid_values()[ii] * prod;
					fp=fp+grid.getFirst_derivative_of_grid_values()[ii]*prod;
					 fpp=fpp+grid.getSecond_derivative_of_grid_values()[ii]*prod;
					
				}
			}// end of if distance
			density=f;
			fac1 =-fp;
			fac2=fpp;		
		}//end of grid density calculation
		Vector gradientVector = getGradientVector(distanceUnitVector, fac1);

		Matrix hessian = getHessian(distanceVector, atomName,
				distanceReciprocal, distanceUnitVector, fac1, fac2);
		// set densityPoint
		densityPoint.setDensity(density);
		densityPoint.setGradientVector(gradientVector);
		densityPoint.setHessian(hessian);
		return densityPoint;
	}

	private Vector getGradientVector(Vector distanceUnitVector, double fac1) {
		return distanceUnitVector.scalarMultiply(fac1 * -1.0);
	}

	private double getPromolecularDensity(Element atomName, double exp1, double exp2,
			double exp3) {
		return atomName.c1() * exp1 + atomName.c2() * exp2 + atomName.c3()
				* exp3;
	}

	private Matrix getHessian(Vector distanceVector, Element atomName,
			double distanceReciprocal, Vector distanceUnitVector, double fac1,
			double fac2) {
		Matrix hessian = myMatrix.matrix3x3();
		Vector distanceUnitVector2 = distanceUnitVector
				.ebeMultiply(distanceUnitVector);
		double distanceReciprocal2 = distanceReciprocal * distanceReciprocal;
		double fac11 = fac1 * distanceReciprocal;// first derivative of density
													// divided by distance

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