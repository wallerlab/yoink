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
package org.wallerlab.yoink.molecule.service.translator;

import org.cml_v3.generated.Atom;
import org.cml_v3.generated.Molecule;
import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.molecule.domain.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecule.Translator;
import org.wallerlab.yoink.api.service.molecule.Converter;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

import org.cml_v3.generated.AtomArray;
import org.cml_v3.generated.Cml;
import org.cml_v3.generated.MoleculeList;

import java.util.*;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.molecule.service.IDistanceCalculator;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
/**
 * This class is to translate JAXB Cml to MolecularSystem model.
 * 
 * @author Min Zheng
 *
 */
@Service
public class MolecularSystemTranslator implements Translator<MolecularSystem, JAXBElement<Cml>> {

	// This is the raw JAXB data.
	private JAXBElement<Cml> cml;

	// CML jaxb type
	private Cml cmlMolecularSystem;

	// This is our final domain model that we want from the CML.
	private MolecularSystem molecularSystem;

	// This list contains all of the molecules from the CML.
	private Set<MolecularSystem.Molecule> molecules;

	//@Value("${yoink.job.unitconvertertype}")
	private Converter.UnitConverterType unitConverterType = Converter.UnitConverterType.AngstromToBohr;

	private int moleculeIndexCounter;

	private int atomIndexCounter;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	private IDistanceCalculator distanceCalculator;

	/**
	 * translate JAXBElement Cml to MolecularSystem
	 * 
	 * @param cml
	 *            -JAXBElement Cml {@link Cml} contains all
	 *            the information about moleuclar system.
	 * @return molecularSystem
	 *         {@link MolecularSystem }
	 */
	@Override
	public MolecularSystem translate(JAXBElement<Cml> cml) {
		init(cml);
		findMoleculeListInCml(molecules, cmlMolecularSystem);
		return new SimpleMolecularSystem( molecules);
	}

	// This class initializes the instance variables
	private void init(JAXBElement<Cml> cml) {
		moleculeIndexCounter = 0;
		atomIndexCounter = 0;
		this.cml = cml;
		this.molecules = new HashSet<>();
		this.cmlMolecularSystem = cml.getValue();
	}

	// loop over elements in a cml to find the molcule list??
	private void findMoleculeListInCml(Set<MolecularSystem.Molecule> molecules, Cml cmlMolecularSystem) {
		for (Object elementMoleculeList : cmlMolecularSystem.getAnyCmlOrAnyOrAny()) {
			checkIfMoleculeList(elementMoleculeList);
		}
	}

	private void checkIfMoleculeList(Object elementMoleculeList) {
		@SuppressWarnings("unchecked")
		JAXBElement<MoleculeList> element = (JAXBElement<MoleculeList>) elementMoleculeList;
		if (element.getDeclaredType() == MoleculeList.class) {
			MoleculeList moleculeList = element.getValue();
			loopOverElementsInCmlMoleculeList(moleculeList);
		}
	}

	private void loopOverElementsInCmlMoleculeList(MoleculeList cmlMoleculeList) {
		// loopOverCMlMoleculelist
		for (Object elementMolecule : cmlMoleculeList.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<Molecule> element = (JAXBElement<Molecule>) elementMolecule;
			// check molecule
			if (element.getDeclaredType() == Molecule.class) {
				Molecule cmlMolecule = element.getValue();
				parseMolecule(cmlMolecule);
			}
		}
	}

	private void parseMolecule(Molecule cmlMolecule) {
		moleculeIndexCounter++;
		Set<MolecularSystem.Molecule.Atom> atoms = parseAtoms(cmlMolecule);
		MolecularSystem.Molecule molecule = new SimpleMolecule(moleculeIndexCounter, (Set) atoms);
		Region.Name name = Region.Name.valueOf(cmlMolecule.getId());
		molecule.setName(name);
		Set<MolecularSystem.Molecule> moleculeSet = new HashSet<>();
		moleculeSet.add(molecule);
		Coord centerOfMass = this.distanceCalculator.centerOfMass(moleculeSet);
		molecule.setCenterOfMass(centerOfMass);
		molecules.add(molecule);
	}

	private Set<MolecularSystem.Molecule.Atom> parseAtoms(Molecule cmlMolecule) {
		Set<MolecularSystem.Molecule.Atom> atoms = new HashSet<>();
		for (Object elementAtomArray : cmlMolecule.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<AtomArray> element = (JAXBElement<AtomArray>) elementAtomArray;
			if (element.getDeclaredType() == AtomArray.class) {
				@SuppressWarnings("unchecked")
				AtomArray cmlAtomArray = ((JAXBElement<AtomArray>) elementAtomArray).getValue();
				loopOverAtoms(atoms, cmlAtomArray);
			}
		}
		return atoms;
	}

	private void loopOverAtoms(Set<MolecularSystem.Molecule.Atom> atoms, AtomArray cmlAtomArray) {
		for (Object elementAtom : cmlAtomArray.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<Atom> element = (JAXBElement<Atom>) elementAtom;
			if (element.getDeclaredType() == Atom.class) {
				Atom cmlAtom = element.getValue();
				parseAtom(atoms, cmlAtom);
			}
		}
	}

	private void parseAtom(Set<MolecularSystem.Molecule.Atom> atoms, Atom cmlAtom) {
		atomIndexCounter++;
		Element elementType = Element.valueOf(cmlAtom.getElementType());
		Vector atomCoord = parseCoord(cmlAtom);
		MolecularSystem.Molecule.Atom atom = new SimpleAtom(atomIndexCounter, elementType, atomCoord);
		atoms.add(atom);
	}

	private Vector parseCoord(Atom cmlAtom) {
		@SuppressWarnings("rawtypes")
		Vector coordVector = myVector3D.create(new double[] { cmlAtom.getX3(), cmlAtom.getY3(), cmlAtom.getZ3() });
		// get from property file
		@SuppressWarnings("rawtypes")
		Vector bohrCoordVector = coordVector.scalarMultiply(unitConverterType.value());
		return bohrCoordVector;
	}

}
