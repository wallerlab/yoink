package org.wallerlab.yoink.molecule.service.calculator


import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.math.linear.CommonsVector3D
import org.wallerlab.yoink.molecule.service.DistanceCalculator
import spock.lang.Ignore
import spock.lang.Specification

class SortedDistancesCalculatorSpec extends Specification{

	@Ignore
	def "test method calculate(Coord centerCoord,Set<Molecule> molecules)"(){

		def centerCoord=Mock(Coord)
		centerCoord.getCoords()>>new CommonsVector3D(0,0,0);
		def a=Mock(MolecularSystem.Molecule.Atom)
		def ma=Mock(MolecularSystem.Molecule)
		ma.getAtoms()>>[a]
		def b=Mock(MolecularSystem.Molecule.Atom)
		def mb=Mock(MolecularSystem.Molecule)
		mb.getAtoms()>>[b]
		def c=Mock(MolecularSystem.Molecule.Atom)
		def mc=Mock(MolecularSystem.Molecule)
		mc.getAtoms()>>[c]
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(centerCoord,a)>> 2.0d
		distanceCalculator.calculate(centerCoord,b)>> 3.0d
		distanceCalculator.calculate(centerCoord,c)>> 1.0d
		def molecules = new HashSet<MolecularSystem.Molecule>()
		molecules.add(ma)
		molecules.add(mb)
		molecules.add(mc)
		
		when:"set up a new SortedDistancesCalculator"
		def sdCalculator= new DistanceCalculator()
		sdCalculator.distanceCalculator=distanceCalculator
		then:"assert the return type and value"
		def sdMap= sdCalculator.calculate(centerCoord, molecules)
		sdMap instanceof Map
		def sdList=new ArrayList<MolecularSystem.Molecule>(sdMap.keySet())
		sdList==[mc, ma, mb]
	}
}
