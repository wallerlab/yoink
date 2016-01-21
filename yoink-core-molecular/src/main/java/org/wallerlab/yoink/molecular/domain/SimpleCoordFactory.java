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
package org.wallerlab.yoink.molecular.domain;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * factory to generate new SimpleCoord instance
 * 
 * @author Min Zheng
 *
 */
@Service
public class SimpleCoordFactory implements Factory<Coord, double[]> {

	@Resource
	private SimpleVector3DFactory myVector3D;

	/**
	 * make a new Coord. x/y/z values are zero.
	 * 
	 * @return newCooord -Coord
	 *         {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	public Coord create() {
		Vector coordVector = myVector3D.create(0, 0, 0);
		Coord newCooord = new SimpleCoord(coordVector);
		return newCooord;
	}

	/**
	 * use an array with 3 elements to make a new Coord
	 * 
	 * @param d
	 *            array double[3]
	 * @return newCooord -Coord
	 *         {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	public Coord create(double[] d) {
		Vector coordVector = myVector3D.create(d);
		Coord newCooord = new SimpleCoord(coordVector);
		return newCooord;
	}
}
