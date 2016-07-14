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

import java.util.Set;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;

/**
 * the domain model for molecule.
 * 
 * @author Min Zheng
 *
 */
public class SimpleMolecule implements Molecule {

	private final int index;

	private final Set<Atom> atoms;

	protected Region.Name name;

	private Coord centerOfMass;

	private Double distanceToPoint;

	public SimpleMolecule(final int index, final Set<Atom> atoms) {
		this.index = index;
		this.atoms = atoms;
	}

	/**
	 * get the molecule name
	 */
	@Override
	public Region.Name getName() {
		return name;
	}

	/**
	 * set the molecule name
	 */
	@Override
	public void setName(Region.Name value) {
		this.name = value;
	}

	/**
	 * get atoms in molecule
	 */
	@Override
	public Set<Atom> getAtoms() {
		return this.atoms;
	}

	/**
	 * get molecule index in molecule system
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

	public Double getDistanceToPoint() {
		return distanceToPoint;
	}

	public void setDistanceToPoint(Double distanceToPoint) {
		this.distanceToPoint = distanceToPoint;
	}

	@Override
	public String toString() {
		return "SimpleMolecule{" +
				"index=" + index +
				", atoms=" + atoms +
				", name=" + name +
				", calculate=" + centerOfMass +
				'}';
	}

}
