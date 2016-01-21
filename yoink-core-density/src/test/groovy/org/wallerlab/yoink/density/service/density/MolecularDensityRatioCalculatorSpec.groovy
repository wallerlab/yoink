package org.wallerlab.yoink.density.service.density


import org.wallerlab.yoink.density.service.density.MolecularDensityRatioCalculator
import org.wallerlab.yoink.math.linear.CommonsMatrix
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;

import spock.lang.Specification

class MolecularDensityRatioCalculatorSpec extends Specification {


	def"calculate()"(){
		given:
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def a1=Mock(Atom)
		def a2=Mock(Atom)
		m1.getAtoms()>>[a1]
		m2.getAtoms()>>[a2]
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, a1)>>(double)1
		atomDensityCalculator.calculate(currentCoord, a2)>>(double)2
		Molecule[] neighbours=[m1, m2]

		when:"make a  new MolecularDensityRatioCalculator"
		def calculator= new MolecularDensityRatioCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then :"assert calculated value"
		double ratio=	calculator.calculate(currentCoord,
				neighbours)
		Math.abs(ratio)<=0.5
	}
}
