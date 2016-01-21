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

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * This class is to do Silva density analysis calculation for a point. Silva
 * developed two density analysis methods (DORI and SEDD).
 * 
 * 
 * For DORI,see:De Silva, Piotr, and Clémence Corminboeuf.
 * "Simultaneous Visualization of Covalent and Noncovalent Interactions Using Regions of Density Overlap."
 * Journal of chemical theory and computation 10.9 (2014): 3745-3756.
 *
 * For SEDD, see:De Silva, Piotr, Jacek Korchowiec, and Tomasz A. Wesolowski.
 * "Revealing the Bonding Pattern from the Molecular Electron Density Using
 * Single Exponential Decay Detector: An Orbital‐Free Alternative to the
 * Electron Localization Function." ChemPhysChem 13.15 (2012): 3462-3465.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SilvaDensityComputer implements Computer<Double, DensityPoint> {

	/**
	 * calculate Silva density analysis value for a density point
	 * 
	 * @param densityPoint
	 *            -{@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 * @return silvaValue of a density point
	 */
	public Double calculate(DensityPoint densityPoint) {
		// initialize
		double silvaValue = 0.0;
		silvaValue = calculateSilvaValue(densityPoint, silvaValue);
		return silvaValue;
	}

	protected double calculateSilvaValue(DensityPoint densityPoint,
			double silvaValue) {
		silvaValue = loopOverHessian(densityPoint, silvaValue);
		silvaValue = getSilvaValue(densityPoint, silvaValue);
		return silvaValue;
	}

	// rewrite in child class
	protected double getSilvaValue(DensityPoint densityPoint, double silvaValue) {
		return silvaValue;
	}

	protected double loopOverHessian(DensityPoint densityPoint,
			double silvaValue) {
		double density = densityPoint.getDensity();
		Vector gradientVector = densityPoint.getGradientVector();
		Matrix hessian = densityPoint.getHessian();
		double gradient = densityPoint.getGradient();
		silvaValue = loopEveryElementInHessian(silvaValue, density,
				gradientVector, hessian, gradient);
		return silvaValue;
	}

	private double loopEveryElementInHessian(double silvaValue, double density,
			Vector gradientVector, Matrix hessian, double gradient) {
		for (int i = 0; i < 3; i++) {
			double temp = 0.0;
			for (int j = 0; j < 3; j++) {
				temp += gradientVector.getEntry(j) * hessian.getEntry(i, j);
			}
			silvaValue += Math.pow((density * temp - gradientVector.getEntry(i)
					* gradient), 2);
		}
		return silvaValue;
	}

}
