package org.wallerlab.yoink.region.service.partitioners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.molecule.service.calculator.DistanceCalculator;

import static org.wallerlab.yoink.api.model.batch.JobParameter.DISTANCE_BUFFER;
import static org.wallerlab.yoink.api.model.batch.JobParameter.DISTANCE_QM;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

import java.util.*;

/**
 *
 * A distance partitioner is used to create two sets of molecules.
 *
 * The molecules in the QM adaptive region are closer than distanceQM
 *
 * The molecules in the buffer region are closer than distanceBuffer
 * but not closer than distanceQM.
 *
 */
@Service
public class DistancePartitioner implements Partitioner{

	@Autowired
	DistanceCalculator distanceCalculator;

	public Map<Region.Name,Set<Molecule>> partition(Job job) {

		double distanceQm = (double) Double.parseDouble(job.getParameter(DISTANCE_QM).toString());
		double distanceBuffer = distanceQm + Double.parseDouble(job.getParameter(DISTANCE_BUFFER).toString());

		Coord centerOfMass =  job.getRegion(QM_CORE).getCenterOfMass();

		Map<Region.Name,Set<Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		Set<Molecule> bufferMolecules = new HashSet<>();
		Set<Molecule> qmAdaptiveMolecules = new HashSet<>();

		Set<Molecule> moleculesInNonQmCore = new HashSet<Molecule>(job.getMoleculesInRegion(SYSTEM));
		moleculesInNonQmCore.removeAll(job.getMoleculesInRegion(QM_CORE));

		moleculesInNonQmCore.stream()
				.forEach(molecule -> {
					Double distance = distanceCalculator.closest(centerOfMass, molecule);
					if (distance < distanceQm) qmAdaptiveMolecules.add(molecule);
					else if (distance < distanceBuffer) bufferMolecules.add(molecule);
				});

		qmAdaptiveAndBuffer.put(QM_ADAPTIVE,qmAdaptiveMolecules);
		qmAdaptiveAndBuffer.put(BUFFER, bufferMolecules);
		return qmAdaptiveAndBuffer;
	}

}