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
package org.wallerlab.yoink.cube.domain;

import java.util.List;
import java.util.Set;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.math.constants.Constants;

/**
 * Cube domain Model stores all information about cube.
 *
 * Constructing a cube needs:
 * 1. Coordinate of the origin
 * 2. Number of steps along x/y/z axis
 * 3. Step size along x/y/z axis.
 */
public class SimpleCube implements Cube {

	private final int size;

	private String name;

	private final Coord origin;

	private final int[] numberOfXYZSteps;

	private final double[] xyzStepSize;

	private final List<Coord> coordinates;

	private final Set<Molecule> molecules;

	public SimpleCube(int size,
					  Coord origin,
					  int[] numberOfXYZSteps,
					  double[] xyzStepSize,
					  List<Coord> coordinates,
					  final Set<Molecule> molecules){
		this.size=size;
		this.origin = origin;
		this.numberOfXYZSteps = numberOfXYZSteps;
		this.xyzStepSize = xyzStepSize;
		this.coordinates = coordinates;
		this.molecules = molecules;

		for (int i = 0; i < 3; i++) {
			this.xyzStepSize[i] = xyzStepSize[i] * Constants.ANGSTROM_TO_BOHR;
		}
	}

	@Override
	public Coord getGridOrigin() {
		return origin;
	}

	@Override
	public int[] getNumberOfXYZSteps() {
		return numberOfXYZSteps;
	}

	@Override
	public double[] getXyzStepSize() {
		return xyzStepSize;
	}

	@Override
	public Set<Molecule> getMolecules() {
		return  molecules;
	}

	@Override
	public Set<Atom> getAtoms() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public List<Coord> getCoordinates() {
		return this.coordinates;
	}

}
