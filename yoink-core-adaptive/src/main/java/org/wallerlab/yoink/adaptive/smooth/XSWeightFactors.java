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
 * this class is to get weight factor in XS. for details please see:
 * "ONIOM-XS: An extension of the ONIOM method for molecular simulation in condensed phase"
 * Chemical Physics Letters, Volume 355, Number 3, 2 April 2002, pp. 257-262(6).
 * 
 * @author Min Zheng
 *
 */
@Service("xsWeightFactors")
public class XSWeightFactors implements Smoothner {

	/**
	 * use smooth factors to calculate the weight factors.
	 * 
	 * @param job
	 *            -parameters and results in job
	 */
	public void smooth(Job<JAXBElement> job) {
		// initialize
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegions()
				.get(Region.Name.BUFFER).getMolecularMap();
		List<Integer> bufferIndices = new ArrayList<Integer>(
				bufferMoleculeMap.values());

		List<Double> lambda = (List<Double>) job.getProperties().get(
				"smoothfactors");
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();

		double sumLambda = 0;
		for (Double value : lambda) {
			sumLambda += value;
		}
		//Equation 1
		double qmWeight = sumLambda / lambda.size();
		double bufferWeight = 1 - qmWeight;
		molecularIndicesAndWeightFactor.put(new ArrayList<Integer>(), qmWeight);
		molecularIndicesAndWeightFactor.put(bufferIndices, bufferWeight);

		job.getProperties().put("weightfactors",
				molecularIndicesAndWeightFactor);
	}

}
