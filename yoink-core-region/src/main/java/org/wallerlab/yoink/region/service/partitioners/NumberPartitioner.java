package org.wallerlab.yoink.region.service.partitioners;

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.molecule.service.DistanceCalculator;

import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import static java.util.stream.Collectors.toList;

/**
 * this class is for parameter based adaptive qm/mm partitioning
 * 
 *    -Distance
 *    -Number
 *    -Size
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
	public Map<Region.Name,Set<MolecularSystem.Molecule>> partition(Job job){

		Map<Region.Name,Set<MolecularSystem.Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		Set<MolecularSystem.Molecule> bufferMolecules = new HashSet<>();
		Set<MolecularSystem.Molecule> qmAdaptiveMolecules = new HashSet<>();

		int qmNumber = Integer.parseInt(job.getParameter(NUMBER_QM).toString());
		int bufferNumber = qmNumber + Integer.parseInt(job.getParameter(NUMBER_BUFFER).toString());

		Coord centerOfMass = distanceCalculator.centerOfMass(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		Set<MolecularSystem.Molecule> moleculesInNonQmCore = 
		      new HashSet<MolecularSystem.Molecule>(job.getMolecularSystem().getMolecules());
		      
		moleculesInNonQmCore.removeAll(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		List<MolecularSystem.Molecule> sortedMolecules =
		            moleculesInNonQmCore.stream()
						.sorted((molecule1, molecule2) -> {
							Double distance1 = distanceCalculator.closest(centerOfMass, molecule1);
							Double distance2 = distanceCalculator.closest(centerOfMass, molecule2);
							return distance1.compareTo(distance2);
						})
						.limit(bufferNumber)
						.collect(toList());

		if(job.getParameters().get(PARTITIONER).equals(NUMBER)){
				qmAdaptiveMolecules = new HashSet<MolecularSystem.Molecule>(sortedMolecules.subList(0, qmNumber));
				bufferMolecules = new HashSet<MolecularSystem.Molecule>(sortedMolecules.subList(qmNumber, bufferNumber));
		}
		else if (job.getParameter(PARTITIONER).equals(SIZE)){
			int    qmNumberSize 	 =  Integer.parseInt(  job.getParameter(NUMBER_QM).toString()) * 2 / 3;
			double distance_s_qm_in  =  Double.parseDouble(job.getParameter(DISTANCE_S_QM_IN).toString());
			double distance_t_qm_out =  Double.parseDouble(job.getParameter(DISTANCE_T_QM_OUT).toString());

			//find first molecule that is outside of limit.
		  MolecularSystem.Molecule moleculeAtT_Qm_Out =
		  
		     sortedMolecules.stream()
		                    .filter(molecule ->	
		                               distanceCalculator.closest(centerOfMass, molecule) > distance_t_qm_out)
				    .findFirst()
				    .get();
			qmAdaptiveMolecules = new HashSet<>(sortedMolecules.subList(0, qmNumberSize));
			bufferMolecules = new HashSet<>(sortedMolecules.subList(qmNumber,
								    	  	  sortedMolecules.indexOf(moleculeAtT_Qm_Out) + 1));
		}
		qmAdaptiveAndBuffer.put(QM_ADAPTIVE,qmAdaptiveMolecules);
		qmAdaptiveAndBuffer.put(BUFFER,bufferMolecules);
		return qmAdaptiveAndBuffer;
	}

}
