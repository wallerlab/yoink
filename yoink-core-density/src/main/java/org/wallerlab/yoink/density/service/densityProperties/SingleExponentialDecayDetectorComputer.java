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
 * This class is to get the single exponential decay detector(SEDD) value of a
 * grid point. For SEDD, see:De Silva, Piotr, Jacek Korchowiec, and Tomasz A.
 * Wesolowski. "Revealing the Bonding Pattern from the Molecular Electron
 * Density Using Single Exponential Decay Detector: An Orbital‐Free Alternative
 * to the Electron Localization Function." ChemPhysChem 13.15 (2012): 3462-3465.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class SingleExponentialDecayDetectorComputer extends
		SilvaDensityComputer {

	/**
	 * calculate SEDD of a denstiy point
	 * 
	 * @param densityPoint
	 *            -{@link org.wallerlab.yoink.api.model.density.DensityPoint}
	 * @param seddValue
	 *            pre-calculated
	 * @return seddValue final-calculated
	 */
	protected double getSilvaValue(DensityPoint densityPoint, double seddValue) {
		double density = densityPoint.getDensity();
		seddValue *= (4.0 / Math.pow(density, 8));
		seddValue = Math.log((1.0 + seddValue));
		return seddValue;
	}

}
