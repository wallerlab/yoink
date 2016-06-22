package org.wallerlab.yoink.benchmark.imperative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wallerlab.yoink.benchmark.domain.Atom;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.service.PointAtomDistanceCalculator;
/**
 * @author lukas241094
 */
public class PAImperativeClosestDistanceCalculator {
	
	private PointAtomDistanceCalculator distanceCalculator = new PointAtomDistanceCalculator();
	
	public double calculateClosestDistance(Core core,Molecule molecule){
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : molecule.getAtoms()) {
			double tempdistance = distanceCalculator.calculateDistance(core,
					atom);
			distances.add(tempdistance);
		}
		double distance = Collections.min(distances);
		return distance;
	}
	
	

}
