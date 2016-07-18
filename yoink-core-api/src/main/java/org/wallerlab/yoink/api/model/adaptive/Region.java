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
package org.wallerlab.yoink.api.model.adaptive;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Set;

/**
 * this interface is for domain model region which contains a certain type of
 * molecules
 * 
 * @author Min Zheng
 *
 */
public interface Region {

	/**
	 * get the number of molecules in the region
	 * 
	 * @return the number of molecules-Integer
	 */
	Integer getSize();

	/**
	 * get all molecules in the region
	 * 
	 * @return a Set of Molecules -
	 *         {@link MolecularSystem.Molecule}
	 */
	 Set<MolecularSystem.Molecule> getMolecules();

	/**
	 * get all atoms in the region
	 *
	 * @return a Set of Atoms-
	 *         {@link MolecularSystem.Molecule.Atom}
	 */
	Set<MolecularSystem.Molecule.Atom> getAtoms();

	/**
	 * get the name of the region
	 * 
	 * @return {@link Region.Name}
	 */
	 Name getName();

	/**
	 * check if the regions contains all molecules
	 * 
	 * @param molecules
	 *            - a Set of Molecules
	 * @return boolean - if the regions contains all molecules, return true,or
	 *         return false
	 */
	boolean containsAll(Set<MolecularSystem.Molecule> molecules);

	/**
	 * get the center of mass of this region
	 * 
	 * @return Coord - {@link Coord}
	 */
	 Coord getCenterOfMass();

	/**
	 * the names of regions used during adaptive QM/MM partitioning
	 *
	 * @author Min Zheng
	 *
	 */
	enum Name {
		SYSTEM,
		QM_CORE_FIXED,
		QM_CORE,
		QM_ADAPTIVE,
		QM_CORE_ADAPTIVE,
		QM,
		MM,
		ADAPTIVE_SEARCH,
		NONQM_CORE,
		NON_QM_CORE_ADAPTIVE_SEARCH,
		BUFFER,
		MM_NONBUFFER

	}

}
