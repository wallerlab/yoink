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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;

import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.math.set.Subsets;

import com.google.common.primitives.Ints;

/**
 * this class is to get weight factor in DAS. for details please see:
 * "Toward a practical method for adaptive QM/MM simulations." Journal of
 * Chemical Theory and Computation 5.9 (2009): 2212-2221.
 * 
 * @author Min Zheng
 *
 */
@Service("dasWeightFactors")
public class DASWeightFactors implements Smoothner {

	/**
	 * use smooth factors to calculate the weight factors.
	 * @param job -parameters and results in job
	 */
	public void smooth(Job<JAXBElement> job) {
		// initialize
		Set<Molecule> bufferMolecules = job.getRegions()
				.get(Region.Name.BUFFER).getMolecules();
		List<Integer> bufferIndices = new ArrayList<Integer>();
		for (Molecule molecule : bufferMolecules) {
			bufferIndices.add(molecule.getIndex());
		}
		List<Double> lambda = (List<Double>) job.getProperties().get(
				"smoothfactors");
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();
		molecularIndicesAndWeightFactor = Collections
				.synchronizedMap(molecularIndicesAndWeightFactor);
		// loop over all QM/MM sets in buffer region
		loopOverAllQMMMConfigurationsInBuffer(lambda, bufferIndices,
				molecularIndicesAndWeightFactor);
		job.getProperties().put("weightfactors",
				molecularIndicesAndWeightFactor);
	}

	private void loopOverAllQMMMConfigurationsInBuffer(List<Double> lambda,
			List<Integer> bufferIndices,
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor) {
		Subsets.split(Ints.toArray(bufferIndices))
				.parallelStream()
				.forEach(
						qmSet -> {
							Set<Integer> mmSet = new HashSet<Integer>(
									bufferIndices);
							mmSet.removeAll(qmSet);
							findTheRightConfigurations(lambda, bufferIndices,
									molecularIndicesAndWeightFactor, qmSet,
									mmSet);
						});
	}

	private void findTheRightConfigurations(List<Double> lambda,
			List<Integer> bufferIndices,
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor,
			List<Integer> qmSet, Set<Integer> mmSet) {
		double lambdaQMMax = 0;
		double lambdaMMMin = 1;
		// find the maximum value of lambda in QM set
		lambdaQMMax = getQMLambdaMaximum(lambda, bufferIndices, qmSet,
				lambdaQMMax);
		// find the minimum value of lambda in MM set
		lambdaMMMin = getMMLambdaMinimum(lambda, bufferIndices, mmSet,
				lambdaMMMin);
		// calculate weighted function sigma
		getWeightFactor(molecularIndicesAndWeightFactor, qmSet, lambdaQMMax,
				lambdaMMMin);

	}

	private void getWeightFactor(
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor,
			List<Integer> qmSet, double lambdaQMMax, double lambdaMMMin) {
		if (lambdaQMMax < lambdaMMMin) {
			double sigma = lambdaMMMin - lambdaQMMax;
			molecularIndicesAndWeightFactor.put(qmSet, sigma);
		}
	}

	private double getMMLambdaMinimum(List<Double> lambda,
			List<Integer> bufferIndices, Set<Integer> mmSet, double lambdaMMMin) {
		for (Integer molecularIndex : mmSet) {
			int index = bufferIndices.indexOf(molecularIndex);
			if (lambdaMMMin > lambda.get(index)) {
				lambdaMMMin = lambda.get(index);
			}
		}
		return lambdaMMMin;
	}

	private double getQMLambdaMaximum(List<Double> lambda,
			List<Integer> bufferIndices, List<Integer> qmSet, double lambdaQMMax) {
		for (Integer molecularIndex : qmSet) {
			int index = bufferIndices.indexOf(molecularIndex);
			if (lambdaQMMax < lambda.get(index)) {
				lambdaQMMax = lambda.get(index);
			}
		}
		return lambdaQMMax;
	}

}
