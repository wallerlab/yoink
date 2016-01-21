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
import org.wallerlab.yoink.math.constants.Constants;

/**
 * This class is to get the reduced density gradient(RDG) value of a point. For
 * RDG, see: Erin R.Johnson, Shahar Keinan, Paula Mori-Sanchez, Julia
 * Contreras-Garcia, Aron J. Cohen, and Weitao Yang, J. Am. Chem. Soc. 2010,
 * 132, pp 6498-6506.
 * 
 * @author Min Zheng
 *
 */
@Service
public class ReducedDensityGradientComputer implements
		Computer<Double, DensityPoint> {

	/**
	 * calculate RDG of a density point
	 * 
	 * @param densityPoint
	 *            -{@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 * @return rdg value
	 */
	public Double calculate(DensityPoint densityPoint) {
		// initialize
		double density = densityPoint.getDensity();
		double gradient = densityPoint.getGradient();
		double rdg = calculateRdg(density, gradient);
		return rdg;
	}

	private double calculateRdg(double density, double gradient) {
		double rdg = Math.sqrt(gradient) / (Math.pow(density, 4.0 / 3));
		rdg = rdg / Constants.RDG_COEFFICIENT;
		return rdg;
	}

}
