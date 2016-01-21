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
package org.wallerlab.yoink.adaptive.smooth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.math.map.MapSorter;
import org.wallerlab.yoink.math.set.Subsets;

import com.google.common.primitives.Ints;

/**
 * this class is to get weight factor in SCMP. for details please see:
 * "Size-Consistent Multipartitioning QM/MM: A Stable and Efficient Adaptive
 * QM/MM Method"
 * 
 * @author Min Zheng
 *
 */
@Service("scmpWeightFactors")
public class SCMPWeightFactors implements Smoothner {

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	@Resource
	@Qualifier("buloSmoothFunction")
	private SmoothFunction buloSmoothFunction;// for QM

	@Resource
	@Qualifier("scmpSmoothFunction")
	private SmoothFunction scmpSmoothFunction;// for MM

	private Map<List<Integer>, Double> sigmaIndexMap;

	/**
	 * use smooth factors to calculate the weight factors.
	 * 
	 * @param job
	 *            -parameters and results in job
	 */
	public void smooth(Job<JAXBElement> job) {
		// initialize
		getWeightForAllPartitioningConfiguration(job);
		int partitionNumber = (int) job.getParameters().get(
				JobParameter.NUMBER_PARTITION);
		double sigmas[] = new double[partitionNumber];
		List<List<Integer>> qmSets = new ArrayList<List<Integer>>();
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = getWeightForSelectedPartitioningConfiguration(
				partitionNumber, sigmas, qmSets);
		job.getProperties().put("weightfactors",
				molecularIndicesAndWeightFactor);
	}

	private void getWeightForAllPartitioningConfiguration(Job<JAXBElement> job) {
		Map<JobParameter, Object> parameters = job.getParameters();
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegions()
				.get(Region.Name.BUFFER).getMolecularMap();
		List<Molecule> bufferMolecules = new ArrayList<Molecule>(
				bufferMoleculeMap.keySet());
		List<Integer> bufferIndices = new ArrayList<Integer>(
				bufferMoleculeMap.values());
		double s_qm_out = (double) parameters
				.get(JobParameter.DISTANCE_S_QM_OUT);
		double t_qm_out = (double) parameters
				.get(JobParameter.DISTANCE_T_QM_OUT);
		double s_qm_in = (double) parameters.get(JobParameter.DISTANCE_S_QM_IN);
		double t_qm_in = (double) parameters.get(JobParameter.DISTANCE_T_QM_IN);
		double s_mm_out = (double) parameters
				.get(JobParameter.DISTANCE_S_MM_OUT);
		double t_mm_out = (double) parameters
				.get(JobParameter.DISTANCE_T_MM_OUT);
		double s_mm_in = (double) parameters.get(JobParameter.DISTANCE_S_MM_IN);
		double t_mm_in = (double) parameters.get(JobParameter.DISTANCE_T_MM_IN);
		sigmaIndexMap = new HashMap<List<Integer>, Double>();
		sigmaIndexMap = Collections.synchronizedMap(sigmaIndexMap);
		int qmNumber = (int) parameters.get(JobParameter.NUMBER_QM);
		int number_qmInBuffer = qmNumber - qmNumber * 2 / 3;
		Map<Region.Name, Region> regions = job.getRegions();
		Coord centerCoord = regions.get(Region.Name.QM_CORE).getCenterOfMass();
		calculateWeightForEachConfiguration(bufferMolecules, bufferIndices,
				s_qm_out, t_qm_out, s_qm_in, t_qm_in, s_mm_out, t_mm_out,
				s_mm_in, t_mm_in, number_qmInBuffer, centerCoord);
	}

	private void calculateWeightForEachConfiguration(
			List<Molecule> bufferMolecules, List<Integer> bufferIndices,
			double s_qm_out, double t_qm_out, double s_qm_in, double t_qm_in,
			double s_mm_out, double t_mm_out, double s_mm_in, double t_mm_in,
			int number_qmInBuffer, Coord centerCoord) {
		Subsets.split(Ints.toArray(bufferIndices), number_qmInBuffer)
				.parallelStream()
				.forEach(
						qmSet -> {
							Set<Integer> mmSet = new HashSet<Integer>(
									bufferIndices);
							mmSet.removeAll(qmSet);

							double sigma = calculateSigma(centerCoord,
									bufferMolecules, bufferIndices, s_qm_out,
									t_qm_out, s_qm_in, t_qm_in, s_mm_out,
									t_mm_out, s_mm_in, t_mm_in, qmSet, mmSet);
							sigmaIndexMap.put(qmSet, sigma);

						});
	}

	private Map<List<Integer>, Double> getWeightForSelectedPartitioningConfiguration(
			int partitionNumber, double[] sigmas, List<List<Integer>> qmSets) {
		Map<List<Integer>, Double> sortedSigmaIndexMap = MapSorter
				.sortByValue(sigmaIndexMap);
		List<List<Integer>> sortedIndices = new ArrayList<List<Integer>>(
				sortedSigmaIndexMap.keySet());
		List<Double> sortedSigmas = new ArrayList<Double>(
				sortedSigmaIndexMap.values());
		double sum_sigmas = 0;
		List<Double> subSigmas = sortedSigmas.subList(sortedSigmas.size()
				- partitionNumber, sortedSigmas.size());
		List<List<Integer>> subIndices = sortedIndices.subList(
				sortedSigmas.size() - partitionNumber, sortedSigmas.size());
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

	private double calculateSigma(Coord centerCoord,
			List<Molecule> bufferMolecules, List<Integer> bufferIndices,
			double s_qm_out, double t_qm_out, double s_qm_in, double t_qm_in,
			double s_mm_out, double t_mm_out, double s_mm_in, double t_mm_in,
			ArrayList<Integer> qmSet, Set<Integer> mmSet) {
		double fadeOutQM = fadeQM(centerCoord, bufferMolecules, bufferIndices,
				s_qm_out, t_qm_out, qmSet);
		double fadeInQM = 1 - fadeQM(centerCoord, bufferMolecules,
				bufferIndices, s_qm_in, t_qm_in, qmSet);
		double fadeOutMM = fadeMM(centerCoord, bufferMolecules, bufferIndices,
				s_mm_out, t_mm_out, mmSet);
		double fadeInMM = 1 - fadeMM(centerCoord, bufferMolecules,
				bufferIndices, s_mm_in, t_mm_in, mmSet);
		double sigma = fadeOutQM * fadeOutMM * fadeInQM * fadeInMM;
		return sigma;
	}

	private double fadeQM(Coord centerCoord, List<Molecule> bufferMolecules,
			List<Integer> bufferIndices, double s_qm_out, double t_qm_out,
			List<Integer> qmSet) {
		double fadeQM = 1.0;
		for (Integer molecularIndex : qmSet) {
			int index = bufferIndices.indexOf(molecularIndex);
			Molecule molecule = bufferMolecules.get(index);
			double currentDistance = closestDistanceToMoleculeCalculator
					.calculate(centerCoord, molecule);
			double lamdba = scmpSmoothFunction.evaluate(currentDistance,
					s_qm_out, t_qm_out);
			fadeQM *= lamdba;
		}
		return fadeQM;
	}

	private double fadeMM(Coord centerCoord, List<Molecule> bufferMolecules,
			List<Integer> bufferIndices, double s_qm_out, double t_qm_out,
			Set<Integer> mmSet) {
		double fadeMM = 1.0;
		for (Integer molecularIndex : mmSet) {
			int index = bufferIndices.indexOf(molecularIndex);
			Molecule molecule = bufferMolecules.get(index);
			double currentDistance = closestDistanceToMoleculeCalculator
					.calculate(centerCoord, molecule);
			double lamdba = buloSmoothFunction.evaluate(currentDistance,
					s_qm_out, t_qm_out);
			fadeMM *= lamdba;
		}
		return fadeMM;
	}

}
