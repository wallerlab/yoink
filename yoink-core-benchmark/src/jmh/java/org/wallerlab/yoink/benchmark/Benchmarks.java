package org.wallerlab.yoink.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;
import org.wallerlab.yoink.benchmark.domain.Region;
import org.wallerlab.yoink.benchmark.service.FunctionalDistanceCalculator;
import org.wallerlab.yoink.benchmark.service.ImperativeDistanceCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static org.wallerlab.yoink.benchmark.domain.Region.*;

/**
 * @author lukas241094
 */
@State(Scope.Thread)
public class Benchmarks {

	int numberOfAtoms;
	int numberOfMolecules;
	double qmThreshold;
	double bufferThreshold;
	List<Molecule> molecules;
	Core core = new Core(0.0,0.0,0.0);

	ImperativeDistanceCalculator imperativeCalculator = new ImperativeDistanceCalculator();
	FunctionalDistanceCalculator functionalCalculator = new FunctionalDistanceCalculator();

	@Setup
	public void setup() {
		qmThreshold = 2.0;
		bufferThreshold = 5.0;
		numberOfAtoms= 2;
		numberOfMolecules = 2;

		molecules =
				IntStream.range(0, numberOfMolecules)
						 .parallel()
						 .mapToObj(i -> new Molecule(i, numberOfAtoms))
						 .collect(Collectors.toList());
	}

	@Benchmark
	public void imperativeBenchmark(Blackhole bh) {
		bh.consume(imperative(molecules, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalBenchmark(Blackhole bh) {
		bh.consume(functional(molecules, qmThreshold, bufferThreshold,core));
	}

	public Map<Region, List<Molecule>> imperative(List<Molecule> molecules, double qmThreshold, double bufferThreshold, Core core){
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

	public Map<Region, List<Molecule>> functional(List<Molecule> molecules, double qmThreshold, double bufferThreshold, Core core){

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


}
