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
package org.wallerlab.yoink.cube.service


import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem

import org.wallerlab.yoink.api.service.molecule.Calculator

import spock.lang.Specification;

class VoronoiCalculatorSpec extends Specification {

	def "test method  calculate(Coord tempCoord,Set<Molecule> molecules)"(){
		def tempCoord= Mock(Coord)
		def m1=Mock(MolecularSystem.Molecule)
		def a1=Mock(MolecularSystem.Molecule.Atom)
		m1.getAtoms()>>[a1]
		def m2=Mock(MolecularSystem.Molecule)
		def a2=Mock(MolecularSystem.Molecule.Atom)
		m2.getAtoms()>>[a2]
		def m3=Mock(MolecularSystem.Molecule)
		def a3=Mock(MolecularSystem.Molecule.Atom)
		m3.getAtoms()>>[a3]
		Set<MolecularSystem.Molecule> mSet=(Set<MolecularSystem.Molecule>)[m1, m2, m3]
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(tempCoord, a1)>>(double)1
		distanceCalculator.calculate(tempCoord, a2)>>(double)2
		distanceCalculator.calculate(tempCoord, a3)>>(double)3

		when:"make a new VoronoiCalculator"
		def vp= new SimpleVoronoizer()
		vp.distanceCalculator=distanceCalculator

		then:"call method ratio and check the return value"
		def map=vp.calculate(tempCoord, mSet)
		map.size()==2
		map.get("twoClosestAtoms")==(Set<MolecularSystem.Molecule.Atom>)[a1, a2]
		map.get("twoClosestMolecules")==(Set<MolecularSystem.Molecule>)[m1, m2]
	}


	def"test method getTwoClosestNeighbours()"(){

		when:"call VoronoiCalculator.getTwoClosestNeighbours(), the size of neighbourDistances is 3"
		List<MolecularSystem.Molecule.Atom> twoNeighbours=Mock(List)
		List<Double> neighbourDistances=Mock(List)
		def atom=Mock(MolecularSystem.Molecule.Atom)
		def molecule=Mock(MolecularSystem.Molecule)
		List<MolecularSystem.Molecule> twoMolecules=Mock(List)
		neighbourDistances.size()>>3
		def vp= new SimpleVoronoizer()
		vp.getTwoClosestNeighbours( twoNeighbours,
				neighbourDistances,  atom,  2.9,
				twoMolecules,  molecule)
		then:"throw execption"
		Exception ex=thrown()
		ex.message=="Invalid number of neighbours: 3"
	}
}
