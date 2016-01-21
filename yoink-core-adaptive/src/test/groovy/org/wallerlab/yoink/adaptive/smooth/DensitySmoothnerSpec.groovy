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
package org.wallerlab.yoink.adaptive.smooth

import spock.lang.Specification;

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;
import org.wallerlab.yoink.api.model.bootstrap.Job

class DensitySmoothnerSpec extends Specification{

	def "test distance smoothner"(){
		def smoothFunction=Mock(SmoothFunction)
		smoothFunction.evaluate(_,_,_)>>(double)1.0

		def job=Mock(Job)
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def  region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		def regions=Mock(Map)
		regions.get(_)>>region
		job.getRegions()>>regions
		def parameters=Mock(Map)
		parameters.get(_)>>(double)1.0
		job.getParameters()>>parameters
		def centerOfMassComputer=Mock(Computer)
		centerOfMassComputer
				.calculate(_)>>Mock(Coord)
		def  densityCalculator=Mock(Calculator)
		densityCalculator.calculate(_,_)>>(double)1.0
		def propertites=new HashMap<String,Object>()
		job.getProperties()>>propertites

		when:"set up a new  DensitySmoothner"
		def smoothner=new DensitySmoothner( smoothFunction)
		smoothner.centerOfMassComputer=centerOfMassComputer
		smoothner. densityCalculator= densityCalculator

		then:"call method smooth, assert the return value of method loopOverAllBufferMolecules() "
		smoothner.smooth(job)
		smoothner.loopOverAllBufferMolecules(job)==[1.0, 1.0]
		smoothner.loopOverAllBufferMolecules(job).size()==2
	}
}
