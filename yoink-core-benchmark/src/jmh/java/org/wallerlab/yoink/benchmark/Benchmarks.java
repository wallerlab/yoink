package org.wallerlab.yoink.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@State(Scope.Thread)
public class Benchmarks {

	double qmThreshold;
	double bufferThreshold;

	int numberOfAtoms;
	int numberOfMolecules;

	List<Molecule> molecules;

	
	Core core = new Core(0.0,0.0,0.0);
	
	MethodStarter starter;

	@Setup
	public void setup() {
		qmThreshold = 2.0;
		bufferThreshold = 5.0;
		System.out.println("setting up molecules");

		numberOfAtoms= 2;
		numberOfMolecules = 2;
		molecules =
				IntStream.range(0, numberOfMolecules)
						 .parallel()
						 .mapToObj(i -> new Molecule(i, numberOfAtoms))
						 .collect(Collectors.toList());

		starter = new MethodStarter();

	}
	
	
	//Molecules small atoms small

	@Benchmark
	public void imperativeMsAs(Blackhole bh) {
		bh.consume(starter.calculateImperative(molecules, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(molecules, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(molecules, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(molecules, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(molecules, qmThreshold, bufferThreshold,core));
	}


}
