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
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.service.adaptive.Smoothner
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.model.bootstrap.Job

class AdaptiveQMMMSmoothnerRouterSpec extends Specification{

	def "test method smooth(YoinkJob job)"(){
		when:"mock smoothner"
		def router=Mock(Smoothner)
		def job=Mock(Job)
		then:"method smooth is executable"
		router.smooth(job)
	}

	def "test method getSmoothers(YoinkJob job)"(){

		def router=new AdaptiveQMMMSmoothnerRouter()
		router.distanceSmoothnerBF=Mock(Smoothner)
		
		router.distanceSmoothnerDAS=Mock(Smoothner)
		
		router.dasWeightFactors=Mock(Smoothner)

		when:"smoothner is BUFFERED_FORCE"
		def job=Mock(Job)
		def parameters=Mock(Map)
		parameters.get(JobParameter.SMOOTHNER)>>Smoothner.Type.BUFFERED_FORCE
		job.getParameters()>>parameters
		then:"assert the size of smoothners in router"
		router.getSmoothers(job).size()==0
		router.getSmoothers(job) instanceof List<Smoothner>

		when:"smoothner is DISTANCE_DAS"
		def job2=Mock(Job)
		def parameters2=Mock(Map)
		parameters2.get(JobParameter.SMOOTHNER)>>Smoothner.Type.DISTANCE_DAS
		job2.getParameters()>>parameters2
		then:" the size of smoothners in router is 2"
		router.getSmoothers(job2).size()==2

		when:"smoothner is DISTANCE_XS"
		def job3=Mock(Job)
		def parameters3=Mock(Map)
		parameters3.get(JobParameter.SMOOTHNER)>>Smoothner.Type.DISTANCE_XS
		job3.getParameters()>>parameters3
		then:
		router.getSmoothers(job3).size()==2

		when:"smoothner is DISTANCE_SAP"
		def job4=Mock(Job)
		def parameters4=Mock(Map)
		parameters4.get(JobParameter.SMOOTHNER)>>Smoothner.Type.DISTANCE_SAP
		job4.getParameters()>>parameters4
		then:" the size of smoothners in router is 2"
		router.getSmoothers(job4).size()==2
	}
}
