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
package org.wallerlab.yoink.regionizer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;

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
		checkCriteria(qmAdaptiveRegion, bufferRegion, centerCoord,
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

}
