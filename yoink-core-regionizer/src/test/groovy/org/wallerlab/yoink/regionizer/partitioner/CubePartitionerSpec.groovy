
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
package org.wallerlab.yoink.regionizer.partitioner


import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.cube.Assigner;
import org.wallerlab.yoink.api.service.cube.CubeBuilder;
import org.wallerlab.yoink.api.*

class CubePartitionerSpec extends Specification{


	def"test method getXyzStepSizeByDensityType(Map<JobParameter, Object> parameters, DensityType densityType)"(){
		def partitioner= new  CubePartitioner()
		def parameters=Mock(Map)

		when:"set up sedd/dori stepsize"
		double[] d1 =[0.1, 0.1, 0.1]
		double[] d2 =[0.2, 0.2, 0.2]
		parameters.get(JobParameter.SEDD_STEPSIZE)>>d1
		parameters.get(JobParameter.DORI_STEPSIZE)>>d2

		then:"get right sedd/dori stepsize"
		partitioner.getXyzStepSizeByDensityType(parameters,DensityType.DORI)==d2
		partitioner.getXyzStepSizeByDensityType(parameters,DensityType.SEDD)==d1

		when:"get the stepsize when the densityType not in source code"
		partitioner.getXyzStepSizeByDensityType(parameters,DensityType.DENSITY)
		then:"throw exception"
		Exception ex=thrown()
		ex.message=="invalid  name"
	}

	def "test partition(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters, DensityType densityType)"(){

		def parameters=Mock(Map)
		double[] d2 =[0.2, 0.2, 0.2]
		parameters.get(JobParameter.DORI_STEPSIZE)>>d2
		parameters.get(JobParameter.REGION_CUBE)>>Region.Name.ADAPTIVE_SEARCH
		def regions=Mock(Map)
		def region=Mock(Region)
		region.getMolecules()>>[
			Mock(Molecule),
			Mock(Molecule)
		]
		regions.get(_)>>region
		def gridPointAssigner=Mock(Assigner)
		def cube=Mock(Cube)
		def cubeBuilder=Mock(CubeBuilder)
		cube.getCoordinates()>>[Mock(Coord)]
		cubeBuilder.build(_,_)>>cube

		when:"set up a new  CubePartitioner, gridPointAssigner returns empty"
		def partitioner= new  CubePartitioner()
		partitioner.cubeBuilder=cubeBuilder
		gridPointAssigner.assign(_,_,_)>>new HashMap<String, Object>()
		partitioner.gridPointAssigner=gridPointAssigner
		then:"method partition is executable and returns right result"
		partitioner.partition(regions,parameters, DensityType.DORI).size()==0
	}

	def "gridPointAssigner is not empty"(){

		def parameters=Mock(Map)
		double[] d2 =[0.2, 0.2, 0.2]
		parameters.get(JobParameter.DORI_STEPSIZE)>>d2
		parameters.get(JobParameter.REGION_CUBE)>>Region.Name.ADAPTIVE_SEARCH
		def regions=Mock(Map)
		def region=Mock(Region)
		region.getMolecules()>>[
			Mock(Molecule),
			Mock(Molecule)
		]
		regions.get(_)>>region
		def gridPointAssigner=Mock(Assigner)
		def cube=Mock(Cube)
		def cubeBuilder=Mock(CubeBuilder)
		cube.getCoordinates()>>[Mock(Coord)]
		cubeBuilder.build(_,_)>>cube

		when:"set up a new  CubePartitioner, gridPointAssigner returns not empty"
		def partitioner= new  CubePartitioner()
		gridPointAssigner.assign(_,_,_)>>Mock(Map)
		partitioner.cubeBuilder=cubeBuilder
		partitioner.gridPointAssigner=gridPointAssigner
		then:"method partition is executable and returns right result"
		partitioner.partition(regions,parameters, DensityType.DORI).size()==1
	}
}
