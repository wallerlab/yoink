package org.wallerlab.yoink.region.service.partitioners;

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.molecule.service.DistanceCalculator;

import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;
import static org.wallerlab.yoink.api.model.Job.JobParameter.DISTANCE_QM;
import static org.wallerlab.yoink.api.model.Job.JobParameter.DISTANCE_BUFFER;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * A distance partitioner is used to create two sets of molecules.
 *
 * The molecules in the QM adaptive region are closer than distance_QM
 *
 * The molecules in the buffer region are closer than distance_Buffer
 * but not closer than distanceQM.
 *
 */
@Service
public class DistancePartitioner implements Partitioner{

	@Autowired
	DistanceCalculator distanceCalculator;

	public Map<Region.Name,Set<MolecularSystem.Molecule>> partition(Job job) {

		//Fix this up with domain model
		double distanceQm = Double.parseDouble(job.getParameter(DISTANCE_QM).toString());
		double distanceBuffer = distanceQm + Double.parseDouble(job.getParameter(DISTANCE_BUFFER).toString());

		Coord centerOfMass =  distanceCalculator.centerOfMass(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		Map<Region.Name,Set<MolecularSystem.Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		Set<MolecularSystem.Molecule> bufferMolecules = new HashSet<>();
		Set<MolecularSystem.Molecule> qmAdaptiveMolecules = new HashSet<>();

		Set<MolecularSystem.Molecule> moleculesInNonQmCore = 
		      new HashSet<MolecularSystem.Molecule>(job.getMolecularSystem().getMolecules());
		      
		moleculesInNonQmCore.removeAll(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		moleculesInNonQmCore.stream()
				    .forEach(molecule -> { Double distance = distanceCalculator.closest(centerOfMass, molecule);
							   if (distance < distanceQm) qmAdaptiveMolecules.add(molecule);
							   else if (distance < distanceBuffer) bufferMolecules.add(molecule);});

		qmAdaptiveAndBuffer.put(QM_ADAPTIVE,qmAdaptiveMolecules);
		qmAdaptiveAndBuffer.put(BUFFER, bufferMolecules);
		return qmAdaptiveAndBuffer;
	}

}
