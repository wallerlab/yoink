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
package org.wallerlab.yoink.api.model.regionizer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Computer;

/**
 * this interface is for domain model region which contains a certain type of
 * molecules
 * 
 * @author Min Zheng
 *
 */
public interface Region {

	/**
	 * the names of regions used during adaptive QM/MM partitioning
	 * 
	 * @author Min Zheng
	 *
	 */
	public enum Name {
		SYSTEM, QM_CORE_FIXED, QM_CORE, QM_ADAPTIVE, QM_CORE_ADAPTIVE, QM, MM, ADAPTIVE_SEARCH, NONQM_CORE, NONQM_CORE_ADAPTIVE_SEARCH, BUFFER, MM_NONBUFFER
	}

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
	 *         {@link org.wallerlab.yoink.api.model.molecular.Molecule}
	 */
	 Set<Molecule> getMolecules();

	/**
	 * add one molecule into molecularMap
	 * 
	 * @param molecule
	 *            - the key
	 * @param index
	 *            - the value
	 */
	void addMolecule(Molecule molecule, Integer index);

	/**
	 * set the value of moleculeMap
	 * 
	 * @param molecularMap
	 *            - Molecule as key, Integer as value
	 */
	void setMolecularMap(Map<Molecule, Integer> molecularMap);

	/**
	 * get the value of molecularMap
	 * 
	 * @return molecularMap,- {@link java.util.Map} Molecule as key, Integer as
	 *         value
	 */
	 Map<Molecule, Integer> getMolecularMap();

	/**
	 * get the name of the region
	 * 
	 * @return {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 */
	 Name getName();

	/**
	 * set the name of the region
	 * 
	 * @param name
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 */
	void setName(Name name);

	/**
	 * add a map into the region
	 * 
	 * @param map
	 *            - {@link java.util.Map} Molecule as key, Integer as value
	 */
	 void addAll(Map<Molecule, Integer> map);

	/**
	 * check if the regions contains all molecules
	 * 
	 * @param molecules
	 *            - a Set of Molecules
	 * @return boolean - if the regions contains all molecules, return true,or
	 *         return false
	 */
	boolean containsAll(Set<Molecule> molecules);

	/**
	 * Convenient method for getting atoms from a region
	 * 
	 * @return a List of atoms
	 */
	 List<Atom> getAtoms();

	/**
	 * get the center of mass of this region
	 * 
	 * @return Coord - {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 */
	 Coord getCenterOfMass();

	/**
	 * set molecular name as same as region name
	 */
	 void changeMolecularId();

	/**
	 * change molecular name as the given name
	 * 
	 * @param name
	 *            -{@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 *
	 */
	void changeMolecularId(Name name);

	/**
	 * set the value of CenterOfMassComputer
	 * 
	 * @param centerOfMassComputer
	 *            - {@link org.wallerlab.yoink.api.service.Computer}
	 *
	 */
	void setCenterOfMassComputer(
			Computer<Coord, Set<Molecule>> centerOfMassComputer);

}
