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
package org.wallerlab.yoink.api.model.molecule;

/**
 * this interface is for domain model atom
 * 
 * @author Min Zheng
 *
 */
public interface Atom {

	/**
	 * get the element of this atom
	 * 
	 * @return element of this atom, -
	 *         {@link org.wallerlab.yoink.api.model.molecule.Element}
	 */
	Element getElementType();

	/**
	 * get the index of this atom in molecular system
	 * 
	 * @return atomic index in molecular system -{@link java.lang.Integer}
	 */
	int getIndex();
	

	/**
	 * get the radial grid 
	 * 
	 * @return radial grids for density -{@link org.wallerlab.yoink.api.model.molecule.RadialGrid}
	 */
	RadialGrid getRadialGrid();
	

	/**
	 * set the radial grid 
	 *
	 * @param radial_grid the grid to be used
	 * 
	 */
	void setRadialGrid(RadialGrid radial_grid);


	/**
	 * get the coordinate of this atom
	 * @return {@link org.wallerlab.yoink.api.model.molecule.Coord}
	 */
	Coord getCoordinate();

	/**
	 * get the x coordinate of this atom
	 * @return {@link java.lang.Double}
	 */
	double getX3();

	/**
	 * get the y coordinate of this atom
	 * @return {@link java.lang.Double}
	 */
	double getY3();

	/**
	 * get the z coordinate of this atom
	 * @return {@link java.lang.Double}
	 */
	double getZ3();

}
