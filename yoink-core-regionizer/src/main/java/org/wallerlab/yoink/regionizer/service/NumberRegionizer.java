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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;

/**
 * this class is the calculation of number based qm/mm partitioning. for number
 * based adaptive QM/MM partition. See, Takenaka N, Kitamura Y, Koyano Y, et al.
 * The number-adaptive multiscale QM/MM molecular dynamics simulation:
 * Application to liquid water[J]. Chemical Physics Letters, 2012, 524: 56-61.
 * 
 * @author Min Zheng
 *
 */
@Service
public class NumberRegionizer extends ParameterRegionizer {

	@Resource
	private Calculator<Map<Molecule, Double>, Coord, Set<Molecule>> sortedDistancesCalculator;

	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType==Partitioner.Type.NUMBER){
			super.regionize(regions, parameters);
		}
		return regions;
	}

	
	/**
	 * moleculeSequence is ordered by the ascending distance between molecule
	 * and the center of mass of QM core, and take first qmNumber of molecules
	 * from the moleculeSequence as adaptive QM molecules. those molecules
	 * between qm number threshold and buffer number threshold are in buffer
	 * region.
	 */

	protected void checkEveryNonQMCoreMolecule(Region qmAdaptiveRegion,
			Map<JobParameter, Object> parameters, Region bufferRegion,
			Coord centerCoord, Set<Molecule> nonQMCoreMolecules) {
		/*
		 * distanceAndMoleculeSequence which is a Map(modlecularIndex,distance),
		 * contains the order of non-QM molecules which is based on ascending
		 * distances between non-QM molecules and the center of mass of QM core.
		 */
		Map<Molecule, Double> distanceAndMoleculeSequence = sortedDistancesCalculator
				.calculate(centerCoord, nonQMCoreMolecules);
		int qmNumber = (int) parameters.get(JobParameter.NUMBER_QM);
		int bufferNumber = qmNumber
				+ (int) parameters.get(JobParameter.NUMBER_BUFFER);
		checkCriteria(qmAdaptiveRegion, bufferRegion,
				distanceAndMoleculeSequence, qmNumber, bufferNumber);
		getDistanceMinAndMAxForBufferRegion(parameters,
				distanceAndMoleculeSequence, qmNumber, bufferNumber);
	}

	private void checkCriteria(Region qmAdaptiveRegion, Region bufferRegion,
			Map<Molecule, Double> distanceAndMoleculeSequence, int qmNumber,
			int bufferNumber) {
		// only use the molecules.
		List<Molecule> moleculeSequence = new ArrayList<Molecule>(
				distanceAndMoleculeSequence.keySet());
		// take the first n number of molecules as adaptive qm
		List<Molecule> adaptiveQMMolecules = moleculeSequence.subList(0,
				qmNumber);
		for (Molecule molecule : adaptiveQMMolecules) {
			qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex());
		}
		List<Molecule> bufferMolecules = moleculeSequence.subList(qmNumber,
				bufferNumber);
		for (Molecule molecule : bufferMolecules) {
			bufferRegion.addMolecule(molecule, molecule.getIndex());
		}
	}

	private void getDistanceMinAndMAxForBufferRegion(
			Map<JobParameter, Object> parameters,
			Map<Molecule, Double> distanceAndMoleculeSequence, int qmNumber,
			int bufferNumber) {
		List<Double> distanceSequence = new ArrayList<Double>(
				distanceAndMoleculeSequence.values());
		double distanceThreshold = distanceSequence.get(qmNumber - 1);
		double bufferWidth = distanceSequence.get(bufferNumber - 1)
				- distanceThreshold;
		parameters.put(JobParameter.DISTANCE_QM,
				distanceSequence.get(qmNumber - 1));
		parameters.put(JobParameter.DISTANCE_BUFFER, bufferWidth);
	}

}
