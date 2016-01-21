package org.wallerlab.yoink.density.service.density

import spock.lang.Specification

import org.wallerlab.yoink.density.service.density.AtomicDensityRatioCalculator
import org.wallerlab.yoink.math.linear.CommonsMatrix
import org.wallerlab.yoink.math.linear.CommonsVector3D
import org.wallerlab.yoink.math.linear.CommonsVector3D

import spock.lang.Specification

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;

class AtomicDensityRatioCalculatorSpec extends Specification {

	def"calculate()"(){
		given:
		def atom1=Mock(Atom)
		def atom2=Mock(Atom)
		def currentCoord=Mock(Coord)
		Atom[] atoms=[atom1, atom2]
		Calculator<Double, Coord, Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0

		when:"set up AtomicDensityRatioCalculator for two known atoms"
		def calculator= new AtomicDensityRatioCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert ratio value"
		double ratio=calculator.calculate(currentCoord,atoms);
		Math.abs(ratio-0.5)<=1.0E-6
	}
}
