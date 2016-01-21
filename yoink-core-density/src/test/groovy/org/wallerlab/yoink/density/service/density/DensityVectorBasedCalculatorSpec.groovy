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
package org.wallerlab.yoink.density.service.density

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wallerlab.yoink.density.service.density.DensityCalculator
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory

import spock.lang.Specification
import spock.lang.*
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;

class DensityVectorBasedCalculatorSpec extends Specification {

	def"test method calculate(currentCoord,molecules), when density is not close zero"(){
		given:"curretn coord is quite close to molecules"
		def atom1=Mock(Atom)
		atom1.getElementType()>>Element.H
		def m1=Mock(Molecule)
		m1.getAtoms()>>[atom1]
		def atom2=Mock(Atom)
		atom2.getElementType()>>Element.C
		def m2=Mock(Molecule)
		m2.getAtoms()>>[atom2]
		def atom3=Mock(Atom)
		atom3.getElementType()>>Element.H
		def m3=Mock(Molecule)
		m3.getAtoms()>>[atom3]
		def atom4=Mock(Atom)
		atom4.getElementType()>>Element.C
		def m4=Mock(Molecule)
		m4.getAtoms()>>[atom4]
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord,_)>>(double)1.0
		def elementVectorImpl=new ElementVectorImpl();
		elementVectorImpl.myVector3D=new SimpleVector3DFactory(Vector3DType.COMMONS);

		when:"set up DensityVectorBasedCalculator"
		def calculator= new DensityVectorBasedCalculator()
		calculator.distanceCalculator=distanceCalculator
		calculator.elementVectorImpl=elementVectorImpl

		then:"assert density value"
		double density=calculator.calculate(currentCoord, ((Set<Molecule>)[m1, m2, m3, m4]))
		assert Math.abs(density-0.042481*2-0.1904507*2)<=1.0E-6
	}


	def"test method calculate(currentCoord,molecules), density is very close zero"(){
		given:"current coord is far away from molecules"
		def atom1=Mock(Atom)
		atom1.getElementType()>>Element.H
		def m1=Mock(Molecule)
		m1.getAtoms()>>[atom1]
		def atom2=Mock(Atom)
		atom2.getElementType()>>Element.C
		def m2=Mock(Molecule)
		m2.getAtoms()>>[atom2]
		def atom3=Mock(Atom)
		atom3.getElementType()>>Element.H
		def m3=Mock(Molecule)
		m3.getAtoms()>>[atom3]
		def atom4=Mock(Atom)
		atom4.getElementType()>>Element.C
		def m4=Mock(Molecule)
		m4.getAtoms()>>[atom4]
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord,_)>>(double)100.0
		def elementVectorImpl=new ElementVectorImpl();
		elementVectorImpl.myVector3D=new SimpleVector3DFactory(Vector3DType.COMMONS);

		when:"set up DensityVectorBasedCalculator"
		def calculator= new DensityVectorBasedCalculator()
		calculator.distanceCalculator=distanceCalculator
		calculator.elementVectorImpl=elementVectorImpl

		then:"assert density value"
		double density=calculator.calculate(currentCoord,((Set<Molecule>)[m1, m2, m3, m4]))
		density==1.0E-30
		assert Math.abs(density-1.0E-30)<=1.0E-40
	}


	@Ignore
	def"test density calculation speed for different methods  for a large system  "(){
		given:
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord,_)>>(double)1.0
		def elementVectorImpl=new ElementVectorImpl();
		elementVectorImpl.myVector3D=new SimpleVector3DFactory(Vector3DType.COMMONS);
		Set<Molecule> moleculesXL=new HashSet<Molecule>()
		for (int i=0;i<20000;i++){
			def atom=Mock(Atom)
			atom.getElementType()>>Element.H
			def m=Mock(Molecule)
			m.getAtoms()>>[atom]
			moleculesXL.add(m)
		}




		when:"loop over atom types using vector"
		def calculatorV= new DensityVectorBasedCalculator()
		calculatorV.distanceCalculator=distanceCalculator
		calculatorV.elementVectorImpl=elementVectorImpl

		then:
		calculatorV.calculate(currentCoord, (moleculesXL))//44940 milliseconds
		calculatorV.calculate(currentCoord, (moleculesXL))//45600
		calculatorV.calculate(currentCoord, (moleculesXL))// 53602



		when: "loop over atoms"
		def calculator= new DensityCalculator()
		def atomDensityCalculator= new AtomDensityCalculator()
		atomDensityCalculator.distanceCalculator=distanceCalculator
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"call method calculate"
		calculator.calculate(currentCoord, (moleculesXL))//51994 milliseconds
		calculator.calculate(currentCoord, (moleculesXL))//51875
		calculator.calculate(currentCoord, (moleculesXL))// 52096




		when:"loop over atoms using vector"
		def calculatorAV= new DensityCalculator()
		def atomDensityVCalculator= new AtomDensityVectorBasedCalculator()
		atomDensityVCalculator.distanceCalculator=distanceCalculator
		atomDensityVCalculator.elementVectorImpl=elementVectorImpl
		calculatorAV.atomDensityCalculator=atomDensityVCalculator

		then:"call method calculate"
		calculatorAV.calculate(currentCoord, (moleculesXL))//51499 milliseconds
		calculatorAV.calculate(currentCoord, (moleculesXL))//51946
		calculatorAV.calculate(currentCoord, (moleculesXL))//51425


	}



	@Ignore
	def"test density calculation speed for different methods  for a small system  "(){

		given:
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord,_)>>(double)1.0
		def elementVectorImpl=new ElementVectorImpl();
		elementVectorImpl.myVector3D=new SimpleVector3DFactory(Vector3DType.COMMONS);
		Set<Molecule> moleculesXL=new HashSet<Molecule>()
		for (int i=0;i<1000;i++){
			def atom=Mock(Atom)
			atom.getElementType()>>Element.H
			def m=Mock(Molecule)
			m.getAtoms()>>[atom]
			moleculesXL.add(m)
		}

		when:"loop over atom types using vector"
		def calculatorV= new DensityVectorBasedCalculator()
		calculatorV.distanceCalculator=distanceCalculator
		calculatorV.elementVectorImpl=elementVectorImpl

		then:"call method calculate"
		calculatorV.calculate(currentCoord, (moleculesXL))//110 milliseconds
		calculatorV.calculate(currentCoord, (moleculesXL))//53
		calculatorV.calculate(currentCoord, (moleculesXL))//62
		calculatorV.calculate(currentCoord, (moleculesXL))//60
		calculatorV.calculate(currentCoord, (moleculesXL))//51


		when: "loop over atoms"
		def calculator= new DensityCalculator()
		def atomDensityCalculator= new AtomDensityCalculator()
		atomDensityCalculator.distanceCalculator=distanceCalculator
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"call method calculate"
		calculator.calculate(currentCoord, (moleculesXL))//51milliseconds
		calculator.calculate(currentCoord, (moleculesXL))//50
		calculator.calculate(currentCoord, (moleculesXL))// 51
		calculator.calculate(currentCoord, (moleculesXL))//50
		calculator.calculate(currentCoord, (moleculesXL))// 50



		when:"loop over atoms using vector"
		def calculatorAV= new DensityCalculator()
		def atomDensityVCalculator= new AtomDensityVectorBasedCalculator()
		atomDensityVCalculator.distanceCalculator=distanceCalculator
		atomDensityVCalculator.elementVectorImpl=elementVectorImpl
		calculatorAV.atomDensityCalculator=atomDensityVCalculator

		then:"call method calculate"
		calculatorAV.calculate(currentCoord, (moleculesXL))//62 milliseconds
		calculatorAV.calculate(currentCoord, (moleculesXL))//49
		calculatorAV.calculate(currentCoord, (moleculesXL))//48
		calculatorAV.calculate(currentCoord, (moleculesXL))//47
		calculatorAV.calculate(currentCoord, (moleculesXL))//48

	}
}
