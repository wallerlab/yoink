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
package org.wallerlab.yoink.adaptive.services.weights;

import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.math.map.MapSorter;
import org.wallerlab.yoink.math.set.Subsets;
import org.wallerlab.yoink.adaptive.services.SmoothFunctions;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.*;

import java.util.*;
import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * this class is to use weights factor in SCMP. for details please see:
 * "Size-Consistent Multipartitioning QM/MM: A Stable and Efficient Adaptive
 * QM/MM Method"
 * 
 * @author Min Zheng
 *
 */
@Service("scmpWeightFactors")
public class SCMPWeightFactors implements Smoothner {

	@Resource
	private Calculator<Double, Coord, Molecule> closest;

	@Autowired
	SmoothFunctions smoothFunctions;

	private Map<List<Integer>, Double> sigmaIndexMap;

	/**
	 * use smooth factors to molecular the weights factors.
	 * 
	 * @param job
	 *            -parameters and results in job
	 */
	public List<Double> compute(Job<JAXBElement> job) {
		// initialize
		getWeightForAllPartitioningConfiguration(job);

		int partitionNumber = (int) job.getParameters().get(NUMBER_PARTITION);
		double sigmas[] = new double[partitionNumber];
		List<List<Integer>> qmSets = new ArrayList<List<Integer>>();

		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = getWeightForSelectedPartitioningConfiguration(partitionNumber, sigmas, qmSets);
		job.getProperties().put("weightfactors", molecularIndicesAndWeightFactor);
		return job;
	}

	private void getWeightForAllPartitioningConfiguration(Job<JAXBElement> job) {
		Map<JobParameter, Object> parameters = job.getParameters();
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegion(BUFFER).getMolecularMap();
		List<Molecule> bufferMolecules = new ArrayList<Molecule>(bufferMoleculeMap.keySet());
		List<Integer> bufferIndices = new ArrayList<Integer>(bufferMoleculeMap.values());

		double s_qm_out = (double) parameters.get(DISTANCE_S_QM_OUT);
		double t_qm_out = (double) parameters.get(DISTANCE_T_QM_OUT);
		double s_qm_in =  (double) parameters.get(DISTANCE_S_QM_IN);
		double t_qm_in =  (double) parameters.get(DISTANCE_T_QM_IN);
		double s_mm_out = (double) parameters.get(DISTANCE_S_MM_OUT);
		double t_mm_out = (double) parameters.get(DISTANCE_T_MM_OUT);
		double s_mm_in =  (double) parameters.get(DISTANCE_S_MM_IN);
		double t_mm_in =  (double) parameters.get(DISTANCE_T_MM_IN);

		sigmaIndexMap = Collections.synchronizedMap(new HashMap<List<Integer>, Double>());

		int qmNumber = (int) parameters.get(NUMBER_QM);

		int number_qmInBuffer = qmNumber - qmNumber * 2 / 3;

		Map<Region.Name, Region> regions = job.getRegions();

		Coord centerCoord = regions.get(Region.Name.QM_CORE).getCenterOfMass();

		Subsets.split(Ints.toArray(bufferIndices), number_qmInBuffer)
				.parallelStream()
				.forEach(
						qmSet -> {
							Set<Integer> mmSet = new HashSet<Integer>(bufferIndices);
							mmSet.removeAll(qmSet);
							double fadeOutQM = fade(centerCoord, bufferMolecules, bufferIndices, s_qm_out, t_qm_out, qmSet,smoothFunctions.use(SCMP));
							double fadeInQM = 1 - fade(centerCoord, bufferMolecules, bufferIndices, s_qm_in, t_qm_in, qmSet,smoothFunctions.use(SCMP));
							double fadeOutMM = fade(centerCoord, bufferMolecules, bufferIndices, s_mm_out, t_mm_out, mmSet,smoothFunctions.use(BULO));
							double fadeInMM = 1 - fade(centerCoord, bufferMolecules, bufferIndices, s_mm_in, t_mm_in, mmSet,smoothFunctions.use(BULO));
							double sigma = fadeOutQM * fadeOutMM * fadeInQM * fadeInMM;
							sigmaIndexMap.put(qmSet, sigma);
						});
	}

	private Map<List<Integer>, Double> getWeightForSelectedPartitioningConfiguration(
			int partitionNumber, double[] sigmas, List<List<Integer>> qmSets) {

		Map<List<Integer>, Double> sortedSigmaIndexMap = MapSorter.sortByValue(sigmaIndexMap);
		List<List<Integer>> sortedIndices = new ArrayList<List<Integer>>(sortedSigmaIndexMap.keySet());
		List<Double> sortedSigmas = new ArrayList<Double>(sortedSigmaIndexMap.values());
		double sum_sigmas = 0;
		List<Double> subSigmas = sortedSigmas.subList(sortedSigmas.size() - partitionNumber, sortedSigmas.size());
		List<List<Integer>> subIndices = sortedIndices.subList(sortedSigmas.size() - partitionNumber, sortedSigmas.size());
		for (int num = 0; num < subSigmas.size(); num++) {
			double sigma = subSigmas.get(num);
			sigmas[num] = sigma;
			List<Integer> qmSet = subIndices.get(num);
			qmSets.add(qmSet);
			sum_sigmas += sigma;
		}
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();
		for (int i = 0; i < sigmas.length; i++) {
			sigmas[i] = sigmas[i] / sum_sigmas;
			molecularIndicesAndWeightFactor.put(qmSets.get(i), sigmas[i]);
		}
		return molecularIndicesAndWeightFactor;
	}

	private double fade(Coord centerCoord,
						List<Molecule> molecules,
						List<Integer> indices,
						double s_qm_out,
						double t_qm_out,
						List<Integer> set,
						SmoothFunctions.SmoothFunction smoothFunction) {
		double fade = 1.0;
		for (Integer index : set) {
			double currentDistance = closest.calculate(centerCoord, molecules.get(indices.indexOf(index)));
			double lamdba = smoothFunction.evaluate(currentDistance, s_qm_out, t_qm_out);
			fade *= lamdba;
		}
		return fade;
	}

}
