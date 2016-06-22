package org.wallerlab.yoink.benchmark.imperative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wallerlab.yoink.benchmark.domain.Atom;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.service.AtomAtomDistanceCalculator;


/**
 * @author lukas241094
 */
public class AAImperativeClosestDistanceCalculator {
	
	private AtomAtomDistanceCalculator distanceCalculator = new AtomAtomDistanceCalculator();
	
	public double calculateClosestDistance(Molecule core,Molecule molecule){
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : molecule.getAtoms()) {
			for (Atom coreAtom : core.getAtoms()){
				double tempdistance = distanceCalculator.calculateDistance(coreAtom, atom);
				distances.add(tempdistance);
			}
		}
		double distance = Collections.min(distances);
		return distance;
	}

}
