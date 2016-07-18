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
package org.wallerlab.yoink.molecule.domain;

import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * the domain model of atom
 * 
 * @author Min Zheng
 *
 */
public class SimpleAtom implements MolecularSystem.Molecule.Atom {

	protected final int index;

	protected final Element element;

	//The unit is Bohr.
	private final Vector coordinate;

	public SimpleAtom(int index, Element elementType, Vector coordinate) {
		this.index = index;
		this.element = elementType;
		this.coordinate = coordinate;
	}

	/**
	 * get the element type of the atom
	 */
	@Override
	public Element getElement() {
		return element;
	}

	/**
	 * get the atomic index in molecule system
	 */
	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * get the coordinate of the atom
	 */
	public Vector getCoordinate() {return coordinate;}


	@Override
	public String toString() {
		return "Atom{" +
				"index=" + index +
				", element=" + element +
				", coordinate=" + coordinate +
				'}';
	}
}
