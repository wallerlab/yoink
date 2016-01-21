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
package org.wallerlab.yoink.molecular.service.calculator;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecular.domain.SimpleCoord;
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory;

/**
 * This class is to get the center of mass for a set of molecules. e.g.
 * molecules in a region.
 * 
 * @author Min Zheng
 *
 */
@Service
public class CenterOfMassComputer implements Computer<Coord, Set<Molecule>> {

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	/**
	 * calculate the center of mass of molecules.
	 * 
	 * @param molecules
	 *            - a set of Molecules
	 * @return centerCoord {@link org.wallerlab.yoink.api.model.molecular.Coord}
	 * 
	 */
	public Coord calculate(Set<Molecule> molecules) {
		double massOfMolecule = 0;
		Vector massWeightedCoordinate = myVector3D.create(0, 0, 0);
		for (Molecule molecule : molecules) {
			for (Atom atom : molecule.getAtoms()) {
				double atomMass = atom.getElementType().atomMass();
				Vector matrix = atom.getCoordinate().getCoords();
				matrix = matrix.scalarMultiply(atomMass);
				massWeightedCoordinate = matrix.add(massWeightedCoordinate);
				massOfMolecule = massOfMolecule + atomMass;
			}
		}
		Coord centerCoord = setCOM(massWeightedCoordinate, massOfMolecule);
		return centerCoord;
	}

	private Coord setCOM(Vector massWeightedCoordinate, double massOfMolecule) {
		Coord centerCoord = simpleCoordFactory.create();
		Vector centerCoordinate = massWeightedCoordinate
				.scalarMultiply(1.0 / massOfMolecule);
		centerCoord.setCoords(centerCoordinate);
		return centerCoord;
	}

}
