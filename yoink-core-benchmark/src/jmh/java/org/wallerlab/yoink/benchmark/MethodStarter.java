package org.wallerlab.yoink.benchmark;

import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.domain.Region;
import org.wallerlab.yoink.benchmark.functional.PAFunctionalClosestDistanceCalcualtor;
import org.wallerlab.yoink.benchmark.imperative.PAImperativeClosestDistanceCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.wallerlab.yoink.benchmark.domain.Region.*;

/**
 * @author lukas241094
 */
public class MethodStarter {
	
	PAImperativeClosestDistanceCalculator imperativeCalculator = new PAImperativeClosestDistanceCalculator();
	PAFunctionalClosestDistanceCalcualtor functionalCalculator = new PAFunctionalClosestDistanceCalcualtor();
	
	public Map<Region, List<Molecule>> calculateImperative(List<Molecule> molecules,double qmThreshold, double bufferThreshold,Core core){
		Map<Region, List<Molecule>> regions = new HashMap<>();
		List<Molecule> qmRegion = new ArrayList();
		List<Molecule> bufferRegion = new ArrayList();
		List<Molecule> mmRegion = new ArrayList();
		for (Molecule molecule : molecules){
			double distance = imperativeCalculator.calculateClosestDistance(core, molecule);
			if(distance < qmThreshold) qmRegion.add(molecule);
				else if(distance < bufferThreshold) bufferRegion.add(molecule);
					else mmRegion.add(molecule);
		}
		regions.put(Region.QMZONE, qmRegion);
		regions.put(Region.BUFFERZONE, bufferRegion);
		regions.put(Region.MM, mmRegion);
		return regions;
	}
	
	
	public Map<Region, List<Molecule>> calculateStreamSerial(List<Molecule> molecules,double qmThreshold, double bufferThreshold,Core core){

		return molecules.stream()
				      //.parallel()
				 		.collect(
						 groupingBy(molecule -> {
							 double distance = functionalCalculator.calculateClosestDistance(core, molecule);
							 if (distance < qmThreshold) return QMZONE;
							 	else if (distance > bufferThreshold) return MM;
							 		else return BUFFERZONE;
						 }));
	}
	
	
	public Map<Region, List<Molecule>> calculateStreamParallelFunctionalCDC(List<Molecule> molecules,double qmThreshold, double bufferThreshold,Core core){

		return molecules.stream()
				 .parallel()
				 .collect(
						 groupingBy(molecule -> {
							 double distance = functionalCalculator.calculateClosestDistance(core, molecule);
							 if (distance < qmThreshold) return QMZONE;
							 	else if (distance > bufferThreshold) return MM;
							 		else return BUFFERZONE;
						 }));
	}

	public Map<Region, List<Molecule>> calculateStreamPrallelImpCDC(List<Molecule> molecules,double qmThreshold, double bufferThreshold,Core core){

		return molecules.stream()
				 .parallel()
				 .collect(groupingBy(molecule -> {
							 double distance = imperativeCalculator.calculateClosestDistance(core, molecule);
							 if (distance < qmThreshold) return QMZONE;
							 	else if (distance > bufferThreshold) return MM;
							 		else return BUFFERZONE;
						 }));
	}
	
	public Map<Region, List<Molecule>> calculateStreamParallelstream(List<Molecule> molecules,double qmThreshold, double bufferThreshold,Core core){
		
		return molecules.parallelStream()
				 		.collect(groupingBy(molecule -> {
							 double distance = functionalCalculator.calculateClosestDistance(core, molecule);
							 if (distance < qmThreshold) return QMZONE;
							 	else if (distance > bufferThreshold) return MM;
							 		else return BUFFERZONE;
						 }));
	}

}
