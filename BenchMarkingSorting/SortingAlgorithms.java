package BenchMarkingSorting;


import Data_Structures.Treap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class SortingAlgorithms {

    private SortingAlgorithms() {
        // Utility class
    }

    public static List<Integer> treapSort(List<Integer> input) {
        Treap<Integer, Integer> treap = new Treap<>(Integer::compareTo);

        for (Integer value : input) {
            treap.put(value, value);
        }

        return treap.inOrderKeys();
    }

    public static List<Integer> pqSort(List<Integer> input) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(input);
        List<Integer> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }

        return result;
    }

    public static List<Integer> quickSort(List<Integer> input) {
        Integer[] array = input.toArray(new Integer[0]);
        QuickSort.quickSort(array, Integer::compareTo);
        return new ArrayList<>(Arrays.asList(array));
    }

    public static List<Integer> mergeSort(List<Integer> input) {
        Integer[] array = input.toArray(new Integer[0]);
        MergeSort.mergeSort(array, Integer::compareTo);
        return new ArrayList<>(Arrays.asList(array));
    }

    public static List<Integer> javaSort(List<Integer> input) {
        List<Integer> copy = new ArrayList<>(input);
        Collections.sort(copy);
        return copy;
    }
}