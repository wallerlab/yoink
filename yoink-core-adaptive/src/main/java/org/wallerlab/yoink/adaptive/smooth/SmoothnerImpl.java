package org.wallerlab.yoink.adaptive.smooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Computer;

import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;

/**
 * this class is for smoothing the buffer region.
 * 
 * @author Min Zheng
 *
 */
public abstract class SmoothnerImpl implements Smoothner {

	@Resource
	protected Computer<Coord, Set<Molecule>> centerOfMassComputer;

	private SmoothFunction smoothFunction;

	/**
	 * SmoothnerImpl constructor with specified smooth function.
	 * 
	 * @param smoothFunction -the specified smooth function for a new object of SmoothnerImpl.
	 */
	public SmoothnerImpl(SmoothFunction smoothFunction) {
		this.smoothFunction = smoothFunction;
	}

	/**
	 * calculate smooth factors for molecules in buffer region
	 */
	public void smooth(Job job) {
		List<Double> smoothFactors = loopOverAllBufferMolecules(job);
		job.getProperties().put("smoothfactors", smoothFactors);
	}

	protected List<Double> loopOverAllBufferMolecules(Job job) {
		Map<Region.Name, Region> regions = job.getRegions();
		Map<JobParameter, Object> parameters = job.getParameters();
		Set<Molecule> qmCoreMolecules = regions.get(Region.Name.QM_CORE)
				.getMolecules();
		Set<Molecule> bufferMolecules = regions.get(Region.Name.BUFFER)
				.getMolecules();
		List<Double> smoothFactors = new ArrayList<Double>();
		List<Double> smoothParameters = new ArrayList<Double>();
		for (Molecule molecule : bufferMolecules) {
			
			getOriginalMinAndMax(smoothParameters, parameters);
			getProperSmoothParameters(qmCoreMolecules, smoothParameters,
					molecule);
			double min = smoothParameters.get(0);
			double max = smoothParameters.get(1);
			double currentValue = smoothParameters.get(2);
			double smoothFactor = smoothFunction.evaluate(currentValue, min,
					max);
			smoothFactors.add(smoothFactor);
		}
		return smoothFactors;
	}

	protected abstract void getProperSmoothParameters(
			Set<Molecule> qmCoreMolecules, List<Double> smoothParameters,
			Molecule molecule);

	protected abstract void getOriginalMinAndMax(List<Double> smoothParameters,
			Map<JobParameter, Object> parameters);

}
