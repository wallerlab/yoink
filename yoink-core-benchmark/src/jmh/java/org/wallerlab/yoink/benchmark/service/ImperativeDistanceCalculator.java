package org.wallerlab.yoink.benchmark.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wallerlab.yoink.benchmark.domain.Atom;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.service.DistanceCalculator;

/**
 * @author lukas241094
 */
public class ImperativeDistanceCalculator extends DistanceCalculator{

	public double calculateClosestDistance(Core core,Molecule molecule){
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : molecule.getAtoms()) {
			double tempdistance = calculateDistance(core, atom);
			distances.add(tempdistance);
		}
		double distance = Collections.min(distances);
		return distance;
	}

	public double calculateClosestDistance(Molecule core,Molecule molecule){
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : molecule.getAtoms()) {
			for (Atom coreAtom : core.getAtoms()){
				double tempdistance = calculateDistance(coreAtom, atom);
				distances.add(tempdistance);
			}
		}
		double distance = Collections.min(distances);
		return distance;
	}

}
