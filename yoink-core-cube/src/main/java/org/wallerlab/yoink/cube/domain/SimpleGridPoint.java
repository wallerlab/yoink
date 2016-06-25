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
package org.wallerlab.yoink.cube.domain;

import java.util.*;

import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import static java.util.stream.Collectors.toSet;
/**
 * This domain model is to store information and properties of a grid point
 * 
 * @author Min Zheng
 *
 */
public class SimpleGridPoint<String, V> implements GridPoint<String, V> {

	private int indexInCube;

	private Coord coordinate;

	private List<Atom> nearestAtoms;

	private List<Molecule> nearestMolecules;

	private Map<String, V> properties = new HashMap<String, V>();

	@Override
	public void addProperty(String k, V v) {
		this.properties.put(k, v);
	}

	@Override
	public V getProperty(String k) {
		return this.properties.get(k);
	}

	@Override
	public Coord getCoordinate() {
		return coordinate;
	}

	@Override
	public void setCoordinate(Coord coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public Map<String, V> getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Map<String, V> properties) {
		this.properties = properties;
	}

	@Override
	public int getIndexInCube() {
		return indexInCube;
	}

	@Override
	public void setIndexInCube(int indexInCube) {
		this.indexInCube = indexInCube;
	}

	@Override
	public Set<Atom> getTwoClosestAtoms() {
		return (Set<Atom>) this.properties.get("twoClosestAtoms");
	}

	@Override
	public Set<Molecule> getTwoClosestMolecules() {
		return (Set<Molecule>) this.properties.get("twoClosestMolecules");
	}

	@Override
	public Set<Atom> getAtomsInTwoClosestMolecules() {
		Set<Molecule> molecules = (Set<Molecule>) this.properties.get("twoClosestMolecules");
		return molecules.stream()
				 		.flatMap(molecule -> molecule.getAtoms().stream())
						.collect(toSet());
	}

	public void setNearestAtoms(List<Atom> nearestAtoms) {
		this.nearestAtoms = nearestAtoms;
	}

	public List<Atom> getNearestAtoms() {
		return nearestAtoms;
	}

	public List<Molecule> getNearestMolecules() {
		return nearestMolecules;
	}

	public void setNearestMolecules(List<Molecule> nearestMolecules) {
		this.nearestMolecules = nearestMolecules;
	}


}
