package org.wallerlab.yoink.benchmark.functional;

import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.service.PointAtomDistanceCalculator;
/**
 * @author lukas241094
 */
public class PAFunctionalClosestDistanceCalcualtor {
	
	private PointAtomDistanceCalculator calculator = new PointAtomDistanceCalculator();

	public double calculateClosestDistance(Core core, Molecule molecule) {
		return molecule.getAtoms()
						.stream()
						.mapToDouble(atom -> calculator.calculateDistance(core, atom))
						.min()
						.getAsDouble();
	}

}
