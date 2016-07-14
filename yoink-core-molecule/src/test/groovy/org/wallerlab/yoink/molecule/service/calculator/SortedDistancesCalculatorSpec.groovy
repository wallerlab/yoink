package org.wallerlab.yoink.molecule.service.calculator

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.math.linear.CommonsVector3D
import spock.lang.Ignore
import spock.lang.Specification

class SortedDistancesCalculatorSpec extends Specification{

	@Ignore
	def "test method calculate(Coord centerCoord,Set<Molecule> molecules)"(){

		def centerCoord=Mock(Coord)
		centerCoord.getCoords()>>new CommonsVector3D(0,0,0);
		def a=Mock(Atom)
		def ma=Mock(Molecule)
		ma.getAtoms()>>[a]
		def b=Mock(Atom)
		def mb=Mock(Molecule)
		mb.getAtoms()>>[b]
		def c=Mock(Atom)
		def mc=Mock(Molecule)
		mc.getAtoms()>>[c]
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(centerCoord,a)>> 2.0d
		distanceCalculator.calculate(centerCoord,b)>> 3.0d
		distanceCalculator.calculate(centerCoord,c)>> 1.0d
		def molecules = new HashSet<Molecule>()
		molecules.add(ma)
		molecules.add(mb)
		molecules.add(mc)
		
		when:"set up a new SortedDistancesCalculator"
		def sdCalculator= new DistanceCalculator()
		sdCalculator.distanceCalculator=distanceCalculator
		then:"assert the return type and value"
		def sdMap= sdCalculator.calculate(centerCoord, molecules)
		sdMap instanceof Map
		def sdList=new ArrayList<Molecule>(sdMap.keySet())
		sdList==[mc, ma, mb]
	}
}
