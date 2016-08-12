package org.wallerlab.yoink.molecule.service.calculator

import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.math.linear.CommonsVector3D
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.service.DistanceCalculator

import spock.lang.Specification

class SortedDistancesCalculatorSpec extends Specification{

	def "test method calculate(Coord centerCoord,Set<Molecule> molecules)"(){

		def centerCoord=Mock(Coord)
		centerCoord.getCoords()>> SimpleVector3DFactory.staticCreate(0,0,0);

		def a=Mock(MolecularSystem.Molecule.Atom)
		def ma=Mock(MolecularSystem.Molecule)
		ma.getAtoms()>>[a]
		a.getCoordinate() >> SimpleVector3DFactory.staticCreate(1,1,1);

		def b=Mock(MolecularSystem.Molecule.Atom)
		def mb=Mock(MolecularSystem.Molecule)
		mb.getAtoms()>>[b]
		b.getCoordinate() >> SimpleVector3DFactory.staticCreate(2,2,2);

		def c=Mock(MolecularSystem.Molecule.Atom)
		def mc=Mock(MolecularSystem.Molecule)
		mc.getAtoms()>>[c]
		c.getCoordinate() >> SimpleVector3DFactory.staticCreate(1.5,1.5,1.5);

		def molecules = [ma,mb,mc] as Set

		when:"set up a new SortedDistancesCalculator"
		def calculator= new DistanceCalculator()
		then:"assert the return type and value"
		    def sdMap= calculator.sortByDistance(centerCoord, molecules)

		    println sdMap
		      sdMap instanceof Map
		    def sdList=new ArrayList<MolecularSystem.Molecule>(sdMap.keySet())
		   sdList==[ma, mc, mb]
	}
}
