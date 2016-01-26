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
package org.wallerlab.yoink.api.model.molecular;

import java.util.List;

/**
 * this interface is for domain model MolecularSystem
 * 
 * @author Min Zheng
 *
 */
public interface MolecularSystem {

	/**
	 * get all the atoms in the molecular system
	 * 
	 * @return a list of atoms, -
	 *         {@link org.wallerlab.yoink.api.model.molecular.Atom}
	 */
	List<Atom> getAtoms();

	/**
	 * get all molecules in the molecular system
	 * 
	 * @return a lsit of molecules, -
	 *         {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 */
	 List<Molecule> getMolecules();

}
