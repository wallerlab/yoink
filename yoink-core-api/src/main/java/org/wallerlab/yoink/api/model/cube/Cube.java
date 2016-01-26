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
package org.wallerlab.yoink.api.model.cube;

import java.util.List;
import java.util.Set;

import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;

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
	 * @return {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	Coord getGridOrigin();

	/**
	 * set the value of origin coordinate of the cube
	 * 
	 * @param gridOrigin
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	void setGridOrigin(Coord gridOrigin);

	/**
	 * get the number of steps along xyz axes
	 * 
	 * @return an int array
	 */
	int[] getNumberOfXYZSteps();

	/**
	 * set the number of steps along xyz axes
	 * 
	 * @param numberOfXYZSteps
	 *            , an int array with 3 elements
	 */
	void setNumberOfXYZSteps(int[] numberOfXYZSteps);

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
	 *         {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 */
	Set<Molecule> getMolecules();

	/**
	 * get the atoms in the cube
	 * 
	 * @return a Set of atoms.-
	 *         {@link org.wallerlab.yoink.api.model.molecular.Atom}
	 */
	 Set<Atom> getAtoms();

	/**
	 * set the value of molecules in the cube
	 * 
	 * @param molecules
	 *            -a Set of molecules.
	 *            {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 */
	void setMolecules(Set<Molecule> molecules);

	/**
	 * get the name of the cube
	 * 
	 * @return name -{@link java.lang.String}
	 */
	String getName();

	/**
	 * set the name of the cube
	 * 
	 * @param name
	 *            -{@link java.lang.String}
	 */
	void setName(String name);

	/**
	 * get the value of density types of the cube
	 * 
	 * @return a
	 *         {@link org.wallerlab.yoink.api.model.density.DensityPoint.DensityType }
	 *         List
	 */
	List<DensityType> getDensityTypes();

	/**
	 * set the value of density types of the cube
	 * 
	 * @param densityTypes
	 *            -a
	 *            {@link org.wallerlab.yoink.api.model.density.DensityPoint.DensityType }
	 *            List
	 */
	void setDensityTypes(List<DensityType> densityTypes);

	/**
	 * get the number of grid points in the cube
	 * 
	 * @return number of grid points -{@link java.lang.Integer}
	 */
	int getSize();

	/**
	 * set the number of grid points in the cube
	 * 
	 * @param size
	 *            , number of grid points -{@link java.lang.Integer}
	 */
	void setSize(int size);

	/**
	 * get the coordinates of grid points in the cube
	 * 
	 * @return a {@link org.wallerlab.yoink.api.model.molecular.Coord} List
	 */
	List<Coord> getCoordinates();

	/**
	 * set the coordinates of grid points in the cube
	 * 
	 * @param coordinates
	 *            , a {@link org.wallerlab.yoink.api.model.molecular.Coord} List
	 */
	void setCoordinates(List<Coord> coordinates);

	/**
	 * get the values of grid points in the cube
	 * 
	 * @return a {@link java.lang.Double} List
	 */
	List<Double> getValues();

	/**
	 * set teh values of grid points in the cube
	 * 
	 * @param values
	 *            , a {@link java.lang.Double} List
	 */
	void setValues(List<Double> values);
}
