package org.wallerlab.yoink.adaptive.services;

import org.wallerlab.yoink.adaptive.domain.BufferMolecule;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.density.DensityCalculator;
import org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction;

import static java.util.stream.Collectors.toSet;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME.*;

import java.util.*;
import javax.xml.bind.JAXBElement;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.molecule.service.DistanceCalculator;

@Service
public class SmoothFactors {

	@Resource private DistanceCalculator distanceCalculator;

	@Resource private DensityCalculator densityCalculator;

	private Map<NAME,SmoothFactor> factors;

	public SmoothFactors() {
		this.factors = new HashMap<>();
		this.factors.put(DISTANCE, distance);
		this.factors.put(DENSITY, density);
		this.factors.put(DISTANCE_OR_DENSITY, distanceOrDensity);
	}

	public SmoothFactor use(NAME name){
		return this.factors.get(name);
	}

	SmoothFactor distance = (job, smoothFunction) -> {

		double min = (double) job.getParameters().get(DISTANCE_QM);
		double max = (double) job.getParameters().get(DISTANCE_BUFFER) + (double) job.getParameters().get(DISTANCE_QM);

		Coord centerOfMass = distanceCalculator.centerOfMass(job.getRegions().get(QM_CORE).getMolecules());
		return job.getRegions()
				  .get(BUFFER)
				  .getMolecules()
				  .stream()
				  .map(molecule -> {
						double current = distanceCalculator.closest(centerOfMass, molecule);
						return new BufferMolecule( molecule, smoothFunction.evaluate(current, min, max));
			       })
			 	  .collect(toSet());
	};

	private static final double densityMin = Math.abs(Math.log10(0.001d));
	private static final double densityMax = Math.abs(Math.log10(1.0d));

	SmoothFactor density = (job, smoothFunction)-> {
		Set qmCoreMolecules = job.getRegions().get(QM_CORE).getMolecules();
		return job.getRegions()
				  .get(BUFFER)
				  .getMolecules()
				  .stream()
				  .map(molecule -> {
							Coord  com = molecule.getCenterOfMass();
							double current = Math.abs(Math.log10(densityCalculator.electronic(com, qmCoreMolecules)));
							return new BufferMolecule( molecule, smoothFunction.evaluate(current, densityMin, densityMax));
				  })
				 .collect(toSet());
	};

	SmoothFactor distanceOrDensity = (job, smoothFunction) -> {
		if(job.getParameter(PARTITIONER).equals(DISTANCE))
			return distance.compute(job, smoothFunction);
		else if(job.getParameter(PARTITIONER).equals(DENSITY))
			return density.compute(job,smoothFunction);
		return null;
	};

	@FunctionalInterface
	public interface SmoothFactor {

		Set<BufferMolecule> compute(Job<JAXBElement> job , SmoothFunction smoothFunction);

		enum NAME{
			DISTANCE,
			DENSITY,
			DISTANCE_OR_DENSITY;
		}
	}

}
