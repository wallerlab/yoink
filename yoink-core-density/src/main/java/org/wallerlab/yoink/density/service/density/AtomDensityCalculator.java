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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.RadialGrid;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * this class is to calculate density from one atom
 * 
 * @author Min Zheng
 *
 */
@Service
public class AtomDensityCalculator implements Calculator<Double, Coord, Atom> {

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	/**
	 * calculate the density of a point from an atom
	 * 
	 * @param atom
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Atom}
	 * @param currentCoord
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @return the density of a point from an atom
	 */
	public Double calculate(Coord currentCoord, Atom atom) {
		double distance = distanceCalculator.calculate(currentCoord, atom);
		double density = 0;
		if (atom.getRadialGrid() == null) {
			Element atomType = atom.getElementType();
			double exp1 = Math.exp(-distance / atomType.z1());
			double exp2 = Math.exp(-distance / atomType.z2());
			double exp3 = Math.exp(-distance / atomType.z3());
			density = atomType.c1() * exp1 + atomType.c2() * exp2
					+ atomType.c3() * exp3;
		} else {

			double f = 0.0;
			// double fp=0.0;
			// double fpp=0.0;
			RadialGrid grid = atom.getRadialGrid();
			if (distance < grid.getPosition_max()) {

				int ir = 0;
				double r = 0;
				double[] grid_positions = grid.getGrid_positions();
				// careful with grid limits.
				if (distance <= grid_positions[0]) {
					
					ir = 1;
					r = grid_positions[0];
				} else {
					ir = (int) (1 + Math.floor(Math.log(distance / grid.getA())
							/ grid.getB()));
					r = distance;
				}
				
				double[] rr = new double[4];
				double[] dr1 = new double[4];
				double[][] x1dr12 = new double[4][4];
				
				for (int i = 0; i < 4; i++) {
					int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3 + i);
					rr[i] = grid_positions[ii];
					dr1[i] = r - rr[i];
					
					for (int j = 0; j <= i - 1; j++) {
						x1dr12[i][j] = 1.0 / (rr[i] - rr[j]);
						x1dr12[j][i] = -x1dr12[i][j];
						
					}
				}
		
				// interpolate, lagrange 3rd order, 4 nodes
				for (int i = 0; i < 4; i++) {
					int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3+ i);
					double prod = 1.0;
					for (int j = 0; j < 4; j++) {
						if (i == j) {
							continue;
						}
						
						prod = prod * dr1[j] * x1dr12[i][j];
					}
					// density=density+grid.getGrid_values()[ii]*prod;
					f = f + grid.getGrid_values()[ii] * prod;
					// fp=fp+grid.getFirst_derivative_of_grid_values()[ii]*prod;
					 //fpp=fpp+grid.getSecond_derivative_of_grid_values()[ii]*prod;
					
				}
			
				
			}// end of if distance
			
			density = f;
		}// end of grid density calculation
		return density;
	}

}
