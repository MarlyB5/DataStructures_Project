package BenchMarkingSorting;

import BenchmarkingMethods.BenchmarkResults;
import BenchmarkingMethods.DataGenerator;

import java.util.ArrayList;
import java.util.List;

public class SortingBenchmarkRunner {

    private static final int[] SIZES = {100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "reverse", "partial"};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        List<BenchmarkResults> results = new ArrayList<>();

        for (int size : SIZES) {
            for (String type : INPUT_TYPES) {
                List<Integer> data = DataGenerator.generateByType(type, size);

                results.add(runSorting("TreapSort", type, size, data));
                results.add(runSorting("PQSort", type, size, data));
                results.add(runSorting("QuickSort", type, size, data));
                results.add(runSorting("MergeSort", type, size, data));
                results.add(runSorting("JavaSort", type, size, data));
            }
        }

        System.out.println("structure,operation,input,size,time_ns");
        for (BenchmarkResults r : results) {
            System.out.println(r.toCSV());
        }
    }

    private static BenchmarkResults runSorting(String algorithm, String type, int size, List<Integer> data) {
        long total = 0;

        for (int i = 0; i < RUNS; i++) {
            total += SortBenchmarkTests.benchmarkSorting(algorithm, data);
        }

        return new BenchmarkResults("Sorting", algorithm, type, size, total / RUNS);
    }
}