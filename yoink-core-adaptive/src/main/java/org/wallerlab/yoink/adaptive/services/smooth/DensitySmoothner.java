package org.wallerlab.yoink.adaptive.services.smooth;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.wallerlab.yoink.batch.api.model.batch.JobParameter;
import org.wallerlab.yoink.batch.api.model.molecular.Coord;
import org.wallerlab.yoink.batch.api.model.molecular.Molecule;
import org.wallerlab.yoink.batch.api.service.Calculator;
import org.wallerlab.yoink.batch.api.service.adaptive.SmoothFunction;

/**
 * this class is to electronic density based smoothing.
 * 
 * @author Min Zheng
 *
 */
public class DensitySmoothner extends SmoothnerImpl {

	@Resource
	private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

	/**
	 * the constructor of DensitySmoothner with specified smooth function. in
	 * the smooth function the variable will be electronic density
	 * 
	 * @param smoothFunction
	 *            -the specified function for smoothing, and density value is
	 *            the variable
	 */
	public DensitySmoothner(SmoothFunction smoothFunction) {
		super(smoothFunction);
	}

	protected void getProperSmoothParameters(Set<Molecule> qmCoreMolecules,
			List<Double> smoothParameters, Molecule molecule) {
		Coord centerOfMass = molecule.getCenterOfMass();
		double currentDensity = densityCalculator.calculate(centerOfMass,
				qmCoreMolecules);
		smoothParameters.add(2, currentDensity);
		for (int i = 0; i < smoothParameters.size(); i++) {
			smoothParameters.set(i,
					Math.abs(Math.log10(smoothParameters.get(i))));
		}
	}

	protected void getOriginalMinAndMax(List<Double> smoothParameters,
			Map<JobParameter, Object> parameters) {
		smoothParameters.add(0,
				(double) parameters.get(JobParameter.DENSITY_QM));
		smoothParameters.add(1,
				(double) parameters.get(JobParameter.DENSITY_BUFFER));
	}

}
