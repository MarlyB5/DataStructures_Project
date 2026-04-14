package BenchMarkingSorting;


import BenchmarkingMethods.DataGenerator;
import java.util.ArrayList;
import java.util.List;

public class SortBenchmarkTests {

    private SortBenchmarkTests() {
        // Utility class
    }

    public static long benchmarkSorting(String algorithm, List<Integer> data) {
        List<Integer> copy = new ArrayList<>(data);

        long start = System.nanoTime();

        switch (algorithm) {
            case "TreapSort":
                SortingAlgorithms.treapSort(copy);
                break;
            case "PQSort":
                SortingAlgorithms.pqSort(copy);
                break;
            case "QuickSort":
                SortingAlgorithms.quickSort(copy);
                break;
            case "MergeSort":
                SortingAlgorithms.mergeSort(copy);
                break;
            case "JavaSort":
                SortingAlgorithms.javaSort(copy);
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        long end = System.nanoTime();
        return end - start;
    }
}

