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

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Set;

/**
 * This domain model is to store information and properties of a grid point
 * 
 * @author Min Zheng
 *
 */
public class SimpleVoronoiPoint implements VoronoiPoint {

	private final Coord coordinate;

	private final Set<MolecularSystem.Molecule.Atom> nearestAtoms;

	private final Set<MolecularSystem.Molecule> nearestMolecules;

	public SimpleVoronoiPoint(final Coord coord,
							  final Set<MolecularSystem.Molecule> nearestMolecules,
							  final Set<MolecularSystem.Molecule.Atom> nearestAtoms){
		this.coordinate = coord;
		this.nearestMolecules = nearestMolecules;
		this.nearestAtoms = nearestAtoms;
	}

	public Coord getCoordinate() {
		return coordinate;
	}

	public Set<MolecularSystem.Molecule.Atom> getNearestAtoms() {
		return nearestAtoms;
	}

	public Set<MolecularSystem.Molecule> getNearestMolecules() {
		return nearestMolecules;
	}

}
