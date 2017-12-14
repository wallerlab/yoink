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

package org.wallerlab.yoink.graph.service

import org.wallerlab.yoink.api.model.bootstrap.Job;

import spock.lang.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wallerlab.yoink.region.partitioner.DensityPartitioner;
import org.wallerlab.yoink.graph.service.DORIGrapher;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.api.model.cube.GridPoint;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.Calculator
import org.wallerlab.yoink.api.model.graph.Graph;

class DORIGrapherSpec extends Specification{
	def "test method getInteractionList(Job job)"(){
		def job=Mock(Job)
		def regions=new HashMap<Region.Name,Region>()
		def myVector3D=new SimpleVector3DFactory( Vector3DType.COMMONS)

		def vector=myVector3D.create((double)0,(double)0,(double)0)
		def region=Mock(Region)
		def c=Mock(Coord)
		c.getCoords()>>vector
		def a=Mock(Atom)
		a.getCoordinate()>>c
		def m=Mock(Molecule)
		m.getAtoms()>>[a]
		m.getIndex()>>0
		def m1=Mock(Molecule)
		m1.getAtoms()>>[a]
		m1.getIndex()>>1
		def m2=Mock(Molecule)
		m2.getAtoms()>>[a]
		m2.getIndex()>>2
		region.getAtoms()>>[a, a, a]
		region.getCenterOfMass()>>Mock(Coord)
        region.getMolecules()>>[m, m1, m2]

		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		molecularMap.put(m1,1)
		molecularMap.put(m2,2)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.SYSTEM,region)


		def parameters=Mock(Map)
		parameters.get(JobParameter.PARTITIONER) >>Partitioner.Type.INTERACTION
		parameters.get(JobParameter.REGION_CUBE)>>Region.Name.SYSTEM
		parameters.get(JobParameter.INTERACTION_WEIGHT) >>true
		parameters.get(JobParameter.DENSITY_DORI)>> (double)1.0E-4
		parameters.get(JobParameter.DORI) >> (double)0.9
		parameters.get(JobParameter.DGRID)>>false
		
		job.getParameters()>>parameters
		job.getRegions()>>regions

		def d1= Mock(GridPoint)
		d1.getTwoClosestMolecules() >>[m, m1] 
		def d2= Mock(GridPoint)
		d2.getTwoClosestMolecules() >>[m2, m1] 
		def d3= Mock(GridPoint)
		d3.getTwoClosestMolecules() >>[m2, m1] 
		
		
		def gridPoints = new ArrayList<GridPoint>()
		gridPoints.add(d1) 
		gridPoints.add(d2)
		gridPoints.add(d3)
		def densityPropertiesCalculator =Mock(Calculator)
		def densityCalculator = Mock(Calculator)
		densityCalculator.calculate(_,_)>>(double)0.1
	
		def cubePartitioner= Mock(Partitioner)
		cubePartitioner.partition(_,_,_)>>gridPoints
		def densityOverlapRegionsIndicatorComputer= Mock(Computer)
		densityOverlapRegionsIndicatorComputer.calculate(_)>>(double)0.91
		def densityPartitioner = new DensityPartitioner()
		
		Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator=Mock(Calculator)
		closestDistanceToMoleculeCalculator.calculate(_,_)>>(double)3.0
		
		when:
		def doriInteractionList = new DORIGrapher ()
		doriInteractionList.debug=false
		doriInteractionList.closestDistanceToMoleculeCalculator=closestDistanceToMoleculeCalculator
		doriInteractionList.cubePartitioner=cubePartitioner
		doriInteractionList.densityCalculator=densityCalculator
		doriInteractionList.densityOverlapRegionsIndicatorComputer=densityOverlapRegionsIndicatorComputer
		doriInteractionList.densityPartitioner=densityPartitioner
		doriInteractionList.densityPropertiesCalculator=densityPropertiesCalculator
		doriInteractionList.simpleGraph = Mock(Graph)
		then:
		doriInteractionList.graphing(job)
	}
}

