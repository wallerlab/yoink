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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecular.domain.SimpleCoord;

/**
 * this class is to get the coordinate of a grid point in cube
 * 
 * @author Min Zheng
 *
 */
@Service
public class CoordInCubeCalculator implements Calculator<Coord, int[], Cube> {

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	/**
	 * calculate the coordinate of a grid point in cube based on current x/y/z
	 * steps, x/y/z stepsize and the grid origin of cube
	 * 
	 * @param xyzCurrentStep
	 *            - an integer array of x/y/z current steps
	 * @param cube
	 *            -{@link org.wallerlab.yoink.api.model.cube.Cube } contains the
	 *            values of x/y/z stepsizes and the grid origin
	 * @return gridPointCoord -
	 *         {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	public Coord calculate(int[] xyzCurrentStep, Cube cube) {
		Vector originCoordMatrix = cube.getGridOrigin().getCoords();
		Vector xyzStepSize = myVector3D.create(cube.getXyzStepSize());
		Vector currentSteps = myVector3D.create(new double[] {
				xyzCurrentStep[0], xyzCurrentStep[1], xyzCurrentStep[2] });
		currentSteps.ebeMultiply(xyzStepSize);
		currentSteps.add(xyzStepSize);
		Vector coordinate = (currentSteps.ebeMultiply(xyzStepSize))
				.add(originCoordMatrix);
		Coord gridPointCoord = simpleCoordFactory.create();
		gridPointCoord.setCoords(coordinate);
		return gridPointCoord;
	}

}
