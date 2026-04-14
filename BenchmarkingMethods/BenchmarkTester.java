
package BenchmarkingMethods;

import BenchMarkingSorting.SortBenchmarkTests;
import Data_Structures.AVLTreeMap;
import Data_Structures.Treap;

import java.util.*;

public class BenchmarkTester {

    private static final int[] SIZES = { 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "reverse", "partial"};

    private static final int RUNS = 5;

    public static void main(String[] args) {

        List<BenchmarkResults> results = new ArrayList<>();

        for (int size : SIZES) {
            for (String type : INPUT_TYPES) {

                List<Integer> data = DataGenerator.generateByType(type, size);
                List<Integer> missing = DataGenerator.generateMissing(data);

                // TREAP
                results.add(runInsert("Treap", type, size, data));
                results.add(runSearchHit("Treap", type, size, data));
                results.add(runSearchMiss("Treap", type, size, data, missing));
                results.add(runRemove("Treap", type, size, data));

                // AVL
                results.add(runInsert("AVL", type, size, data));
                results.add(runSearchHit("AVL", type, size, data));
                results.add(runSearchMiss("AVL", type, size, data, missing));
                results.add(runRemove("AVL", type, size, data));

                // TreeMap
                results.add(runInsert("TreeMap", type, size, data));
                results.add(runSearchHit("TreeMap", type, size, data));
                results.add(runSearchMiss("TreeMap", type, size, data, missing));
                results.add(runRemove("TreeMap", type, size, data));


                // TREAP
                results.add(runInsert("Treap", type, size, data));
                results.add(runSearchHit("Treap", type, size, data));
                results.add(runSearchMiss("Treap", type, size, data, missing));
                results.add(runRemove("Treap", type, size, data));

                // AVL
                results.add(runInsert("AVL", type, size, data));
                results.add(runSearchHit("AVL", type, size, data));
                results.add(runSearchMiss("AVL", type, size, data, missing));
                results.add(runRemove("AVL", type, size, data));

                // TreeMap
                results.add(runInsert("TreeMap", type, size, data));
                results.add(runSearchHit("TreeMap", type, size, data));
                results.add(runSearchMiss("TreeMap", type, size, data, missing));
                results.add(runRemove("TreeMap", type, size, data));

            }
        }

        // Print CSV header
        System.out.println("structure,operation,input,size,time_ns");

        for (BenchmarkResults r : results) {
            System.out.println(r.toCSV());
        }
    }


    // INSERT
    private static BenchmarkResults runInsert(String structure, String type, int size, List<Integer> data) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            if (structure.equals("Treap")) {
                Treap<Integer, String> treap = new Treap<>(Integer::compare);
                for (int key : data) treap.insert(key, "v");
            }

            else if (structure.equals("AVL")) {
                AVLTreeMap<Integer, String> avl = new AVLTreeMap<>(Integer::compare);
                for (int key : data) avl.insert(key, "v");
            }

            else {
                TreeMap<Integer, String> map = new TreeMap<>();
                for (int key : data) map.put(key, "v");
            }

            long end = System.nanoTime();
            total += (end - start);
        }

        return new BenchmarkResults(structure, "insert", type, size, total / RUNS);
    }

    // SEARCH

    private static BenchmarkResults runSearchHit(String structure, String type, int size, List<Integer> data) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {

            if (structure.equals("Treap")) {
                Treap<Integer, String> treap = new Treap<>(Integer::compare);
                for (int key : data) treap.insert(key, "v");

                long start = System.nanoTime();
                for (int key : data) treap.get(key);
                total += (System.nanoTime() - start);
            }

            else if (structure.equals("AVL")) {
                AVLTreeMap<Integer, String> avl = new AVLTreeMap<>(Integer::compare);
                for (int key : data) avl.insert(key, "v");

                long start = System.nanoTime();
                for (int key : data) avl.get(key);
                total += (System.nanoTime() - start);
            }

            else {
                TreeMap<Integer, String> map = new TreeMap<>();
                for (int key : data) map.put(key, "v");

                long start = System.nanoTime();
                for (int key : data) map.get(key);
                total += (System.nanoTime() - start);
            }
        }

        return new BenchmarkResults(structure, "search_hit", type, size, total / RUNS);
    }

    private static BenchmarkResults runSearchMiss(String structure, String type, int size, List<Integer> data, List<Integer> missing) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {

            if (structure.equals("Treap")) {
                Treap<Integer, String> treap = new Treap<>(Integer::compare);
                for (int key : data) treap.insert(key, "v");

                long start = System.nanoTime();
                for (int key : missing) treap.get(key);
                total += (System.nanoTime() - start);
            }

            else if (structure.equals("AVL")) {
                AVLTreeMap<Integer, String> avl = new AVLTreeMap<>(Integer::compare);
                for (int key : data) avl.insert(key, "v");

                long start = System.nanoTime();
                for (int key : missing) avl.get(key);
                total += (System.nanoTime() - start);
            }

            else {
                TreeMap<Integer, String> map = new TreeMap<>();
                for (int key : data) map.put(key, "v");

                long start = System.nanoTime();
                for (int key : missing) map.get(key);
                total += (System.nanoTime() - start);
            }
        }

        return new BenchmarkResults(structure, "search_miss", type, size, total / RUNS);
    }

    private static BenchmarkResults runRemove(String structure, String type, int size, List<Integer> data) {

        long total = 0;

        for (int i = 0; i < RUNS; i++) {

            if (structure.equals("Treap")) {
                Treap<Integer, String> treap = new Treap<>(Integer::compare);
                for (int key : data) treap.insert(key, "v");

                long start = System.nanoTime();
                for (int key : data) treap.delete(key);
                total += (System.nanoTime() - start);
            }

            else if (structure.equals("AVL")) {
                AVLTreeMap<Integer, String> avl = new AVLTreeMap<>(Integer::compare);
                for (int key : data) avl.insert(key, "v");

                long start = System.nanoTime();
                for (int key : data) avl.delete(key);
                total += (System.nanoTime() - start);
            }

            else {
                TreeMap<Integer, String> map = new TreeMap<>();
                for (int key : data) map.put(key, "v");

                long start = System.nanoTime();
                for (int key : data) map.remove(key);
                total += (System.nanoTime() - start);
            }
        }


        return new BenchmarkResults(structure, "remove", type, size, total / RUNS);
    }
}