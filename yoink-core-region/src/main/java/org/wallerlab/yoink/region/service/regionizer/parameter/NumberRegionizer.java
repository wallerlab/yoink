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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.service.region.Partitioner.Type.NUMBER;
/**
 * this class is the calculation of number based qm/mm partitioning. for number
 * based adaptive QM/MM partition. See, Takenaka N, Kitamura Y, Koyano Y, et al.
 * The number-adaptive multiscale QM/MM molecule dynamics simulation:
 * Application to liquid water[J]. Chemical Physics Letters, 2012, 524: 56-61.
 * 
 * @author Min Zheng
 *
 */
@Service
public class NumberRegionizer extends ParameterRegionizer {

	@Resource
	private Calculator<Map<Molecule, Double>, Coord, Set<Molecule>> sortedDistancesCalculator;

	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions, Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters.get(PARTITIONER);
		if (partitionType==NUMBER){
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
											   Map<JobParameter, Object> parameters,
											   Region bufferRegion,
											   Coord centerCoord,
											   Set<Molecule> nonQMCoreMolecules) {
		/*
		 * distanceAndMoleculeSequence which is a Map(modlecularIndex,distance),
		 * contains the order of non-QM molecules which is based on ascending
		 * distances between non-QM molecules and the center of mass of QM core.
		 */
		Map<Molecule, Double> distanceAndMoleculeSequence = sortedDistancesCalculator.calculate(centerCoord, nonQMCoreMolecules);
		int qmNumber = (int) parameters.get(NUMBER_QM);
		int bufferNumber = qmNumber + (int) parameters.get(NUMBER_BUFFER);
		checkCriteria(qmAdaptiveRegion, bufferRegion, distanceAndMoleculeSequence, qmNumber, bufferNumber);
		getDistanceMinAndMaxForBufferRegion(parameters, distanceAndMoleculeSequence, qmNumber, bufferNumber);
	}

	private void checkCriteria(Region qmAdaptiveRegion,
							   Region bufferRegion,
							   Map<Molecule, Double> distanceAndMoleculeSequence,
							   int qmNumber,
							   int bufferNumber) {
		// only use the molecules.
		List<Molecule> moleculeSequence = new ArrayList<Molecule>(distanceAndMoleculeSequence.keySet());

		// take the first n number of molecules as adaptive qm
		List<Molecule> adaptiveQMMolecules = moleculeSequence.subList(0, qmNumber);
		adaptiveQMMolecules.forEach(molecule -> qmAdaptiveRegion.addMolecule(molecule, molecule.getIndex()));

		// add the molecules to the buffer region
		List<Molecule> bufferMolecules = moleculeSequence.subList(qmNumber, bufferNumber);
		bufferMolecules.forEach(molecule -> bufferRegion.addMolecule(molecule, molecule.getIndex()));
	}

	private void getDistanceMinAndMaxForBufferRegion(Map<JobParameter, Object> parameters,
													 Map<Molecule, Double> distanceAndMoleculeSequence,
													 int qmNumber,
													 int bufferNumber) {
		List<Double> distanceSequence = new ArrayList<Double>(distanceAndMoleculeSequence.values());
		double distanceThreshold = distanceSequence.get(qmNumber - 1);
		double bufferWidth = distanceSequence.get(bufferNumber - 1) - distanceThreshold;
		parameters.put(DISTANCE_QM, distanceSequence.get(qmNumber - 1));
		parameters.put(DISTANCE_BUFFER, bufferWidth);
	}

}
