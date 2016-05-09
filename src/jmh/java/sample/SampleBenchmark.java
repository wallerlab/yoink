package sample;

import org.openjdk.jmh.annotations.Benchmark;
import sample.SampleClass;


public class SampleBenchmark {

    @Benchmark
     public String testYoink() {
        return SampleClass.yoinkTest();
    }

}
