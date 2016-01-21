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
package org.wallerlab.yoink.regionizer.service

import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
class AdaptiveRegionizerSpec extends Specification{

	def "test constructor AdaptiveRegionizer(DensityType densityType, Partitioner partitioner)"(){
		def partitioner=Mock(Partitioner)
		def densityType=DensityType.DENSITY

		when:"use densityTyep,partitioner to construct AdaptiveRegionizer"
		def regionizer=new AdaptiveRegionizer( densityType,  partitioner)

		then:"get a new AdaptiveRegionizer, its densityType and partitioner equal the  arguments of construtor method"
		regionizer instanceof AdaptiveRegionizer
		regionizer.densityType==DensityType.DENSITY
		regionizer.partitioner==partitioner
		regionizer.getDensityType()==DensityType.DENSITY
	}



	def "test method egionize(Map<Region.Name, Region> regions,	Map<JobParameter, Object> parameters)"(){
		def densityType=DensityType.DENSITY
		def densityPartitioner=Mock(Partitioner)
		def  cubePartitioner=Mock(Partitioner)
		def partitioner=Mock(Partitioner)
		def regions=Mock(Map)
		def parameters=Mock(Map)
		parameters.get(JobParameter.PARTITIONER)>>Partitioner.Type.DORI
		densityPartitioner.partition(regions, parameters,_)>>regions
		cubePartitioner.partition(regions,parameters, _)>>Mock(List)
		partitioner.partition(regions,parameters, _)>>regions

		when:"set up a new AdaptiveRegionizer"
		def regionizer=new AdaptiveRegionizer( densityType,  partitioner)
		regionizer.densityPartitioner=densityPartitioner
		regionizer.cubePartitioner=cubePartitioner

		then:"execute method regionize and return type is Map"
		def result=regionizer.regionize(regions,parameters)
		result instanceof Map
	}
}
