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
package org.wallerlab.yoink.service

import org.xml_cml.schema.ObjectFactory

import spock.lang.Specification;

import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.model.bootstrap.Job
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
class PropertyWrapperSpec extends Specification{
	def "test method wrap(YoinkJob<JAXBElement> job) when no weightfactors and smoothfactors"(){
		given:"make up a job without weightfactors and smoothfactors"
		def job=Mock(Job)
		def factory=new ObjectFactory()
		def cml=factory.createCml()
		def input=factory.createCml(cml)
		job.getInput()>>input
		def parameters=Mock(Map)
		job.getParameters()>>parameters
		def properties=Mock(Map)
		properties.containsKey("weightfactors")>>(boolean)false
		properties.containsKey("smoothfactors")>>(boolean)false
		job.getProperties()>>properties
		def regions=Mock(Map)
		def region=Mock(Region)
		region.getMolecules()>>[Mock(Molecule)]
		regions.get(_)>>region
		job.getRegions()>>regions
		when:"make a new PropertyWrapper"
		def wrapper=new PropertyWrapper()
		then:"call method wrap, no error thrown"
		wrapper.wrap(job)
	}


	def "test method wrap(YoinkJob<JAXBElement> job) with weightfactors and smoothfactors"(){
		given:"make up a job"
		def job=Mock(Job)
		def factory=new ObjectFactory()
		def cml=factory.createCml()
		def input=factory.createCml(cml)
		job.getInput()>>input
		def parameters=Mock(Map)
		job.getParameters()>>parameters
		def properties=Mock(Map)
		properties.containsKey("weightfactors")>>(boolean)true
		properties.containsKey("smoothfactors")>>(boolean)true
		def wMap=new HashMap<List<Integer>, Double>()
		wMap.put([1], 1.0)
	
		properties.get("weightfactors")>>wMap
		properties.get("smoothfactors")>>Mock(List)
		job.getProperties()>>properties
		def regions=Mock(Map)
		def region=Mock(Region)
		region.getMolecules()>>[Mock(Molecule)]
		regions.get(_)>>region
		job.getRegions()>>regions

		when:"make a new PropertyWrapper"
		def wrapper=new PropertyWrapper()
		then:"call method wrap, no error thrown"
		wrapper.wrap(job)
	}
	
	
	def "test method wrap(YoinkJob<JAXBElement> job) with forces and energy"(){
		given:"make up a job"
		def job=Mock(Job)
		def factory=new ObjectFactory()
		def cml=factory.createCml()
		def input=factory.createCml(cml)
		job.getInput()>>input
		def parameters=Mock(Map)
		job.getParameters()>>parameters
		def properties=Mock(Map)
		properties.containsKey("weightfactors")>>(boolean)true
		properties.containsKey("smoothfactors")>>(boolean)true
		
		properties.containsKey("forcesAndEnergy")>>(boolean)true
		def wMap=new HashMap<List<Integer>, Double>()
		wMap.put([1], 1.0)
	
		
		def fMap=new HashMap<List<Vector>, Double>()
		def myVector3D=new SimpleVector3DFactory( Vector3DType.COMMONS)
		
				def vector=myVector3D.create((double)0,(double)0,(double)0)
		fMap.put([vector], (double)1.0)
		
		
		
		properties.get("weightfactors")>>wMap
		properties.get("smoothfactors")>>Mock(List)
		properties.get("forcesAndEnergy")>>fMap
		job.getProperties()>>properties
		def regions=Mock(Map)
		def region=Mock(Region)
		region.getMolecules()>>[Mock(Molecule)]
		regions.get(_)>>region
		job.getRegions()>>regions

		when:"make a new PropertyWrapper"
		def wrapper=new PropertyWrapper()
		then:"call method wrap, no error thrown"
		wrapper.wrap(job)
	}
}
