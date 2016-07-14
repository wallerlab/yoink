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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.cube.domain.Cube;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.cube.domain.SimpleCube;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecule.domain.SimpleCoord;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.*;

/**
 * This class is to calculate the grid origin
 * and the number of steps along XYZ
 * axis to construct a cube.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SimpleCubeBuilder implements CubeBuilder<Set<Molecule>> {

	@Resource
	private Calculator<Coord, int[], Cube> coordInCubeCalculator;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	@Qualifier("simpleCoordFactory")
	private Factory<Coord, double[]> coordFactory;

	/**
	 * build a cube based on a Set of molecules
	 *
	 * @param xyzStepSizes
	 *            - a double array for step sizes along x/y/z axes
	 * @param molecules
	 *            - a Set of molecules -
	 *            {@link Molecule}
	 *
	 * @return cube -{@link Cube}
	 */
	public Cube build(double[] xyzStepSizes, Set<Molecule> molecules) {

		List<List<Double>> coords = new ArrayList<>();
		IntStream.rangeClosed(0,2).forEach(i -> coords.add(new ArrayList<>()));
		getxyzLists(molecules,coords);

		List<Double> cubeMins = new ArrayList();
		List<Double> cubeMaxs = new ArrayList();
		getMinMax(coords, cubeMins, cubeMaxs);

		Coord origin = getOrigin(cubeMins);

		int[] numberOfXYZSteps = getNumberOfSteps(cubeMins,cubeMaxs,xyzStepSizes);
		int size = numberOfXYZSteps[0] * numberOfXYZSteps[1] * numberOfXYZSteps[2];

		List<Coord> coordinates = getAllCoordinates(size,numberOfXYZSteps,origin,xyzStepSizes);

		return new SimpleCube(size,origin,numberOfXYZSteps,xyzStepSizes,coordinates,molecules);
	}

	private List<Coord> getAllCoordinates(int size,
										  int[] numberOfXYZSteps,
										  Coord origin,
										  double[] xyzStepSizes ) {

		Coord[] initialValues = new SimpleCoord[size];
		List<Coord> coordinates = Collections.synchronizedList(Arrays.asList(initialValues));

		int xMax = numberOfXYZSteps[0];
		int yMax = numberOfXYZSteps[1];
		int zMax = numberOfXYZSteps[2];

		IntStream.range(0, numberOfXYZSteps[0])
				.forEach(nXStep -> {
					for (int y = 0; y < yMax; y++) {
				for (int z = 0; z < zMax; z++) {
					int[] xyzCurrentStep = new int[] { nXStep, y, z };
					Coord currentCoord = calculate(xyzCurrentStep,origin, xyzStepSizes);
					int indexInCube = nXStep * yMax * xMax + y * zMax + z;
					coordinates.set(indexInCube, currentCoord);
				}
			}
		});
		return coordinates;
	}

	private int[] getNumberOfSteps( List<Double> cubeMins, List<Double> cubeMaxs, double[] xyzStepSize ) {
		int[] numberOfXYZSteps = new int[3];
		for(int i= 0; i <= 2; i++){
			numberOfXYZSteps[i] = (int) Math.floor(((cubeMaxs.get(i) - cubeMins.get(i)) / xyzStepSize[i])+1);
		}
		return numberOfXYZSteps;
	}

	private Coord getOrigin(List<Double> cubeMins) {
		double[] dimensions = new double[] { cubeMins.get(0), cubeMins.get(1), cubeMins.get(2)};
		Coord gridOrigin = coordFactory.create(dimensions);
		return gridOrigin;
	}

	private void getMinMax(List<List<Double>> coords, List<Double> cubeMins, List<Double> cubeMaxs ) {
		cubeMins.addAll( coords.stream().map(coord -> Collections.min(coord)).collect(toList()));
		cubeMaxs.addAll(coords.stream().map(coord -> Collections.max(coord)).collect(toList()));
		double border = 2.0;
		for(Double min:cubeMins) min =  min - border;
		for(Double max:cubeMaxs) max =  max - border;
	}

	private void getxyzLists(Set<Molecule> molecules, List<List<Double>> coords ) {
		molecules.stream()
				.flatMap(molecule -> molecule.getAtoms().stream())
				.forEach(atom -> {
						coords.get(0).add(atom.getX3());
						coords.get(1).add(atom.getY3());
						coords.get(2).add(atom.getZ3());
				});
	}

	/**
	 * calculate the coordinate of a grid point in cube based on current x/y/z
	 * steps, x/y/z stepsize and the grid origin of cube
	 *
	 * @param xyzCurrentStep
	 *            - an integer array of x/y/z current steps
	 * @param cube
	 *            -{@link Cube } contains the
	 *            values of x/y/z stepsizes and the grid origin
	 * @return gridPointCoord -
	 *         {@link Coord}
	 */
	private Coord calculate(int[] xyzCurrentStep, Coord origin, double[] xyzStepSizes) {
		Vector originCoordMatrix = origin.getCoords();
		Vector xyzStepSize = myVector3D.create(xyzStepSizes);
		Vector currentSteps = create(xyzCurrentStep);
		currentSteps.ebeMultiply(xyzStepSize);
		currentSteps.add(xyzStepSize);
		org.wallerlab.yoink.api.service.math.Vector coordinate = (currentSteps.ebeMultiply(xyzStepSize)).add(originCoordMatrix);
		Coord gridPointCoord = coordFactory.create();
		gridPointCoord.setCoords(coordinate);
		return gridPointCoord;
	}

	private org.wallerlab.yoink.api.service.math.Vector create(int[] xyzCurrentStep){
		return myVector3D.create(new double[] {xyzCurrentStep[0], xyzCurrentStep[1], xyzCurrentStep[2] });
	}

}
