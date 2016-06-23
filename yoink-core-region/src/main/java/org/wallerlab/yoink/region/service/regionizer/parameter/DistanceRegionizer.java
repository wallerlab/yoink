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
package org.wallerlab.yoink.region.service.regionizer.parameter;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.region.Partitioner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.api.service.region.Partitioner.Type.DISTANCE;
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
	
	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions, Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters.get(PARTITIONER);
		if (partitionType==DISTANCE){
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
											   Map<JobParameter, Object> parameters,
											   Region bufferRegion,
											   Coord centerCoord, Set<Molecule> nonQMCoreMolecules) {
		double distanceThreshold = (double) parameters.get(DISTANCE_QM);
		double bufferThreshold = distanceThreshold + (double) parameters.get(DISTANCE_BUFFER);
		checkCriteria(qmAdaptiveRegion, bufferRegion, centerCoord,nonQMCoreMolecules, distanceThreshold, bufferThreshold);
	}

	public void checkCriteria(Region qmAdaptiveRegion,Region bufferRegion,Coord core,Set<Molecule> molecules,double qmThreshold, double bufferThreshold){
		Map<Region.Name, List<Molecule>> regions = groupByDistance( molecules,qmThreshold,  bufferThreshold,core);
		if(regions.containsKey(QM_ADAPTIVE)){
		 regions.get(QM_ADAPTIVE)
				 .forEach(molecule -> {
					 qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex());}
				 );
		}
		 if(regions.containsKey(BUFFER)){
		 regions.get(BUFFER)
				 .forEach(molecule -> {
					 bufferRegion.addMolecule(molecule, molecule.getIndex());
				 });
		}
	}

	public Map<Region.Name, List<Molecule>> groupByDistance(Set<Molecule> molecules, double qmThreshold, double bufferThreshold, Coord core){
		return molecules.parallelStream()
				 		.collect(groupingBy(molecule -> {
							double distance =closestDistanceToMoleculeCalculator.calculate(core, molecule);
							if (distance < qmThreshold) return QM_ADAPTIVE;
							else if (distance < bufferThreshold) return BUFFER;
							return MM;
						 }));
	}
	
}
