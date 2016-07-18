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

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecule.domain.SimpleCoord;

import java.util.*;
import static java.util.stream.Collectors.toSet;

/**
 * A region is a named set of molecules.
 *
 * it also has a size and a center of mass.
 * 
 * @author Min Zheng
 * 
 *
 */
public class SimpleRegion implements Region {

	private final Integer size;

	private final Region.Name name;

	private final Set<MolecularSystem.Molecule> molecules;

	//Lazy eval, just in case it is not needed.
	private Vector centerOfMass;

	public SimpleRegion(final Region.Name name, final Set<MolecularSystem.Molecule> moleculesInRegion) {
		this.name = name;
		this.molecules = moleculesInRegion;
		this.size = molecules.size();
	}

	/**
	 * get the name of the region
	 */
	public Region.Name getName() { return name;}

	/**
	 * get the number of molecules in the region
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * get molecules in the region
	 */
	public Set<MolecularSystem.Molecule> getMolecules() {return molecules;}


	@Override
	public boolean containsAll(Set<MolecularSystem.Molecule> molecules) {
		return molecules.containsAll(molecules);
	}

	/**
	 * get all atoms in the region
	 */
	public Set<MolecularSystem.Molecule.Atom> getAtoms() {
		return molecules.stream()
				         .flatMap(molecule -> molecule.getAtoms().stream())
						 .collect(toSet());
	}

	/**
	 * get the center of mass of the region
	 */
	public Coord getCenterOfMass() {
		if(centerOfMass !=null) return new SimpleCoord(centerOfMass);
		else {
			double massOfMolecule = 0;
			Vector massWeightedCoordinate  = SimpleVector3DFactory.staticCreate(0.0,0.0,0.0);
			for (MolecularSystem.Molecule molecule : molecules) {
				for (MolecularSystem.Molecule.Atom atom : molecule.getAtoms()) {
					massWeightedCoordinate = atom.getCoordinate().scalarMultiply(atom.getElement().atomMass());
					massOfMolecule = massOfMolecule + atom.getElement().atomMass();
				}
			}
			centerOfMass = massWeightedCoordinate.scalarMultiply(1.0 / massOfMolecule);
		}
		return new SimpleCoord(centerOfMass);
	}

}
