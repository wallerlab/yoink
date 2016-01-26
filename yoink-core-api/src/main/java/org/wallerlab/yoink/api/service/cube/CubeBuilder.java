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
package org.wallerlab.yoink.api.service.cube;

import org.wallerlab.yoink.api.model.cube.Cube;

@FunctionalInterface
public interface CubeBuilder<T> {

	/**
	 * 
	 * @param xyzStepSize
	 *            , a double array for step size along x/y/z axes
	 * @param t
	 *            , it contains the molecular information to build the cube. eg.
	 *            a Set of molecules, {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 * @return cube {@link org.wallerlab.yoink.api.model.cube.Cube}
	 */
	 Cube build(double[] xyzStepSize, T t);
}
