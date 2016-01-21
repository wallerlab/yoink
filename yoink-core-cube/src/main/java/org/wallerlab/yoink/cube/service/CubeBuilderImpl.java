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
package org.wallerlab.yoink.cube.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.cube.CubeBuilder;
import org.wallerlab.yoink.cube.domain.SimpleCube;
import org.wallerlab.yoink.molecular.domain.SimpleCoord;
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory;

/**
 * This class is to calculate the grid origin and the number of steps along XYZ
 * axis to construct a cube.
 * 
 * @author Min Zheng
 *
 */
@Service
public class CubeBuilderImpl implements CubeBuilder<Set<Molecule>> {

	@Resource
	private Calculator<Coord, int[], Cube> coordInCubeCalculator;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	/**
	 * build a cube based on a Set of molecules
	 * 
	 * @param xyzStepSize
	 *            - a double array for step sizes along x/y/z axes
	 * @param molecules
	 *            - a Set of molecules -
	 *            {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 * 
	 * @return cube -{@link org.wallerlab.yoink.api.model.cube.Cube}
	 */
	public Cube build(double[] xyzStepSize, Set<Molecule> molecules) {
		Cube cube = new SimpleCube(xyzStepSize);
		double[] xyzMinimumOfCube = new double[3];
		double[] xyzMaximumOfCube = new double[3];
		// create three lists for x/y/z coordinates in system
		List<Double> xCoordOfAllMolecules = new ArrayList<Double>();
		List<Double> yCoordOfAllMolecules = new ArrayList<Double>();
		List<Double> zCoordOfAllMolecules = new ArrayList<Double>();
		// get three lists x/y/z coordinates in system
		getxyzLists(molecules, xCoordOfAllMolecules, yCoordOfAllMolecules,
				zCoordOfAllMolecules);
		// get minimum and maximum of x/y/z coordinates
		getMinMax(xCoordOfAllMolecules, yCoordOfAllMolecules,
				zCoordOfAllMolecules, cube, xyzMinimumOfCube, xyzMaximumOfCube);
		// set the grid origin of cube
		setOrigin(cube, xyzMinimumOfCube);
		// set the number of x/y/z steps
		setNumberOfSteps(cube, xyzMinimumOfCube, xyzMaximumOfCube);
		// set molecules in cube
		cube.setMolecules(molecules);
		// get coordinates for all the grid points in cube
		List<Coord> coordinates = getAllCoordinates(cube);
		// set coordinates for all the grid points in cube
		cube.setCoordinates(coordinates);
		return cube;
	}

	private List<Coord> getAllCoordinates(Cube cube) {
		Coord[] initialValues = new SimpleCoord[cube.getSize()];
		List<Coord> coordinates = Collections.synchronizedList(Arrays
				.asList(initialValues));
		IntStream
				.range(0, cube.getNumberOfXYZSteps()[0])
				.parallel()
				.forEach(
						nXStep -> {
							for (int nYStep = 0; nYStep < cube
									.getNumberOfXYZSteps()[1]; nYStep++) {
								for (int nZStep = 0; nZStep < cube
										.getNumberOfXYZSteps()[2]; nZStep++) {
									// get the coordinate in cube
									int[] xyzCurrentStep = new int[] { nXStep,
											nYStep, nZStep };
									Coord currentCoord = coordInCubeCalculator
											.calculate(xyzCurrentStep, cube);
									// get the index of a grid point in cube
									int indexInCube = nXStep
											* cube.getNumberOfXYZSteps()[1]
											* cube.getNumberOfXYZSteps()[2]
											+ nYStep
											* cube.getNumberOfXYZSteps()[2]
											+ nZStep;
									coordinates.set(indexInCube, currentCoord);
								}
							}
						});
		return coordinates;
	}

	private void setNumberOfSteps(Cube cube, double[] xyzMinimumOfCube,
			double[] xyzMaximumOfCube) {
		int[] numberOfXYZSteps = new int[3];
		numberOfXYZSteps[0] = (int) Math
				.floor(((xyzMaximumOfCube[0] - xyzMinimumOfCube[0]) / cube
						.getXyzStepSize()[0]) + 1);
		numberOfXYZSteps[1] = (int) Math
				.floor(((xyzMaximumOfCube[1] - xyzMinimumOfCube[1]) / cube
						.getXyzStepSize()[1]) + 1);
		numberOfXYZSteps[2] = (int) Math
				.floor(((xyzMaximumOfCube[2] - xyzMinimumOfCube[2]) / cube
						.getXyzStepSize()[2]) + 1);
		cube.setNumberOfXYZSteps(numberOfXYZSteps);
		cube.setSize(numberOfXYZSteps[0] * numberOfXYZSteps[1]
				* numberOfXYZSteps[2]);

	}

	private void setOrigin(Cube cube, double[] xyzMinimumOfCube) {
		// Coord gridOrigin = new SimpleCoord(xyzMinimumOfCube[0],
		// xyzMinimumOfCube[1], xyzMinimumOfCube[2]);
		Coord gridOrigin = simpleCoordFactory
				.create(new double[] { xyzMinimumOfCube[0],
						xyzMinimumOfCube[1], xyzMinimumOfCube[2] });
		cube.setGridOrigin(gridOrigin);
	}

	private void getMinMax(List<Double> xCoordOfAllMolecules,
			List<Double> yCoordOfAllMolecules,
			List<Double> zCoordOfAllMolecules, Cube cube,
			double[] xyzMinimumOfCube, double[] xyzMaximumOfCube) {
		double xMinimumOfCube = Collections.min(xCoordOfAllMolecules);
		double yMinimumOfCube = Collections.min(yCoordOfAllMolecules);
		double zMinimumOfCube = Collections.min(zCoordOfAllMolecules);
		double xMaximumOfCube = Collections.max(xCoordOfAllMolecules);
		double yMaximumOfCube = Collections.max(yCoordOfAllMolecules);
		double zMaximumOfCube = Collections.max(zCoordOfAllMolecules);
		// build a larger cube to write cube files
		if (cube.getDensityTypes().size() != 0) {
			xMinimumOfCube -= 2;
			yMinimumOfCube -= 2;
			zMinimumOfCube -= 2;
			xMaximumOfCube += 2;
			yMaximumOfCube += 2;
			zMaximumOfCube += 2;
		}
		xyzMinimumOfCube[0] = xMinimumOfCube;
		xyzMinimumOfCube[1] = yMinimumOfCube;
		xyzMinimumOfCube[2] = zMinimumOfCube;
		xyzMaximumOfCube[0] = xMaximumOfCube;
		xyzMaximumOfCube[1] = yMaximumOfCube;
		xyzMaximumOfCube[2] = zMaximumOfCube;
	}

	private void getxyzLists(Set<Molecule> molecules,
			List<Double> xCoordOfAllAtoms, List<Double> yCoordOfAllAtoms,
			List<Double> zCoordOfAllAtoms) {
		for (Molecule molecule : molecules) {
			for (Atom atom : molecule.getAtoms()) {
				xCoordOfAllAtoms.add(atom.getX3());
				yCoordOfAllAtoms.add(atom.getY3());
				zCoordOfAllAtoms.add(atom.getZ3());
			}
		}
	}

}
