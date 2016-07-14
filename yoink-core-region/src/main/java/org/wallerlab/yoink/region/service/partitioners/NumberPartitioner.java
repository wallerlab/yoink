package org.wallerlab.yoink.region.service.partitioners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.molecule.service.calculator.DistanceCalculator;
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

import java.util.*;
import static java.util.stream.Collectors.toList;


/**
 * this class is for parameter based adaptive qm/mm partitioning
 * 			(Distance, Number and Size).
 *
 * it is to get QM_ADAPTIVE region , QM region, and the BUFFER region
 * 
 * @author Min Zheng
 *
 */
@Service
public class NumberPartitioner implements Partitioner{

	@Autowired
	DistanceCalculator distanceCalculator;

	@Override
	public Map<Region.Name,Set<Molecule>> partition(Job job){

		Map<Region.Name,Set<Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		Set<Molecule> bufferMolecules = new HashSet<>();
		Set<Molecule> qmAdaptiveMolecules = new HashSet<>();

		int qmNumber = Integer.parseInt(job.getParameter(NUMBER_QM).toString());
		int bufferNumber = qmNumber + Integer.parseInt(job.getParameter(NUMBER_BUFFER).toString());

		Coord centerOfMass = distanceCalculator.calculate(job.getMolecularSystem().getMolecules("QM_CORE"));

		List<Molecule> sortedMolecules =
				 job.getRegion(NONQM_CORE)
						.getMolecules()
						.stream()
						.sorted((molecule1, molecule2) -> {
							Double distance1 = distanceCalculator.closest(centerOfMass, molecule1);
							Double distance2 = distanceCalculator.closest(centerOfMass, molecule2);
							return distance1.compareTo(distance2);
						})
						.limit(bufferNumber)
						.collect(toList());

		if(job.getParameter(PARTITIONER).equals(NUMBER)){
				qmAdaptiveMolecules = (Set) sortedMolecules.subList(0, qmNumber);
				bufferMolecules = (Set) sortedMolecules.subList(qmNumber, bufferNumber);
		}
		else if (job.getParameter(PARTITIONER).equals(SIZE)){
			int    qmNumberSize 	 =  Integer.parseInt(  job.getParameters().get(NUMBER_QM).toString()) * 2 / 3;
			double distance_s_qm_in  =  Double.parseDouble(job.getParameters().get(DISTANCE_S_QM_IN).toString());
			double distance_t_qm_out =  Double.parseDouble(job.getParameters().get(DISTANCE_T_QM_OUT).toString());

			//find first molecule that is outside of limit.
			Molecule moleculeAtT_Qm_Out = sortedMolecules.stream()
														 .filter(molecule ->
																 distanceCalculator.closest(centerOfMass, molecule)
																		 > distance_t_qm_out)
								   				 	     .findFirst().get();

			qmAdaptiveMolecules = (Set) sortedMolecules.subList(0, qmNumber);
			bufferMolecules = (Set) sortedMolecules.subList(qmNumber, sortedMolecules.indexOf(moleculeAtT_Qm_Out) + 1);
		}
		qmAdaptiveAndBuffer.put(QM_ADAPTIVE,qmAdaptiveMolecules);
		qmAdaptiveAndBuffer.put(BUFFER,bufferMolecules);
		return qmAdaptiveAndBuffer;
	}

}