package org.wallerlab.yoink.adaptive.services;

import org.wallerlab.yoink.adaptive.domain.AdaptiveMolecule;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.molecule.service.Computer;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME.*;

import java.util.*;
import javax.xml.bind.JAXBElement;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;

@Service
public class SmoothFactors {

	@Resource private Calculator<Double, Coord, Molecule> shortestDistance;
	@Resource private Computer<Coord, Set<Molecule>> centerOfMassComputer;

	@Resource private Calculator<Double, Coord, Set<Molecule>> densityCalculator;

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

		Coord centerOfMass = centerOfMassComputer.calculate(job.getRegions().get(QM_CORE).getMolecules());
		return job.getRegions()
				  .get(BUFFER)
				  .getMolecules()
				  .stream()
				  .map(molecule -> {
						double current = shortestDistance.calculate(centerOfMass, molecule);
						return new AdaptiveMolecule( molecule, smoothFunction.evaluate(current, min, max));
			       })
			 	  .collect(toList());
		//Instead of returning lambdas, store them on the molecules.
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
							double current = Math.abs(Math.log10(densityCalculator.calculate(com, qmCoreMolecules)));
							return new AdaptiveMolecule(molecule, smoothFunction.evaluate(current, densityMin, densityMax));
				  })
				 .collect(toList());
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

		List<AdaptiveMolecule> compute(Job<JAXBElement> job , SmoothFunction smoothFunction);

		enum NAME{
			DISTANCE,
			DENSITY,
			DISTANCE_OR_DENSITY;
		}
	}

}
