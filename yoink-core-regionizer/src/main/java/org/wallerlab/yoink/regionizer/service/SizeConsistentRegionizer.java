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
import java.util.HashSet;
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
import org.wallerlab.yoink.math.set.SetDifference;

import com.google.common.collect.Sets;

/**
 * this class is the calculation of size consistent qm/mm partitioning. for
 * details please see, "Size-Consistent Multipartitioning QM/MM: A Stable and
 * Efficient Adaptive QM/MM Method"
 * 
 * @author Min Zheng
 *
 */
@Service
public class SizeConsistentRegionizer extends ParameterRegionizer {

	@Resource
	private Calculator<Map<Molecule, Double>, Coord, Set<Molecule>> sortedDistancesCalculator;

	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType==Partitioner.Type.SIZE){
			super.regionize(regions, parameters);
		}
		return regions;
	}

	
	/**
	 * moleculeSequence is ordered by the ascending distance between molecule
	 * and the center of mass of QM core, and take first 2/3 qmNumber of
	 * molecules from the moleculeSequence as adaptive QM molecules. those
	 * molecules between distance_s_qm_in and distance_t_qm_out are in buffer
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

		int qmNumber = (int) parameters.get(JobParameter.NUMBER_QM) * 2 / 3;
		double distance_s_qm_in = (double) parameters
				.get(JobParameter.DISTANCE_S_QM_IN);
		double distance_t_qm_out = (double) parameters
				.get(JobParameter.DISTANCE_T_QM_OUT);
		checkCriteria(qmAdaptiveRegion, bufferRegion,
				distanceAndMoleculeSequence, qmNumber, distance_s_qm_in,
				distance_t_qm_out);

	}

	private void checkCriteria(Region qmAdaptiveRegion, Region bufferRegion,
			Map<Molecule, Double> distanceAndMoleculeSequence, int qmNumber,
			double distance_s_qm_in, double distance_t_qm_out) {

		List<Molecule> moleculeSequence = new ArrayList<Molecule>(
				distanceAndMoleculeSequence.keySet());
		List<Double> distanceSequence = new ArrayList<Double>(
				distanceAndMoleculeSequence.values());

		// take the first n number of molecules as adaptive qm
		List<Molecule> adaptiveQMMolecules = moleculeSequence.subList(0,
				qmNumber);
		for (Molecule molecule : adaptiveQMMolecules) {
			qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex());
		}

		int endIndexBuffer = 0;

		for (int i = 0; i < distanceSequence.size(); i++) {

			if (distanceSequence.get(i) > distance_t_qm_out) {
				endIndexBuffer = i + 1;
			
			break;

			}
		}

		List<Molecule> bufferMolecules = moleculeSequence.subList(qmNumber,
				endIndexBuffer);

		for (Molecule molecule : bufferMolecules) {
			bufferRegion.addMolecule(molecule, molecule.getIndex());
		}

	}

}
