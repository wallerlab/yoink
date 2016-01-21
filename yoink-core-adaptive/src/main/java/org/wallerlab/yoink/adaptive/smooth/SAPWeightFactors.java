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

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.math.map.MapSorter;
import org.wallerlab.yoink.math.set.Subsets;

import com.google.common.primitives.Ints;

/**
 * this class is to get weight factor in SAP. for details please see: see:
 * Heyden, Andreas, Hai Lin, and Donald G. Truhlar. "Adaptive partitioning in
 * combined quantum mechanical and molecular mechanical calculations of
 * potential energy functions for multiscale simulations." The Journal of
 * Physical Chemistry B 111.9 (2007): 2231-2241.
 * 
 * @author Min Zheng
 *
 */
@Service("sapWeightFactors")
public class SAPWeightFactors implements Smoothner {

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	/**
	 * use smooth factors to calculate the weight factors.
	 * 
	 * @param job
	 *            -parameters and results in job
	 */
	public void smooth(Job<JAXBElement> job) {
		// initialize
		// sort buffer region based on radial coordiante
		List<Double> sortedSmoothFactors = new ArrayList<Double>();
		List<Integer> sortedBufferIndices = new ArrayList<Integer>();
		sortBufferRegionByDistanceToQMCore(job, sortedSmoothFactors,
				sortedBufferIndices);
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();
		calculateWeightFactors(molecularIndicesAndWeightFactor,
				sortedSmoothFactors, sortedBufferIndices);
		job.getProperties().put("weightfactors",
				molecularIndicesAndWeightFactor);
	}

	private void calculateWeightFactors(
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor,
			List<Double> sortedSmoothFactors, List<Integer> sortedBufferIndices) {
		int bufferSize = sortedBufferIndices.size();
		List<Double> weightFactors= new ArrayList<Double>();
		List<List<Integer>> activeBuffers=new ArrayList<List<Integer>>();
		for (int i = 0; i < bufferSize; i++) {
			double pi = sortedSmoothFactors.get(i);
			double x1 = 0;
			for (int j = 0; j < i; j++) {
				x1 += (1 - sortedSmoothFactors.get(j))
						/ (sortedSmoothFactors.get(j) - pi);
			}
			double x2 = (1 - pi) / pi;
			double x3 = 0;
			for (int j = i + 1; j < bufferSize; j++) {
				x3 += (1 - pi) / (pi - sortedSmoothFactors.get(j))
						* sortedSmoothFactors.get(j);
			}
			double weightFactor = Math.pow(1 + x1 + x2 + x3, -3);
			List<Integer> activeBuffer = sortedBufferIndices.subList(0, i + 1);
			activeBuffers.add(activeBuffer);
			weightFactors.add(weightFactor);
			
		}
		
		double product=1.0;
		
		for(int i=0;i<weightFactors.size();i++){
			product*=(1-weightFactors.get(i));
			double factor=weightFactors.get(i);
			for(int j=activeBuffers.get(i).size();j<weightFactors.size();j++){
				factor*=(1-weightFactors.get(j));
			}
			
			molecularIndicesAndWeightFactor.put(activeBuffers.get(i), factor);
		}
		//no buffer molecule is treated by QM
		List<Integer> activeBuffer = new ArrayList<Integer>();
		molecularIndicesAndWeightFactor.put(activeBuffer, product);
	}

	private void sortBufferRegionByDistanceToQMCore(Job<JAXBElement> job,
			List<Double> sortedSmoothFactors, List<Integer> sortedBufferIndices) {
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegions()
				.get(Region.Name.BUFFER).getMolecularMap();
		Coord centerCoord = job.getRegions().get(Region.Name.QM_CORE)
				.getCenterOfMass();
		Map<Map, Double> bufferDistanceMap = new HashMap<Map, Double>();
		List<Integer> bufferIndices = new ArrayList<Integer>(
				bufferMoleculeMap.values());
		List<Double> smoothFactors = (List<Double>) job.getProperties().get(
				"smoothfactors");
		List<Molecule> bufferMolecules = new ArrayList<Molecule>(
				bufferMoleculeMap.keySet());
		for (int count = 0; count < bufferMolecules.size(); count++) {
			Molecule molecule = bufferMolecules.get(count);
			double distance = closestDistanceToMoleculeCalculator.calculate(
					centerCoord, molecule);
			Map<Integer, Double> moleculeFactor = new HashMap<Integer, Double>();
			moleculeFactor.put(bufferIndices.get(count),
					smoothFactors.get(count));
			bufferDistanceMap.put(moleculeFactor, distance);
		}
		bufferDistanceMap = MapSorter.sortByValue(bufferDistanceMap);

		List<Map> moleculeFactorMapList = new ArrayList<Map>(
				bufferDistanceMap.keySet());

		for (Map moleculeFactorMap : moleculeFactorMapList) {
			sortedSmoothFactors.addAll(moleculeFactorMap.values());
			sortedBufferIndices.addAll(moleculeFactorMap.keySet());
		}
	}

}
