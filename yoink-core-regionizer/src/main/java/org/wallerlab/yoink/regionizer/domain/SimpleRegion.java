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
package org.wallerlab.yoink.regionizer.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.molecular.service.calculator.CenterOfMassComputer;

/**
 * This domain model Region contains a certain type molecules.
 * 
 * @author Min Zheng
 * 
 *
 */
public class SimpleRegion implements Region {

	private Region.Name name;

	private Integer size;

	private Map<Molecule, Integer> molecularMap = new HashMap<>();

	private Computer<Coord, Set<Molecule>> centerOfMassComputer;

	private Coord centerOfMass;

	/**
	 * default Region constructor
	 */
	public SimpleRegion() {
	}

	/**
	 * construct a new Region with the region name
	 * 
	 * @param name
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 */
	public SimpleRegion(Region.Name name) {
		this.name = name;
	}

	/**
	 * get the number of molecules in the region
	 */
	@Override
	public Integer getSize() {
		return molecularMap.size();
	}

	/**
	 * get molecules in the region
	 */
	@Override
	public Set<Molecule> getMolecules() {
		return molecularMap.keySet();
	}

	/**
	 * add one element(Molecule,Integer) in this molecularMap
	 */
	@Override
	public void addMolecule(Molecule molecule, Integer index) {

		this.molecularMap.put(molecule, index);
	}

	/**
	 * set the molecularMap in the region
	 */
	@Override
	public void setMolecularMap(Map<Molecule, Integer> molecularMap) {
		this.molecularMap = molecularMap;
	}

	/**
	 * get the molecularMap in the region
	 */
	@Override
	public Map<Molecule, Integer> getMolecularMap() {
		return this.molecularMap;
	}

	/**
	 * get the name of the region
	 */
	@Override
	public Region.Name getName() {
		return name;
	}

	/**
	 * set the name of the region
	 */
	@Override
	public void setName(Region.Name name) {
		this.name = name;
	}

	/**
	 * add a Map(Molecule, Integer) to this molecularMap
	 */
	@Override
	public void addAll(Map<Molecule, Integer> map) {
		this.molecularMap.putAll(map);
	}

	/**
	 * check if all molecules are in the region
	 */
	@Override
	public boolean containsAll(Set<Molecule> molecules) {
		return this.molecularMap.keySet().containsAll(molecules);
	}

	/**
	 * get all atoms in the region
	 */
	@Override
	public List<Atom> getAtoms() {
		List<Atom> atoms = new ArrayList<>();
		for (Molecule molecule : molecularMap.keySet()) {
			for (Atom atom : molecule.getAtoms()) {
				atoms.add(atom);
			}
		}
		return atoms;
	}

	/**
	 * get the center of mass of the region
	 */
	@Override
	public Coord getCenterOfMass() {
		this.centerOfMass = this.centerOfMassComputer
				.calculate(this.molecularMap.keySet());
		return centerOfMass;
	}

	/**
	 * change the id of molecules with the name of region
	 */
	@Override
	public void changeMolecularId() {
		if (getSize() != 0) {
			for (Molecule molecule : this.molecularMap.keySet()) {
				molecule.setName(this.name);
			}
		}
	}

	/**
	 * change the id of molecules with the given name
	 */
	@Override
	public void changeMolecularId(Region.Name name) {
		if (getSize() != 0) {
			for (Molecule molecule : this.molecularMap.keySet()) {
				molecule.setName(name);
			}
		}
	}

	/**
	 * set the value of this.centerOfMassComputer
	 */
	public void setCenterOfMassComputer(
			Computer<Coord, Set<Molecule>> centerOfMassComputer) {
		this.centerOfMassComputer = centerOfMassComputer;
	}

}
