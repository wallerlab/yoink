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
package org.wallerlab.yoink.density.domain;

import java.io.Serializable;

public class SimpleRadialGrid implements RadialGrid, Serializable {

	//  ri = exponent * exp(zeta * (i-1))
	private final double exponent;

	private final double zeta;

	private final int numberOfGrids;

	private final double[] gridPositions;

	private final double[] gridValues;//   4*pi*r^2*rho

	private final double[] firstDerivativeOfGridValues;            // fp(:)

	private final double[] secondDerivativeOfGridValues;           // fpp(:)

	private final double maxGridDistance;

	public SimpleRadialGrid(double exponent,
							double zeta,
							int numberOfGrids,
							double[] gridPositions,
							double[] gridValues,
							double[] firstDerivativeOfGridValues,
							double[] secondDerivativeOfGridValues,
							double maxGridDistance) {
		this.exponent = exponent;
		this.zeta = zeta;
		this.numberOfGrids = numberOfGrids;
		this.gridPositions = gridPositions;
		this.gridValues = gridValues;
		this.firstDerivativeOfGridValues = firstDerivativeOfGridValues;
		this.secondDerivativeOfGridValues = secondDerivativeOfGridValues;
		this.maxGridDistance = maxGridDistance;
	}

	/* (non-Javadoc)
         * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getMaxGridDistance()
         */
	public double getMaxGridDistance() {
		return maxGridDistance;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getExponent()
	 */
	public double getExponent() {
		return exponent;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getZeta()
	 */
	public double getZeta() {
		return zeta;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getNumberOfGrids()
	 */
	public int getNumberOfGrids() {
		return numberOfGrids;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getGridPositions()
	 */
	public double[] getGridPositions() {
		return gridPositions;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getGridValues()
	 */
	public double[] getGridValues() {
		return gridValues;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getFirstDerivativeOfGridValues()
	 */
	public double[] getFirstDerivativeOfGridValues() {
		return firstDerivativeOfGridValues;
	}

	/* (non-Javadoc)
	 * @see org.wallerlab.yoink.molecule.domain.RadialGrid#getSecondDerivativeOfGridValues()
	 */
	public double[] getSecondDerivativeOfGridValues() {
		return secondDerivativeOfGridValues;
	}

	@Override
	public String toString() {
		return "SimpleRadialGrid{" +
				"exponent=" + exponent +
				", zeta=" + zeta +
				", numberOfGrids=" + numberOfGrids +
				", maxGridDistance=" + maxGridDistance +
				'}';
	}
}
