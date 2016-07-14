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

import java.util.Set;

import org.wallerlab.yoink.api.model.region.Region;

/**
 * this interface is for domain model Molecule
 * 
 * @author Min Zheng
 *
 */
public interface Molecule {

	/**
	 * get the name of molecule. it represents which region the molecule is in
	 * 
	 * @return name, -
	 *         {@link Region.Name}
	 */
	Region.Name getName();

	/**
	 * set the name of molecule.
	 * 
	 * @param name
	 *            -indicates which the molecule is in,
	 *            {@link Region.Name}
	 */
	void setName(Region.Name name);

	/**
	 * get all atoms in the molecule
	 * 
	 * @return a List of atoms,
	 *         {@link Atom}
	 */
	Set<Atom> getAtoms();

	/**
	 * get the index of molecule in the molecule system
	 * 
	 * @return molecule index, {@link java.lang.Integer}
	 */
	int getIndex();

	/**
	 * get the center of mass of this molecule.
	 * 
	 * @return center of mass, -
	 *         {@link Coord}
	 */
	 Coord getCenterOfMass();

	/**
	 * set the center of mass of this molecule.
	 * 
	 * @param centerOfMass
	 *            - {@link Coord}
	 */
	 void setCenterOfMass(Coord centerOfMass);

	Double getDistanceToPoint();

	void setDistanceToPoint(Double distanceToPoint);

}
