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
 * this class is smooth function used in Hot-spot. the original smooth function
 * is from:
 * "CHARMM: A program for macromolecular energy, minimization, and dynamics calculations"
 * 
 * @author Min Zheng
 *
 */
@Service("brooksSmoothFunction")
public class BrooksSmoothFunction implements SmoothFunction {
	/**
	 * this smooth function is used in hot-spot method. for details please see:
	 * "A QM/MM simulation method applied to the solution of Li+ in liquid ammonia."
	 * Chemical physics 211.1 (1996): 313-323.
	 * 
	 * @param currentValue, currentValue(variable) in smooth function
	 * @param min, minimum value in smooth function
	 * @param max, maximum value in smooth function
	 * @return smooth factor
	 */
	public double evaluate(double currentValue, double min, double max) {
		double smoothFactor;
		if (currentValue > max) {
			smoothFactor = 0;
		} else if (currentValue <= min) {
			smoothFactor = 1;
		} else {
			smoothFactor = Math.pow(
					(Math.pow(max, 2) - Math.pow(currentValue, 2)), 2);
			smoothFactor = smoothFactor
					* (Math.pow(max, 2) + 2 * Math.pow(currentValue, 2) - 3 * Math
							.pow(min, 2));
			smoothFactor = smoothFactor / Math.pow((max * max - min * min), 3);
		}
		return smoothFactor;
	}

}
