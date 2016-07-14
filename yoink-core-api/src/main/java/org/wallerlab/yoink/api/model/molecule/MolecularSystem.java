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

import java.util.List;
import java.util.Set;

/**
 * this interface is for domain model MolecularSystem
 * 
 * @author Min Zheng
 *
 */
public interface MolecularSystem {

	/**
	 * get all the atoms in the molecule system
	 * 
	 * @return a list of atoms, -
	 *         {@link Atom}
	 */
	Set<Atom> getAtoms();

	/**
	 * get all molecules in the molecule system
	 * 
	 * @return a list of molecules, -
	 *         {@link Molecule}
	 */
	 Set<Molecule> getMolecules();

	/**
	 * get all molecules in the molecule system
	 * with a string query
	 *
	 * @param String the id of the molecule that you
	 *               are interested in.
	 * @return a list of molecules, -
	 *         {@link Molecule}
	 */
	Set<Molecule> getMolecules(String string);

}
