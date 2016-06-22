package org.wallerlab.yoink.benchmark.service;

import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.service.DistanceCalculator;

/**
 * @author lukas241094
 */
public class FunctionalDistanceCalculator extends DistanceCalculator{


	public double calculateClosestDistance(Core core, Molecule molecule) {

		return molecule.getAtoms()
						.stream()
						.mapToDouble(atom -> calculateDistance(core, atom))
						.min()
						.getAsDouble();
	}

	public double calculateClosestDistance(Molecule core, Molecule molecule) {

		return molecule.getAtoms()
				.stream()
				.flatMapToDouble(atom -> {
					return core.getAtoms().stream()
							.mapToDouble(coreAtom -> {
								return calculateDistance(atom, coreAtom);
							});
				})
				.min()
				.getAsDouble();

	}

}
