//package sample;
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.Scope;
//import org.openjdk.jmh.annotations.Setup;
//import org.openjdk.jmh.annotations.State;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//
///**
// * @author taichi
// * @see <a href=
// *      "http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/">
// *      JMH Examples </a>
// */
//@State(Scope.Thread)
//public class HelloBenchmark {
//
//    @Benchmark
//    public void doNothing() {
//    }
//
//    int[] ary;
//
//    @Setup
//    public void setup() {
//        ary = new int[4096];
//    }
//
//    @Benchmark
//    public void simpleLoop() {
//        for (int i = 0; i < ary.length; i++) {
//            ary[i] = 10;
//        }
//    }
//
//    @Benchmark
//    public void complexLoop() {
//        for (int i = 0, l = ary.length; i < l; i++) {
//            ary[i] = 10;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        Options options = new OptionsBuilder()
//                .include(HelloBenchmark.class.getSimpleName())
//                .warmupIterations(5)
//                .measurementIterations(10)
//                .forks(2)
//                .build();
//        new Runner(options).run();
//    }
//}