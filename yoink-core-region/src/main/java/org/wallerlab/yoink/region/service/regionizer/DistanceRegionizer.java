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
package org.wallerlab.yoink.region.service.regionizer;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.batch.JobParameter;
import org.wallerlab.yoink.batch.api.model.molecular.Coord;
import org.wallerlab.yoink.batch.api.model.molecular.Molecule;
import org.wallerlab.yoink.batch.api.model.regionizer.Region;
import org.wallerlab.yoink.batch.api.service.Calculator;
import org.wallerlab.yoink.batch.api.service.region.Partitioner;

/**
 * This class is for setting up distance based adaptive QM/MM partitioning to
 * get the QM region and buffer region
 * 
 * @author Min Zheng
 *
 */
@Service
public class DistanceRegionizer extends ParameterRegionizer {

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;
	
	@Value("${yoink.job.functional}")
	private boolean functional=false;
	
	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
											  Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType==Partitioner.Type.DISTANCE){
			super.regionize(regions, parameters);
		}
		return regions;
	}

	/**
	 * In this method, if the distance between one atom in a non-QM core
	 * molecule and the center of mass of QM core is smaller than the
	 * distanceThreshold, then this molecule is treated by QM method. if the
	 * distance is value between distanceThreshold and buffer threshold, then
	 * the corresponding molecule will be in buffer region
	 */
	protected void checkEveryNonQMCoreMolecule(Region qmAdaptiveRegion,
											   Map<JobParameter, Object> parameters, Region bufferRegion,
											   Coord centerCoord, Set<Molecule> nonQMCoreMolecules) {
		double distanceThreshold = (double) parameters
				.get(JobParameter.DISTANCE_QM);
		double bufferThreshold = distanceThreshold
				+ (double) parameters.get(JobParameter.DISTANCE_BUFFER);
		if(functional==true){
			checkCriteriaFunc(qmAdaptiveRegion, bufferRegion, centerCoord,
					nonQMCoreMolecules, distanceThreshold, bufferThreshold);
		}
		else checkCriteria(qmAdaptiveRegion, bufferRegion, centerCoord,
				nonQMCoreMolecules, distanceThreshold, bufferThreshold);
	}

	private void checkCriteria(Region qmAdaptiveRegion, Region bufferRegion,
			Coord centerCoord, Set<Molecule> nonQMCoreMolecules,
			double distanceThreshold, double bufferThreshold) {
		for (Molecule molecule : nonQMCoreMolecules) {
			double distance = closestDistanceToMoleculeCalculator.calculate(
					centerCoord, molecule);
			if (distance < distanceThreshold) {
				// add all the things in the end -> collect.
				qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex());
			} else if (distance >= distanceThreshold
					&& distance <= bufferThreshold) {
				bufferRegion.addMolecule(molecule, molecule.getIndex());
			}
		}
	}

	public void checkCriteriaFunc(Region qmAdaptiveRegion,Region bufferRegion,Coord core,Set<Molecule> molecules,double qmThreshold, double bufferThreshold){


		Map<Region.Name, List<Molecule>> regions=calculateStreamParallelstream( molecules,qmThreshold,  bufferThreshold,core);

		
		if(regions.containsKey(Region.Name.QM_ADAPTIVE)){
		regions.get(Region.Name.QM_ADAPTIVE).parallelStream().forEach(
				molecule -> {
			qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex());
		});
		}
		if(regions.containsKey(Region.Name.BUFFER)){
		regions.get(Region.Name.BUFFER).parallelStream().forEach(
				molecule -> {
			bufferRegion.addMolecule(molecule, molecule.getIndex());
		});
		}
		

	}
	
	
	public Map<Region.Name, List<Molecule>> calculateStreamParallelstream(Set<Molecule> molecules,double qmThreshold, double bufferThreshold,Coord core){
		
		Map<Region.Name, List<Molecule>> regions =
		molecules.parallelStream()
				 .collect(
						 Collectors.groupingBy(molecule -> {
							// double distance=0;
							double distance =closestDistanceToMoleculeCalculator.calculate(core, molecule);
							 if (distance < qmThreshold) return Region.Name.QM_ADAPTIVE;
							 	else if (distance < bufferThreshold) return Region.Name.BUFFER;
							return Region.Name.MM;

						 }));
		return regions;
	}
	
}
