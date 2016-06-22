package org.wallerlab.yoink.benchmark;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Molecule;


@State(Scope.Thread)
public class SetOfBenchmarks {

	double qmThreshold;
	double bufferThreshold;
	
	int numberOfMoleculesS = 50;
	int numberOfMoleculesM = 5_000;
	int numberOfMoleculesL = 500_000;
	int numberOfAtomsS = 3;
	int numberOfAtomsM = 5;
	int numberOfAtomsL = 10;
	
	List<Molecule> moleculesMsmallAsmall;
	List<Molecule> moleculesMmediumAsmall;
	List<Molecule> moleculesMlargeAsmall;
	List<Molecule> moleculesMsmallAmedium;
	List<Molecule> moleculesMmediumAmedium;
	List<Molecule> moleculesMlargeAmedium;
	List<Molecule> moleculesMsmallAlarge;
	List<Molecule> moleculesMmediumAlarge;
	List<Molecule> moleculesMlargeAlarge;
	
	Core core = new Core(0.0,0.0,0.0);
	
	MethodStarter starter;
	

	@Setup
	public void setup() {
		qmThreshold = 2.0;
		bufferThreshold = 5.0;
		System.out.println("setting up molecules");
		
	
		moleculesMsmallAsmall = 
				IntStream.range(0,numberOfMoleculesS)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsS))
						 .collect(Collectors.toList());
		
		moleculesMmediumAsmall = 
				IntStream.range(0,numberOfMoleculesM)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsS))
						 .collect(Collectors.toList());
		
		moleculesMlargeAsmall = 
				IntStream.range(0,numberOfMoleculesL)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsS))
						 .collect(Collectors.toList());
		
		moleculesMsmallAmedium = 
				IntStream.range(0,numberOfMoleculesS)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsM))
						 .collect(Collectors.toList());
		
		moleculesMmediumAmedium = 
				IntStream.range(0,numberOfMoleculesM)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsM))
						 .collect(Collectors.toList());
		
		moleculesMlargeAmedium = 
				IntStream.range(0,numberOfMoleculesL)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsM))
						 .collect(Collectors.toList());
		
		moleculesMsmallAlarge = 
				IntStream.range(0,numberOfMoleculesS)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsL))
						 .collect(Collectors.toList());
		
		moleculesMmediumAlarge = 
				IntStream.range(0,numberOfMoleculesM)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsL))
						 .collect(Collectors.toList());
		
		moleculesMlargeAlarge = 
				IntStream.range(0,numberOfMoleculesL)
						 .parallel()
						 .mapToObj(i -> new Molecule(i,numberOfAtomsL))
						 .collect(Collectors.toList());

		starter = new MethodStarter();

	}
	
	
	//Molecules small atoms small

	@Benchmark
	public void imperativeMsAs(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMsmallAsmall, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMsmallAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMsmallAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMsmallAsmall, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMsAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMsmallAsmall, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules medium atoms small
	
	@Benchmark
	public void imperativeMmAs(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMmediumAsmall, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMmAs(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMmediumAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMmAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMmediumAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMmAs(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMmediumAsmall, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMmAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMmediumAsmall, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules large atoms small
	
	
	
	@Benchmark
	public void imperativeMlAs(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMlargeAsmall, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMlAs(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMlargeAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMlAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMlargeAsmall, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMlAs(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMlargeAsmall, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMlAs(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMlargeAsmall, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules small atoms medium
	
	
	
	@Benchmark
	public void imperativeMsAm(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMsmallAmedium, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMsAm(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMsmallAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMsAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMsmallAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMsAm(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMsmallAmedium, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMsAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMsmallAmedium, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules medium atoms medium
	
	
	@Benchmark
	public void imperativeMmAm(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMmediumAmedium, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMmAm(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMmediumAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMmAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMmediumAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMmAm(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMmediumAmedium, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMmAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMmediumAmedium, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules large atoms medium
	
	@Benchmark
	public void imperativeMlAm(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMlargeAmedium, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMlAm(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMlargeAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMlAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMlargeAmedium, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMlAm(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMlargeAmedium, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMlAm(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMlargeAmedium, qmThreshold, bufferThreshold,core));
	}
	
	
	
	//molecules small atoms large
	
	
	
	@Benchmark
	public void imperativeMsAl(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMsmallAlarge, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMsAl(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMsmallAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMsAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMsmallAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMsAl(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMsmallAlarge, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMsAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMsmallAlarge, qmThreshold, bufferThreshold,core));
	}
	
	
	//molecules medium atoms large
	
	@Benchmark
	public void imperativeMmAl(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMmediumAlarge, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMmAl(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMmediumAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMmAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMmediumAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMmAl(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMmediumAlarge, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMmAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMmediumAlarge, qmThreshold, bufferThreshold,core));
	}
	
	
	//molecules large atoms large
	
	@Benchmark
	public void imperativeMlAl(Blackhole bh) {
		bh.consume(starter.calculateImperative(moleculesMlargeAlarge, qmThreshold, bufferThreshold,core));
	}

	@Benchmark
	public void functionalMlAl(Blackhole bh) {
		bh.consume(starter.calculateStreamSerial(moleculesMlargeAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelMlAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelFunctionalCDC(moleculesMlargeAlarge, qmThreshold, bufferThreshold,core));
	}
	
	@Benchmark
	public void functionalParallelImperativeCDCMlAl(Blackhole bh) {
		bh.consume(starter.calculateStreamPrallelImpCDC(moleculesMlargeAlarge, qmThreshold, bufferThreshold,core));
	}
	@Benchmark
	public void functionalParallelstreamMlAl(Blackhole bh) {
		bh.consume(starter.calculateStreamParallelstream(moleculesMlargeAlarge, qmThreshold, bufferThreshold,core));
	}
	
	
	public static void main(String[] args) throws Exception {
		Options options = new OptionsBuilder()
				.include(SetOfBenchmarks.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(10)
				.forks(2)
				.build();
		new Runner(options).run();
	}
}
