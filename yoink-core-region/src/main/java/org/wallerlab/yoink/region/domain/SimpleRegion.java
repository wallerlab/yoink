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
package org.wallerlab.yoink.region.domain;

import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

import java.util.*;
import static java.util.stream.Collectors.toSet;

/**
 * A region is a named set of molecules.
 *
 * it has a size and a center of mass.
 * 
 * @author Min Zheng
 * 
 *
 */
public class SimpleRegion implements Region {

	private final Integer size;

	private final Region.Name name;

	private final Set<Molecule> molecules;

	private Coord centerOfMass;

	public SimpleRegion(final Region.Name name, final Set<Molecule> moleculesInRegion) {
		this.name = name;
		this.molecules = moleculesInRegion;
		this.size = molecules.size();
	}

	/**
	 * get the number of molecules in the region
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * get molecules in the region
	 */
	public Set<Molecule> getMolecules() {return molecules;}


	// Do we really want to add molecules to a set???? this should be final?
	@Override
	public void addMolecule(Molecule molecule) {
		this.molecules.add(molecule);
	}

	/**
	 * get the name of the region
	 */

	public Region.Name getName() { return name;}

	@Override
	public boolean containsAll(Set<Molecule> molecules) {
		return molecules.containsAll(molecules);
	}

	/**
	 * get all atoms in the region
	 */
	public Set<Atom> getAtoms() {
		return  molecules.stream()
				         .flatMap(molecule -> molecule.getAtoms().stream())
						 .collect(toSet());
	}

	/**
	 * get the center of mass of the region
	 */
	public Coord getCenterOfMass() {
		if(centerOfMass !=null) return centerOfMass;
		else {
			double massOfMolecule = 0;
			Vector massWeightedCoordinate = SimpleVector3DFactory.staticCreate(0, 0, 0);
			for (Molecule molecule : molecules) {
				for (Atom atom : molecule.getAtoms()) {
					double atomMass = atom.getElementType().atomMass();
					Vector matrix = atom.getCoordinate().getCoords();
					matrix = matrix.scalarMultiply(atomMass);
					massWeightedCoordinate = matrix.add(massWeightedCoordinate);
					massOfMolecule = massOfMolecule + atomMass;
				}
			}
			Vector centerCoordinate = massWeightedCoordinate.scalarMultiply(1.0 / massOfMolecule);
		}
		return centerOfMass;
	}

	//TODO deprecate this
	@Override
	public Map<Molecule, Integer> getMolecularMap() {
		return null;
	}

	/**
	 * change the id of molecules with the name of region
	 */
	//TODO Deprecate  this
	public void changeMolecularId() {
		if (getSize() != 0)
			for (Molecule molecule : molecules) molecule.setName(this.name);
	}

	/**
	 * change the id of molecules with the given name
	 */
	//TODO Deprecate  this
	public void changeMolecularId(org.wallerlab.yoink.api.model.region.Region.Name name) {
		if (getSize() != 0)
			for (Molecule molecule : molecules) molecule.setName(name);
	}

}
