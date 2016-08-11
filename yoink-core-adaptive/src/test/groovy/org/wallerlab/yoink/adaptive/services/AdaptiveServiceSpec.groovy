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
package org.wallerlab.yoink.adaptive.services

import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.service.adaptive.Adaptive
import spock.lang.Ignore
import spock.lang.Specification

class AdaptiveServiceSpec extends Specification{

	@Ignore
	def "test method smooth(YoinkJob job)"(){

		when:"mock smoothner"
		def router=Mock(Adaptive)
		def job=Mock(Job)

		then:"method compute is executable"
		router.compute(job)
	}

	@Ignore
	def "test method getSmoothers(YoinkJob job)"(){

		def smoothFactors=new SmoothFactors()

		smoothFactors.distanceSmoothnerBF=Mock(Adaptive)
		
		smoothFactors.distanceSmoothnerDAS=Mock(Adaptive)
		
		smoothFactors.dasWeightFactors=Mock(Adaptive)

		when:"smoothner is BUFFERED_FORCE"

		def job=Mock(Job)
		def parameters=Mock(Map)
		parameters.get(Job.JobParameter.SMOOTHNER)>>Adaptive.Type.BUFFERED_FORCE
		job.getParameters()>>parameters
		then:"assert the size of calculators in smoothFactors"
		smoothFactors.getSmoothers(job).size()==0
		smoothFactors.getSmoothers(job) instanceof List<Adaptive>

		when:"smoothner is DISTANCE_DAS"
		def job2=Mock(Job)
		def parameters2=Mock(Map)
		parameters2.get(Job.JobParameter.SMOOTHNER)>>Adaptive.Type.DISTANCE_DAS
		job2.getParameters()>>parameters2
		then:" the size of calculators in smoothFactors is 2"
		smoothFactors.getSmoothers(job2).size()==2

		when:"smoothner is DISTANCE_XS"
		def job3=Mock(Job)
		def parameters3=Mock(Map)
		parameters3.get(Job.JobParameter.SMOOTHNER)>>Adaptive.Type.DISTANCE_XS
		job3.getParameters()>>parameters3
		then:
		smoothFactors.getSmoothers(job3).size()==2

		when:"smoothner is DISTANCE_SAP"
		def job4=Mock(Job)
		def parameters4=Mock(Map)
		parameters4.get(Job.JobParameter.SMOOTHNER)>>Adaptive.Type.DISTANCE_SAP
		job4.getParameters()>>parameters4
		then:" the size of calculators in smoothFactors is 2"
		smoothFactors.getSmoothers(job4).size()==2
	}
}
