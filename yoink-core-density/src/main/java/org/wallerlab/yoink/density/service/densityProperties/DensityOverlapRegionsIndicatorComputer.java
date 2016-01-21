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

/**
 * This class is to get the density overlap regions indicator(DORI) value of a
 * grid point. For DORI,see:De Silva, Piotr, and Clémence Corminboeuf.
 * "Simultaneous Visualization of Covalent and Noncovalent Interactions Using Regions of Density Overlap."
 * Journal of chemical theory and computation 10.9 (2014): 3745-3756.
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityOverlapRegionsIndicatorComputer extends
		SilvaDensityComputer {

	/**
	 * calculate dori value of a grid point
	 * 
	 * @param densityPoint
	 *            -{@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 * @param doriValue
	 *            , pre-calculated
	 * @return doriValue, final-calculated
	 * 
	 */
	protected double getSilvaValue(DensityPoint densityPoint, double doriValue) {
		double gradient = densityPoint.getGradient();
		doriValue *= (4.0 / Math.pow(gradient, 3));
		doriValue /= (1.0 + doriValue);
		return doriValue;
	}

}
