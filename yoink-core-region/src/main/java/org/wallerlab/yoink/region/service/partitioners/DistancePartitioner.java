package org.wallerlab.yoink.region.service.partitioners;

import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.xml_cml.schema.Parameter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static java.util.stream.Collectors.*;
import static org.wallerlab.yoink.api.service.region.Partitioner.Type.*;

/**
 * this class is for parameter based adaptive qm/mm partitioning
 * 			(Distance, Number and Size).
 *
 * it is to get QM_ADAPTIVE region , QM region, and the BUFFER region
 * 
 * @author Min Zheng
 *
 */
public class DistancePartitioner implements Partitioner{

	@Resource
	@Qualifier("simpleRegionFactory")
	protected Factory<Region, Region.Name> regionFactory;

	@Resource
	private Calculator<Double, Coord, Molecule> closestDistanceToMoleculeCalculator;

	@Override
	public List<Region> regionize(List<Region> regions, List<JobParameter> parameters) {

		Coord centerCoord = regions.get(QM_CORE).getCenterOfMass();
		List<Molecule> bufferMolecules = new ArrayList<>();
		List<Molecule> qmAdaptiveMolecules = new ArrayList<>();

		switch((Partitioner.Type) parameters.get(PARTITIONER)) {
			DISTANCE:
				distance(parameters, regions, qmAdaptiveMolecules, bufferMolecules, centerCoord);
				break;
			NUMBER, SIZE:
				numberOrSize(parameters, regions, qmAdaptiveMolecules, bufferMolecules, centerCoord);
				break;
		}

		regions.add(regionFactory.create(BUFFER, bufferMolecules));
		regions.add(regionFactory.create(QM_ADAPTIVE,qmAdaptiveMolecules));
		regions.add(regionFactory.create((QM,qmAdaptiveMolecules + regions.get(QM_CORE).getMolecularMap();
		return regions;
	}

	public void distance(List<Parameter> parameters,
						 List<Region> regions,
						 List<Molecule> qmAdaptiveMolecules,
						 List<Molecule> bufferMolecules,
						 Coord centerCoord) {

		double qmThreshold = (double) parameters.get(DISTANCE_QM);
		double bufferThreshold = qmThreshold + (double) parameters.get(DISTANCE_BUFFER);

		return regions.get(NONQM_CORE)
				.getMolecules()
				.stream()
				.forEach(molecule -> {
					double distance = closestDistanceToMoleculeCalculator.calculate(centerCoord, molecule);
					if (distance < qmThreshold) qmAdaptiveMolecules.add(molecule);
					else if (distance < bufferThreshold) bufferMolecules.add(molecule);
				});
	}

	public void numberOrSize(List<Parameter> parameters,
					  List<Region> regions,
					  List<Molecule> qmAdaptiveMolecules,
					  List<Molecule> bufferMolecules,
					  Coord centerCoord) {

		int qmNumber = (int) parameters.get(NUMBER_QM);
		int bufferNumber = qmNumber + (int) parameters.get(NUMBER_BUFFER);

		List<Molecule> sortedMolecules =
				regions.get(NONQM_CORE)
						.getMolecules()
						.stream()
						.sorted((m1, m2) -> {
							Double distance1 = closestDistanceToMoleculeCalculator.calculate(centerCoord, m1);
							Double distance2 = closestDistanceToMoleculeCalculator.calculate(centerCoord, m2);
							return distance1.compareTo(distance2);
						})
						.limit(bufferNumber)
						.collect(toList());

		Partitioner.Type partitionType = (Partitioner.Type) parameters.get(PARTITIONER);

		if (partitionType==SIZE){
			int qmNumberSize = (int) parameters.get(JobParameter.NUMBER_QM) * 2 / 3;
			double distance_s_qm_in = (double) parameters.get(DISTANCE_S_QM_IN);
			double distance_t_qm_out = (double) parameters.get(DISTANCE_T_QM_OUT);
			//find first molecule that is outside of limit.
			Molecule moleculeAtT_Qm_Out =
				sortedMolecules.stream()
						       .filter(molecule ->{
										double distance = closestDistanceToMoleculeCalculator.calculate(centerCoord, molecule);
										return ( distance > distance_t_qm_out);
										})
					.findFirst();

			qmAdaptiveMolecules = sortedMolecules.subList(0, qmNumber);
			List<Molecule> bufferMolecules = sortedMolecules.subList(qmNumber, sortedMolecules.indexOf(moleculeAtT_Qm_Out) + 1);

		}else if(partitionType==NUMBER) {
			qmAdaptiveMolecules = sortedMolecules.subList(0, qmNumber);
			bufferMolecules = sortedMolecules.subList(qmNumber, bufferNumber);
		}
		return;
	}


}
