package org.wallerlab.yoink.region.service.partitioners;

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.wallerlab.yoink.molecule.service.IDistanceCalculator;

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
	IDistanceCalculator distanceCalculator;

	@Override
	public Map<Region.Name,Set<MolecularSystem.Molecule>> partition(Job job){

		Map<Region.Name,Set<MolecularSystem.Molecule>> qmAdaptiveAndBuffer = new HashMap<>();
		Set<MolecularSystem.Molecule> bufferMolecules = new HashSet<>();
		Set<MolecularSystem.Molecule> qmAdaptiveMolecules = new HashSet<>();

		final int numberOfQm = Integer.parseInt(job.getParameter(NUMBER_QM).toString());
		final int numberOfBuffer = numberOfQm + Integer.parseInt(job.getParameter(NUMBER_BUFFER).toString());

		Coord centerOfMass = distanceCalculator.centerOfMass(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		Set<MolecularSystem.Molecule> moleculesInNonQmCore = 
		      new HashSet<MolecularSystem.Molecule>(job.getMolecularSystem().getMolecules());

		moleculesInNonQmCore.removeAll(job.getMolecularSystem().getMolecules("QM_CORE_FIXED"));

		List<MolecularSystem.Molecule> moleculesInNonQmCoreList = new ArrayList<>(moleculesInNonQmCore);

		List<MolecularSystem.Molecule> sortedMolecules =
		            moleculesInNonQmCore.stream()
						.sorted((molecule1, molecule2) -> {
							Double distance1 = distanceCalculator.closest(centerOfMass, molecule1);
							Double distance2 = distanceCalculator.closest(centerOfMass, molecule2);
							return distance1.compareTo(distance2);
						})
						.limit(numberOfBuffer)
						.collect(toList());

		if(job.getParameter(PARTITIONER).equals(NUMBER)){
            qmAdaptiveMolecules = new HashSet<MolecularSystem.Molecule>(sortedMolecules
                                                                          .subList(0, numberOfQm));
            bufferMolecules     = new HashSet<MolecularSystem.Molecule>(sortedMolecules
                                                                          .subList(numberOfQm, numberOfBuffer));

        }
		else if (job.getParameter(PARTITIONER).equals(SIZE)){
			int    qmNumberSize 	 =  Integer.parseInt(  job.getParameter(NUMBER_QM).toString()) * 2 / 3;
			double distance_s_qm_in  =  Double.parseDouble(job.getParameter(DISTANCE_S_QM_IN).toString());
			double distance_t_qm_out =  Double.parseDouble(job.getParameter(DISTANCE_T_QM_OUT).toString());

			System.out.println("com" + centerOfMass);
			System.out.println("com" + sortedMolecules.get(0).getAtoms());
			System.out.println("distance is " + distanceCalculator.closest(centerOfMass,sortedMolecules.get(0)) );

			//find first molecule that is outside of limit.
		    MolecularSystem.Molecule moleculeAtT_Qm_Out =
		       sortedMolecules.stream()
		                      .filter(molecule ->
		                               distanceCalculator.closest(centerOfMass, molecule) > distance_t_qm_out)
				              .findFirst()
				              .get();
			System.out.println("qmNumberSize is " + qmNumberSize);
			qmAdaptiveMolecules = new HashSet<>(sortedMolecules.subList(0, qmNumberSize));

			System.out.println("index " + sortedMolecules.indexOf(moleculeAtT_Qm_Out));

			bufferMolecules = new HashSet<>(sortedMolecules.subList(numberOfQm,
								    	  	  sortedMolecules.indexOf(moleculeAtT_Qm_Out) + 1));
		}
		qmAdaptiveAndBuffer.put(QM_ADAPTIVE,qmAdaptiveMolecules);
		qmAdaptiveAndBuffer.put(BUFFER,bufferMolecules);
		return qmAdaptiveAndBuffer;
	}

}
