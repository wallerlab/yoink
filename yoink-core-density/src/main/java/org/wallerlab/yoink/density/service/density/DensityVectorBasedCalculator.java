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
package org.wallerlab.yoink.density.service.density;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.ElementVector;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.constants.Constants;

/**
 * this class is to calculate the density of molecules on a point
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityVectorBasedCalculator implements
		Calculator<Double, Coord, Set<Molecule>> {

	@Resource
	private Calculator<Double, Coord, Atom> atomDensityCalculator;

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	@Resource
	private ElementVector elementVectorImpl;

	protected static final Log log = LogFactory
			.getLog(DensityVectorBasedCalculator.class);

	/**
	 * calculate the density of a point from molecules
	 * 
	 * @param currentCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param molecules
	 *            -a Set of molecules
	 *            {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 * @return the density of a point from molecules
	 */
	public Double calculate(Coord currentCoord, Set<Molecule> molecules) {
		// long starting = System.currentTimeMillis();
		// get all atoms
		List<Atom> atoms = new ArrayList<Atom>();
		for (Molecule molecule : molecules) {
			atoms.addAll(molecule.getAtoms());
		}
		// group atoms by element type
		Map<Element, List<Atom>> atomMap = atoms.stream().collect(
				Collectors.groupingBy(atom -> atom.getElementType()));
		double density = loopOverEveryElement(currentCoord, atomMap);
		// if the density is too small, zero or close to zero, take the default
		// density value.
		density = Math.max(density, Constants.DENSITY_DEFAULT);
		// System.out.println("starting to ending (millseconds): "
		// + (System.currentTimeMillis() - starting));
		return density;
	}

	private double loopOverEveryElement(Coord currentCoord,
			Map<Element, List<Atom>> atomMap) {
		// loop over different atom types
		double density = 0;
		for (Element element : atomMap.keySet()) {
			elementVectorImpl.setElementType(element);
			Vector inverseZVector = elementVectorImpl.inverseZVector();
			Vector cVector = elementVectorImpl.cVector();
			density = loopOverEveryAtom(currentCoord, atomMap, density,
					element, inverseZVector, cVector);
		}
		return density;
	}

	private double loopOverEveryAtom(Coord currentCoord,
			Map<Element, List<Atom>> atomMap, double density, Element element,
			Vector inverseZVector, Vector cVector) {
		for (Atom a : atomMap.get(element)) {
			double distance = distanceCalculator.calculate(currentCoord, a);
			Vector sVector = inverseZVector.scalarMultiply(-distance);
			Vector expVector = sVector.exp();
			Vector densityVector = expVector.ebeMultiply(cVector);
			density = StatUtils.sum(densityVector.toArray()) + density;
		}
		return density;
	}

}
