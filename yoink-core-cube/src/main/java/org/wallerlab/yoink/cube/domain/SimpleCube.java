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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.math.constants.Constants;

/**
 * Cube domain Model stores all information about cube. Constructing a cube
 * needs the coordinate of the origin grid point, number of steps along x/y/z
 * axis, and also step size along x/y/z axis.
 */

@Component
public class SimpleCube implements Cube {

	private Coord origin;

	// if we want to use it to hold values.
	private List<Double> values;

	private int[] numberOfXYZSteps = new int[3];

	private final double[] xyzStepSize = new double[3];

	private Set<Molecule> molecules;

	private String name;

	private int size;

	private List<Coord> coordinates;

	private List<DensityType> densityTypes = new ArrayList<DensityType>();

	/**
	 * use xyz step sizes construct a cube and convert the unit of step size
	 * from angstrom to bohr
	 * 
	 * @param xyzStepSize
	 *            -a double array with xyz step sizes
	 * 
	 */
	public SimpleCube(double[] xyzStepSize) {
		for (int i = 0; i < 3; i++) {
			this.xyzStepSize[i] = xyzStepSize[i] * Constants.ANGSTROM_TO_BOHR;
		}
	}

	/**
	 * Construct when size is known, or use setter to set number of size
	 * 
	 * @param cubeSize
	 *            , the total number of grid points in the cube
	 * 
	 */
	public SimpleCube(int cubeSize) {
		this.size = cubeSize;
		this.values = Arrays.asList(new Double[cubeSize]);
	}

	/**
	 * default cube constructor
	 */
	public SimpleCube() {
	}

	@Override
	public Coord getGridOrigin() {
		return origin;
	}

	@Override
	public void setGridOrigin(Coord gridOrigin) {
		this.origin = gridOrigin;
	}

	@Override
	public int[] getNumberOfXYZSteps() {
		return numberOfXYZSteps;
	}

	@Override
	public void setNumberOfXYZSteps(int[] numberOfXYZSteps) {
		this.numberOfXYZSteps = numberOfXYZSteps;
	}

	@Override
	public double[] getXyzStepSize() {
		return xyzStepSize;
	}

	@Override
	public Set<Molecule> getMolecules() {
		return molecules;
	}

	/**
	 * get all atoms in the cube
	 */
	@Override
	public Set<Atom> getAtoms() {
		Set<Atom> atoms = new HashSet<Atom>();
		for (Molecule molecule : this.molecules) {
			atoms.addAll(molecule.getAtoms());
		}
		return atoms;
	}

	@Override
	public void setMolecules(Set<Molecule> molecules) {
		this.molecules = molecules;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Value("#{'${yoink.cube.densityTypes}'.split(',')}")
	public List<DensityType> getDensityTypes() {
		return densityTypes;
	}

	@Override
	public void setDensityTypes(List<DensityType> densityTypes) {
		this.densityTypes = densityTypes;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public List<Coord> getCoordinates() {
		return coordinates;
	}

	@Override
	public void setCoordinates(List<Coord> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public List<Double> getValues() {
		return this.values;
	}

	@Override
	public void setValues(List<Double> values) {
		this.values = values;
	}

}
