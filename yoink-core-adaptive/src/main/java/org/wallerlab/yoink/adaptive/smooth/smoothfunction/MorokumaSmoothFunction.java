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
 * this class is for smooth function used in ONIOM-XS.
 * 
 * @author Min Zheng
 *
 */
@Service("morokumaSmoothFunction")
public class MorokumaSmoothFunction implements SmoothFunction {

	/**
	 * this smooth function is used in ONIOM-XS method. for details please see:
	 * "ONIOM-XS: An extension of the ONIOM method for molecular simulation in condensed phase"
	 * Chemical Physics Letters, Volume 355, Number 3, 2 April 2002, pp.
	 * 257-262(6).
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
			double x = (currentValue - min) / (max - min);
			smoothFactor = 6 * (Math.pow((x - 0.5), 5)) - 5
					* (Math.pow((x - 0.5), 3)) + 15.0 / 8 * (x - 0.5) + 0.5;
		}
		return smoothFactor;
	}
}
