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

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.math.constants.Constants;

/**
 * this class is to calculate the density of molecules on a point
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityCalculator implements
		Calculator<Double, Coord, Set<Molecule>> {

	@Resource
	private Calculator<Double, Coord, Atom> atomDensityCalculator;

	protected static final Log log = LogFactory.getLog(DensityCalculator.class);

	/**
	 * calculate the density of a point from molecules. during density
	 * calculation, the density parameters are in the format of Vector
	 * 
	 * @param currentCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param molecules
	 *            -a Set of molecules
	 *            {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 * @return the density of a point from molecules
	 */
	public Double calculate(Coord currentCoord, Set<Molecule> molecules) {
		double density = loopOverEveryAtom(currentCoord, molecules);
		// if the density is too small, zero or close to zero, take the default
		// density value.
		density = Math.max(density, Constants.DENSITY_DEFAULT);
		return density;
	}

	private double loopOverEveryAtom(Coord currentCoord, Set<Molecule> molecules) {
		double density = 0;
		for (Molecule molecule : molecules) {
			for (Atom atom : molecule.getAtoms()) {
				density += atomDensityCalculator.calculate(currentCoord, atom);
			}
		}
		return density;
	}

}
