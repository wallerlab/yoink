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
package org.wallerlab.yoink.molecular.service.calculator;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Calculator;

/**
 * This class is to calculate the distance between two points
 * 
 * @author Min Zheng
 *
 */

@Service
public class TwoCoordsDistanceCalculator implements
		Calculator<Double, Coord, Coord> {

	/**
	 * calculate the distance between two coordinates
	 * 
	 * @param gridCoord1
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @param gridCoord2
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * @return the distance between gridCoord1 and gridCoord2
	 */
	public Double calculate(Coord gridCoord1, Coord gridCoord2) {
		return gridCoord2.getCoords().distance(gridCoord1.getCoords());
	}

}
