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

import java.util.Set;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;

/**
 * this interface is for a grid point in the cube
 * {@link Cube}
 * 
 * @author Min Zheng
 *
 * @param <String>, the Key type of property Map of a grid point
 * @param <V>,the Value type of property Map of a grid point
 */
public interface VoronoiPoint<String, V> {

	/**
	 * get the index of a grid point in cube
	 *
	 * @return int
	 */
	int getIndex();

	/**
	 * get the value of coordinate
	 * 
	 * @return Coord {@link Coord}
	 */
	Coord getCoordinate();

	Set<Molecule> getNearestMolecules();

	Set<Atom> getNearestAtoms();

}
