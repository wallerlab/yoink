package org.wallerlab.yoink.adaptive.services.smooth;

import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static org.wallerlab.yoink.api.model.batch.JobParameter.DENSITY_BUFFER;
import static org.wallerlab.yoink.api.model.batch.JobParameter.DENSITY_QM;

/**
 * this class is to electronic density based smoothing.
 * 
 * @author Min Zheng
 *
 */
public class DensitySmoothner extends AbstractSmoothner {

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

	protected void getProperSmoothParameters(Set<Molecule> qmCoreMolecules, List<Double> smoothParameters, Molecule molecule) {
		double currentDensity = densityCalculator.calculate(molecule.getCenterOfMass(), qmCoreMolecules);
		smoothParameters.add(2, currentDensity);

		IntStream.range(0,smoothParameters.size())
				 .forEach(i -> smoothParameters.set(i, Math.abs(Math.log10(smoothParameters.get(i)))));
	}

	protected void getOriginalMinAndMax(List<Double> smoothParameters, Map<JobParameter, Object> parameters) {
		smoothParameters.add(0, (double) parameters.get(DENSITY_QM));
		smoothParameters.add(1, (double) parameters.get(DENSITY_BUFFER));
	}

}
