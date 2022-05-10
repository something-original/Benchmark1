package myBenchmarkJava;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


public class MyBenchmarkJava {

    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @State(Scope.Benchmark)
    @Fork(2)
    @Warmup(iterations = 4)
    @Measurement(iterations = 10)


    public static class MyBenchmark {

        @Param({"10000"})
        private int n = 10000;

        @NotNull
        private ArrayList<Integer> DATA_FOR_TESTING = new ArrayList<>();


        public static void main(@NotNull String[] args) throws RunnerException {
            Intrinsics.checkNotNullParameter(args, "args");
            Options opt = (new OptionsBuilder()).include(MyBenchmark.class.getSimpleName()).forks(1).build();
            (new Runner(opt)).run();
        }

        @Setup
        public final void setup() {
            this.DATA_FOR_TESTING = this.createData();
        }

        private ArrayList<Integer> createData() {
            ArrayList<Integer> data = new ArrayList<>();
            int i = 0;

            for(int var3 = this.n; i < var3; ++i) {
                data.add(i);
            }

            return data;
        }

        @Benchmark
        public final void testMoreEffective() {
            int i = 0;

            for(int var2 = this.DATA_FOR_TESTING.size(); i < var2; ++i) {
                Object var10000 = this.DATA_FOR_TESTING.get(i);
                Intrinsics.checkNotNullExpressionValue(var10000, "DATA_FOR_TESTING[i]");
                int limit = ((Number)var10000).intValue();
                moreEffective(limit);
            }

        }

        private static int moreEffective(int limit) {
            int result = 0;
            boolean[] sieveErat = new boolean[limit + 1];
            if (limit <= 2) {
                return 1;
            } else {
                for (int ind = 2; ind < Math.floor(Math.sqrt(limit)); ind++) {
                    if (!sieveErat[ind]) {
                        int j = ind * ind;
                        while (j < limit) {
                            sieveErat[j] = true;
                            j += ind;
                        }
                    }
                }

                for (int ind1 = 2; ind1 < limit; ind1++) {
                    if (!sieveErat[ind1]) result++;
                }
            }
            return result;
        }

        @Benchmark
        public final void testLessEffective() {
            int i = 0;

            for(int var2 = this.DATA_FOR_TESTING.size(); i < var2; ++i) {
                Object var10000 = this.DATA_FOR_TESTING.get(i);
                Intrinsics.checkNotNullExpressionValue(var10000, "DATA_FOR_TESTING[i]");
                int limit = ((Number)var10000).intValue();
                lessEffective(limit);
            }

        }

        private static Integer lessEffective(int limit) {
            int result = 2;
            int divisor = 2;

            if (limit == 1) return 1;
            if (limit == 2) return 2;


            for (int j = 3; j <= limit; j++) {
                while (j % divisor != 0) {
                    divisor++;
                    if (divisor == j) {
                        result++;
                        break;
                    }
                }
                divisor = 2;
            }
            return result;
        }
    }
}
