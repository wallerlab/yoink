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

package org.wallerlab.yoink.adaptive.smooth.smoothfunction;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;

/**
 * this class is for smooth function used in PAP and SAP.
 * 
 * @author Min Zheng
 *
 */
@Service("permutedSmoothFunction")
public class PermutedSmoothFunction implements SmoothFunction {

	/**
	 * this smooth function is used in PAP and SAP methods. for details please
	 * see: Heyden, Andreas, Hai Lin, and Donald G. Truhlar. "Adaptive
	 * partitioning in combined quantum mechanical and molecular mechanical
	 * calculations of potential energy functions for multiscale simulations."
	 * The Journal of Physical Chemistry B 111.9 (2007): 2231-2241.
	 * 
	 * @param currentValue
	 *            , currentValue(variable) in smooth function
	 * @param min
	 *            , minimum value in smooth function
	 * @param max
	 *            , maximum value in smooth function
	 * @return smooth factor
	 */
	public double evaluate(double currentValue, double min, double max) {
		double smoothFactor;
		if (currentValue > max) {
			smoothFactor = 0;
		} else if (currentValue < min) {
			smoothFactor = 1;
		} else {
			double alpha = (currentValue - min) / (max - min);
			smoothFactor = -6 * (Math.pow((alpha), 5)) + 15
					* (Math.pow((alpha), 4)) - 10 * (Math.pow((alpha), 3)) + 1;
		}
		return smoothFactor;
	}
}
