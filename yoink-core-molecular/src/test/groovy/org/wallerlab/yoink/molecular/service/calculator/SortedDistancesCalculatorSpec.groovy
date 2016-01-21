package org.wallerlab.yoink.molecular.service.calculator

import java.util.Set;
import java.util.ArrayList;

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.math.linear.CommonsMatrix
import org.wallerlab.yoink.math.linear.CommonsVector3D

import spock.lang.Specification

class SortedDistancesCalculatorSpec extends Specification{

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
		distanceCalculator.calculate(centerCoord,a)>>(double)2
		distanceCalculator.calculate(centerCoord,b)>>(double)3
		distanceCalculator.calculate(centerCoord,c)>>(double)1
		def molecules = new HashSet<Molecule>()
		molecules.add(ma)
		molecules.add(mb)
		molecules.add(mc)
		
		when:"set up a new SortedDistancesCalculator"
		def sdCalculator= new SortedDistancesCalculator()
		sdCalculator.distanceCalculator=distanceCalculator
		then:"assert the return type and value"
		def sdMap= sdCalculator.calculate(centerCoord, molecules)
		sdMap instanceof Map
		def sdList=new ArrayList<Molecule>(sdMap.keySet())
		sdList==[mc, ma, mb]
	}
}
