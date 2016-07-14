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

import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.model.cube.VoronoiPoint;

import java.util.Set;

/**
 * This domain model is to store information and properties of a grid point
 * 
 * @author Min Zheng
 *
 */
public class SimpleVoronoiPoint implements VoronoiPoint {

	private int index;

	private final Coord coordinate;

	private final Set<Atom> nearestAtoms;

	private final Set<Molecule> nearestMolecules;

	public SimpleVoronoiPoint(final Coord coord,
							  final Set<Molecule> nearestMolecules,
							  final Set<Atom> nearestAtoms){
		this.coordinate = coord;
		this.nearestMolecules = nearestMolecules;
		this.nearestAtoms = nearestAtoms;
	}

	public int getIndex() {
		return index;
	}

	public Coord getCoordinate() {
		return coordinate;
	}

	public Set<Atom> getNearestAtoms() {
		return nearestAtoms;
	}

	public Set<Molecule> getNearestMolecules() {
		return nearestMolecules;
	}

}
