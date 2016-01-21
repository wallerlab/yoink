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
 * this class is for smooth function used in DAS.
 * 
 * @author Min Zheng
 *
 */
@Service("buloSmoothFunction")
public class BuloSmoothFunction implements SmoothFunction {

	/**
	 * this smooth function is used in difference-based adaptive solvation(DAS)
	 * method. for details please see:
	 * "Toward a practical method for adaptive QM/MM simulations." Journal of
	 * Chemical Theory and Computation 5.9 (2009): 2212-2221.
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
			smoothFactor = 1;
		} else if (currentValue < min) {
			smoothFactor = 0;
		} else {
			smoothFactor = Math.pow((currentValue - min), 2);
			smoothFactor = smoothFactor * (3 * max - min - 2 * currentValue);
			smoothFactor = smoothFactor / Math.pow((max - min), 3);
		}
		return smoothFactor;
	}

}
