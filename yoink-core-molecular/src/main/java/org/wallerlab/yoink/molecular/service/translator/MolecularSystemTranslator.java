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
package org.wallerlab.yoink.molecular.service.translator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecular.Translator;
import org.wallerlab.yoink.api.service.molecular.Converter.UnitConverterType;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecular.domain.SimpleAtom;
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory;
import org.wallerlab.yoink.molecular.domain.SimpleMolecularSystem;
import org.wallerlab.yoink.molecular.domain.SimpleMolecule;
import org.xml_cml.schema.AtomArray;
import org.xml_cml.schema.Cml;
import org.xml_cml.schema.MoleculeList;

/**
 * This class is to translate JAXB Cml to MolecularSystem model.
 * 
 * @author Min Zheng
 *
 */
@Service
public class MolecularSystemTranslator implements
		Translator<MolecularSystem, JAXBElement<Cml>> {

	// This is the raw JAXB data.
	private JAXBElement<Cml> cml;

	// CML jaxb type
	private Cml cmlMolecularSystem;

	// This is our final domain model that we want from the CML.
	private MolecularSystem molecularSystem;

	// This list contains all of the molecules from the CML.
	private List<Molecule> molecules;

	@Value("${yoink.job.unitconvertertype}")
	private UnitConverterType unitConverterType;

	private int moleculeIndexCounter;

	private int atomIndexCounter;

	@Resource
	private Factory<Coord, double[]> simpleCoordFactory;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Resource
	private Computer<Coord, Set<Molecule>> centerOfMassComputer;

	/**
	 * translate JAXBElement Cml to MolecularSystem
	 * 
	 * @param cml
	 *            -JAXBElement Cml {@link org.xml_cml.schema.Cml} contains all
	 *            the information about moleuclar system.
	 * @return molecularSystem
	 *         {@link org.wallerlab.yoink.api.model.molecular.MolecularSystem }
	 */
	@Override
	public MolecularSystem translate(JAXBElement<Cml> cml) {
		init(cml);
		findMoleculeListInCml(molecules, cmlMolecularSystem);
		this.molecularSystem = new SimpleMolecularSystem(molecules);
		return molecularSystem;
	}

	// This class initializes the instance variables
	private void init(JAXBElement<Cml> cml) {
		moleculeIndexCounter = 0;
		atomIndexCounter = 0;
		this.cml = cml;
		this.molecules = new ArrayList<Molecule>();
		this.cmlMolecularSystem = cml.getValue();
	}

	// loop over elements in a cml to find the molcule list??
	private void findMoleculeListInCml(List<Molecule> molecules,
			Cml cmlMolecularSystem) {
		for (Object elementMoleculeList : cmlMolecularSystem
				.getAnyCmlOrAnyOrAny()) {
			checkIfMoleculeList(elementMoleculeList);
		}
	}

	private void checkIfMoleculeList(Object elementMoleculeList) {
		@SuppressWarnings("unchecked")
		JAXBElement<MoleculeList> element = (JAXBElement<MoleculeList>) elementMoleculeList;
		if (element.getDeclaredType() == MoleculeList.class) {
			MoleculeList moleculeList = (MoleculeList) element.getValue();
			loopOverElementsInCmlMoleculeList(moleculeList);
		}
	}

	private void loopOverElementsInCmlMoleculeList(MoleculeList cmlMoleculeList) {
		// loopOverCMlMoleculelist
		for (Object elementMolecule : cmlMoleculeList.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<org.xml_cml.schema.Molecule> element = (JAXBElement<org.xml_cml.schema.Molecule>) elementMolecule;
			// check molecule
			if (element.getDeclaredType() == org.xml_cml.schema.Molecule.class) {
				org.xml_cml.schema.Molecule cmlMolecule = ((JAXBElement<org.xml_cml.schema.Molecule>) element)
						.getValue();
				parseMolecule(cmlMolecule);
			}
		}
	}

	private void parseMolecule(org.xml_cml.schema.Molecule cmlMolecule) {
		moleculeIndexCounter++;
		List<Atom> atoms = parseAtoms(cmlMolecule);
		Molecule molecule = new SimpleMolecule(moleculeIndexCounter, atoms);
		Region.Name name = Region.Name.valueOf(cmlMolecule.getId());
		molecule.setName(name);
		Set<Molecule> moleculeSet = new HashSet<>();
		moleculeSet.add(molecule);
		Coord centerOfMass = centerOfMassComputer.calculate(moleculeSet);
		molecule.setCenterOfMass(centerOfMass);
		molecules.add(molecule);
	}

	private List<Atom> parseAtoms(org.xml_cml.schema.Molecule cmlMolecule) {
		List<Atom> atoms = new ArrayList<Atom>();
		for (Object elementAtomArray : cmlMolecule.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<AtomArray> element = (JAXBElement<AtomArray>) elementAtomArray;
			if (element.getDeclaredType() == AtomArray.class) {
				@SuppressWarnings("unchecked")
				AtomArray cmlAtomArray = ((JAXBElement<AtomArray>) elementAtomArray)
						.getValue();
				loopOverAtoms(atoms, cmlAtomArray);
			}
		}
		return atoms;
	}

	private void loopOverAtoms(List<Atom> atoms, AtomArray cmlAtomArray) {
		for (Object elementAtom : cmlAtomArray.getAnyCmlOrAnyOrAny()) {
			@SuppressWarnings("unchecked")
			JAXBElement<org.xml_cml.schema.Atom> element = (JAXBElement<org.xml_cml.schema.Atom>) elementAtom;
			if (element.getDeclaredType() == org.xml_cml.schema.Atom.class) {
				org.xml_cml.schema.Atom cmlAtom = (org.xml_cml.schema.Atom) element
						.getValue();
				parseAtom(atoms, cmlAtom);
			}
		}
	}

	private void parseAtom(List<Atom> atoms, org.xml_cml.schema.Atom cmlAtom) {
		atomIndexCounter++;
		Element elementType = Element.valueOf(cmlAtom.getElementType());
		Coord atomCoord = parseCoord(cmlAtom);
		Atom atom = new SimpleAtom(atomIndexCounter, elementType, atomCoord);
		atoms.add(atom);
	}

	private Coord parseCoord(org.xml_cml.schema.Atom cmlAtom) {
		@SuppressWarnings("rawtypes")
		Vector coordVector = myVector3D.create(new double[] { cmlAtom.getX3(),
				cmlAtom.getY3(), cmlAtom.getZ3() });
		// get from property file
		@SuppressWarnings("rawtypes")
		Vector bohrCoordVector = coordVector.scalarMultiply(unitConverterType
				.value());
		Coord atomCoord = simpleCoordFactory.create();
		atomCoord.setCoords(bohrCoordVector);
		return atomCoord;
	}

}
