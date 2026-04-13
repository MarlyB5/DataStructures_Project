package BenchMarkingSorting;

import java.util.Comparator;

public class QuickSort {

    public static <T> void quickSort(T[] data, Comparator<T> comparator) {
        if (data == null || data.length < 2) {
            return;
        }

        quickSortArray(data, 0, data.length - 1, comparator);
    }

    private static <T> void quickSortArray(T[] data, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partitionArray(data, low, high, comparator);

            quickSortArray(data, low, pivotIndex - 1, comparator);
            quickSortArray(data, pivotIndex + 1, high, comparator);
        }
    }

    private static <T> int partitionArray(T[] data, int low, int high, Comparator<T> comparator) {
        T pivot = data[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(data[j], pivot) <= 0) {
                i++;

                T temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        T temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        return i + 1;
    }

}