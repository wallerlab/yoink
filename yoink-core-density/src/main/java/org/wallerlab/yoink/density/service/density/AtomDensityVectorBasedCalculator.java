/** Copyright 2014-2015 the original author or authors.
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
package org.wallerlab.yoink.density.service.density;

import javax.annotation.Resource;

import org.apache.commons.math3.stat.StatUtils;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.ElementVector;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this class is calculate atom density
 */
@Service
public class AtomDensityVectorBasedCalculator implements
		Calculator<Double, Coord, Atom> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	@Resource
	private ElementVector elementVectorImpl;

	/**
	 * during density calculation, the density parameters are in the format of
	 * Vector
	 */
	public Double calculate(Coord currentCoord, Atom atom) {
		double distance = distanceCalculator.calculate(currentCoord, atom);
		Element atomType = atom.getElementType();
		elementVectorImpl.setElementType(atomType);

		Vector inverseZVector = elementVectorImpl.inverseZVector();
		Vector cVector = elementVectorImpl.cVector();

		Vector sVector = inverseZVector.scalarMultiply(-distance);
		Vector expVector = sVector.exp();
		Vector densityVector = expVector.ebeMultiply(cVector);

		double density = StatUtils.sum(densityVector.toArray());
		return density;
	}

}
