package org.wallerlab.yoink.adaptive.smooth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;

/**
 * this class is to do distance based smoothing.
 * 
 * @author Min Zheng
 *
 */
public class DistanceSmoothner extends SmoothnerImpl {

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	/**
	 * the constructor of DistanceSmoothner with specified smooth function. in
	 * the smooth function the variable will be distance
	 * 
	 * @param smoothFunction
	 *            -the specified function for smoothing, and distance value is
	 *            the variable
	 */
	public DistanceSmoothner(SmoothFunction smoothFunction) {
		super(smoothFunction);
	}

	protected void getProperSmoothParameters(Set<Molecule> qmCoreMolecules,
			List<Double> smoothParameters, Molecule molecule) {
		Coord centerCoord = centerOfMassComputer.calculate(qmCoreMolecules);
		double currentDistance = closestDistanceToMoleculeCalculator.calculate(
				centerCoord, molecule);
		smoothParameters.add(2, currentDistance);
	}

	protected void getOriginalMinAndMax(List<Double> smoothParameters,
			Map<JobParameter, Object> parameters) {
		smoothParameters.add(0,
				(double) parameters.get(JobParameter.DISTANCE_QM));
		smoothParameters.add(1,
				(double) parameters.get(JobParameter.DISTANCE_BUFFER)
						+ (double) parameters.get(JobParameter.DISTANCE_QM));
	}

}
