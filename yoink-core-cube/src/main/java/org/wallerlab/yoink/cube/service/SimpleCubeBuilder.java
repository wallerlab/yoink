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

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.cube.domain.Cube;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.cube.domain.SimpleCube;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecule.domain.SimpleCoord;

import java.util.*;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.*;

/**
 * This class is to ratio the grid origin
 * and the number of steps along XYZ
 * axis to construct a cube.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SimpleCubeBuilder implements CubeBuilder<Set<MolecularSystem.Molecule>> {

	/**
	 * build a cube based on a Set of molecules
	 *
	 * @param xyzStepSizes
	 *            - a double array for step sizes along x/y/z axes
	 * @param molecules
	 *            - a Set of molecules -
	 *            {@link MolecularSystem.Molecule}
	 *
	 * @return cube -{@link Cube}
	 */
	public Cube build(double[] xyzStepSizes, Set<MolecularSystem.Molecule> molecules) {

		List<List<Double>> coords = new ArrayList<>();
		IntStream.rangeClosed(0,2).forEach(i -> coords.add(new ArrayList<>()));
		getXyzLists(molecules,coords);

		List<Double> cubeMins = new ArrayList();
		List<Double> cubeMaxs = new ArrayList();

		getMinMax(coords, cubeMins, cubeMaxs);
		System.out.println("mins are " + cubeMins);
		Coord origin = getOrigin(cubeMins);

		int[] numberOfXYZSteps = getNumberOfSteps(cubeMins,cubeMaxs,xyzStepSizes);
		int size = numberOfXYZSteps[0] * numberOfXYZSteps[1] * numberOfXYZSteps[2];

		double xOrigin = origin.getCoords().getX();
		double yOrigin = origin.getCoords().getY();
		double zOrigin = origin.getCoords().getZ();
		double xStep = xyzStepSizes[0];
		double yStep = xyzStepSizes[1];
		double zStep = xyzStepSizes[2];

		List<Coord> coordinates = new ArrayList<>();
		for (int x = 0; x < numberOfXYZSteps[0]-1; x++) {
			for (int y = 0; y < numberOfXYZSteps[1]-1; y++) {
				for (int z = 0; z < numberOfXYZSteps[2]-1; z++) {
					coordinates.add(new SimpleCoord(SimpleVector3DFactory.staticCreate(
							xOrigin + (xStep * x),
							yOrigin + (yStep * y),
							zOrigin + (zStep * z))));
				}
			}
		}
		return new SimpleCube(size,origin,numberOfXYZSteps,xyzStepSizes,coordinates,molecules);
	}

	private int[] getNumberOfSteps( List<Double> cubeMins, List<Double> cubeMaxs, double[] xyzStepSize ) {
		int[] numberOfXYZSteps = new int[3];
		for(int i= 0; i <= 2; i++)
			numberOfXYZSteps[i] = (int) Math.floor(((cubeMaxs.get(i) - cubeMins.get(i)) / xyzStepSize[i])+1);
		return numberOfXYZSteps;
	}

	private Coord getOrigin(List<Double> cubeMins) {
		return new SimpleCoord(SimpleVector3DFactory.staticCreate(cubeMins.get(0), cubeMins.get(1), cubeMins.get(2)));
	}

	private void getMinMax(List<List<Double>> coords, List<Double> cubeMins, List<Double> cubeMaxs ) {
		cubeMins.addAll(coords.stream().map( coord -> Collections.min(coord)).collect(toList()));
		cubeMaxs.addAll(coords.stream().map(coord -> Collections.max(coord)).collect(toList()));
		double border = 2.0;
		for(Double min:cubeMins) min -=  border;
		for(Double max:cubeMaxs) max +=  border;
	}

	private void getXyzLists(Set<MolecularSystem.Molecule> molecules, List<List<Double>> coords ) {
		molecules.stream()
				.flatMap(molecule -> molecule.getAtoms().stream())
				.forEach(atom -> {
						coords.get(0).add(atom.getCoordinate().getX());
						coords.get(1).add(atom.getCoordinate().getY());
						coords.get(2).add(atom.getCoordinate().getZ());
				});
	}


}
