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

import java.util.List;

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;

/**
 * the domain model for molecule.
 * 
 * @author Min Zheng
 *
 */
public class SimpleMolecule implements Molecule {

	private final int index;

	private final List<Atom> atoms;

	protected Region.Name name;

	private Coord centerOfMass;

	public SimpleMolecule(int index, List<Atom> atoms) {
		this.index = index;
		this.atoms = atoms;
	}

	/**
	 * get the molecular name
	 */
	@Override
	public Region.Name getName() {
		return name;
	}

	/**
	 * set the molecular name
	 */
	@Override
	public void setName(Region.Name value) {
		this.name = value;
	}

	/**
	 * get atoms in molecule
	 */
	@Override
	public List<Atom> getAtoms() {
		return this.atoms;
	}

	/**
	 * get molecular index in molecular system
	 */
	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Coord getCenterOfMass() {
		return this.centerOfMass;
	}

	@Override
	public void setCenterOfMass(Coord centerOfMass) {
		this.centerOfMass = centerOfMass;

	}

}
