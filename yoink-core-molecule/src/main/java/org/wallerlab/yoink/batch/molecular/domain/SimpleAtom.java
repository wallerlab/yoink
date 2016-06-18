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
package org.wallerlab.yoink.batch.molecular.domain;

import org.wallerlab.yoink.batch.api.model.molecular.Atom;
import org.wallerlab.yoink.batch.api.model.molecular.Coord;
import org.wallerlab.yoink.batch.api.model.molecular.Element;
import org.wallerlab.yoink.batch.api.model.molecular.RadialGrid;

/**
 * the domain model of atom
 * 
 * @author Min Zheng
 *
 */
public class SimpleAtom implements Atom {

	protected final int index;

	protected final Element elementType;

	private final Coord coordinate;

	private RadialGrid radial_grid;

	public SimpleAtom(int index, Element elementType, Coord coordinate) {
		this.index = index;
		this.elementType = elementType;
		this.coordinate = coordinate;
	}

	/**
	 * get the element type of the atom
	 */
	@Override
	public Element getElementType() {
		return elementType;
	}

	/**
	 * get the atomic index in molecular system
	 */
	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * get the coordinate of the atom
	 */
	@Override
	public Coord getCoordinate() {
		return coordinate;
	}

	/**
	 * get the x coordinate of the atom
	 */
	@Override
	public double getX3() {
		return this.coordinate.getCoords().getX();
	}

	/**
	 * get the y coordinate of the atom
	 */
	@Override
	public double getY3() {
		return this.coordinate.getCoords().getY();
	}

	/**
	 * get the z coordinate of the atom
	 */
	@Override
	public double getZ3() {
		return this.coordinate.getCoords().getZ();
	}

	/**
	 * set the radial grid
	 */
	@Override
	public RadialGrid getRadialGrid() {
		
		return this.radial_grid;
	}

	/**
	 * get the radial grid
	 */
	@Override
	public void setRadialGrid(RadialGrid radial_grid) {

		this.radial_grid=radial_grid;
	}
}
