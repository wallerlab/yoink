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

import java.util.Map;
import java.util.Set;

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;

/**
 * this interface is for a grid point in the cube
 * {@link org.wallerlab.yoink.api.model.cube.Cube}
 * 
 * @author Min Zheng
 *
 * @param <String>, the Key type of property Map of a grid point
 * @param <V>,the Value type of property Map of a grid point
 */
public interface GridPoint<String, V> {

	/**
	 * add one element in properties
	 * 
	 * @param k
	 *            :String , the key(name) of property
	 * @param v
	 *            :Object, the value of property
	 */
	public abstract void addProperty(String k, V v);

	/**
	 * get one element's value in properties by key K
	 * 
	 * @param k
	 *            {@link java.lang.String}
	 * @return V :Object, the value of property
	 */
	public abstract V getProperty(String k);

	/**
	 * get the value of coordinate
	 * 
	 * @return Coord {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	public abstract Coord getCoordinate();

	/**
	 * set the coordinate of a grid point
	 * 
	 * @param coordinate
	 *            {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	public abstract void setCoordinate(Coord coordinate);

	/**
	 * get the value of properties
	 * 
	 * @return properties : {@link java.util.Map}, the key is a String
	 */
	public abstract Map<String, V> getProperties();

	/**
	 * set the value of properties
	 * 
	 * @param properties
	 *            : {@link java.util.Map}, the key is a String
	 *
	 */
	public abstract void setProperties(Map<String, V> properties);

	/**
	 * get the index of a grid point in cube
	 * 
	 * @return int
	 */
	public abstract int getIndexInCube();

	/**
	 * set the index of a grid point in cube
	 * 
	 * @param indexInCube
	 *            , {@link java.lang.Integer}
	 */
	public abstract void setIndexInCube(int indexInCube);

	/**
	 * get two closest atoms in properties
	 * 
	 * @return a Set {@link java.util.Set} of Atoms
	 *         {@link org.wallerlab.yoink.api.model.molecular.Atom}
	 */
	public abstract Set<Atom> getTwoClosestAtoms();

	/**
	 * get two closest molecules in properties
	 * 
	 * @return a Set {@link java.util.Set} of Molecules
	 *         {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 */
	public abstract Set<Molecule> getTwoClosestMolecules();

	/**
	 * get atoms of two closest molecules of a grid point
	 * 
	 * @return a Set {@link java.util.Set} of Atoms
	 *         {@link org.wallerlab.yoink.api.model.molecular.Atom}
	 */
	public abstract Set<Atom> getAtomsInTwoClosestMolecules();

}
