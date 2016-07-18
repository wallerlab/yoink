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

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

/**
 * this interface is for domain model cube
 * 
 * @author Min Zheng
 *
 */
public interface Cube {

	/**
	 * get the value of origin coordinate of the cube
	 * 
	 * @return {@link Coord}
	 */
	Coord getGridOrigin();

	/**
	 * get the number of steps along xyz axes
	 * 
	 * @return an int array
	 */
	int[] getNumberOfXYZSteps();

	/**
	 * get the step size along xyz axes
	 * 
	 * @return a double array
	 */
	double[] getXyzStepSize();

	/**
	 * get the molecules in the cube
	 * 
	 * @return a Set of molecules. -
	 *         {@link MolecularSystem.Molecule}
	 */
	Set<MolecularSystem.Molecule> getMolecules();

	/**
	 * get the atoms in the cube
	 * 
	 * @return a Set of atoms.-
	 *         {@link MolecularSystem.Molecule.Atom}
	 */
	 Set<MolecularSystem.Molecule.Atom> getAtoms();

	/**
	 * get the number of grid points in the cube
	 * 
	 * @return number of grid points -{@link java.lang.Integer}
	 */
	int getSize();

	/**
	 * get the coordinates of grid points in the cube
	 * 
	 * @return a {@link Coord} List
	 */
	List<Coord> getCoordinates();

}
