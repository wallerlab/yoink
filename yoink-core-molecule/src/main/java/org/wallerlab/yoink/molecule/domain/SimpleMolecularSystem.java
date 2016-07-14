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

import java.util.HashSet;
import java.util.Set;

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import static java.util.stream.Collectors.toSet;
/**
 * the domain model for molecule system.
 * 
 * @author Min Zheng
 *
 */
public class SimpleMolecularSystem implements MolecularSystem {

	private final Set<Molecule> molecules;

	public SimpleMolecularSystem(Set<Molecule> molecules) {
		this.molecules = molecules;
	}

	/**
	 * get all atoms in the molecule system.
	 */
	@Override
	public Set<Atom> getAtoms() {
		return molecules.stream()
						.flatMap(molecule ->
										molecule.getAtoms()
												.stream())
												.collect(toSet());
	}

	/**
	 * get all molecules in molecular system.
	 */
	@Override
	public Set<Molecule> getMolecules() {
		return new HashSet<>(this.molecules);
	}

	public Set<Molecule> getMolecules(String query){
		return molecules.stream()
				 		.filter(molecule ->
										molecule.getName()
												.toString()
												.equals(query))
				  		.collect(toSet());
	}

}
