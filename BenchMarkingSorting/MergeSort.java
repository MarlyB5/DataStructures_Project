package BenchMarkingSorting;
import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

    public static <T> void mergeSort(T[] data, Comparator<T> comparator) {

        if (data == null || data.length < 2) return;

        int mid = data.length / 2;

        T[] left = Arrays.copyOfRange(data, 0, mid);
        T[] right = Arrays.copyOfRange(data, mid, data.length);

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        mergeArray(data, left, right, comparator);
    }

    private static <T> void mergeArray(
            T[] data, T[] left, T[] right, Comparator<T> comparator) {

        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {

            if (comparator.compare(left[i], right[j]) <= 0) {
                data[k++] = left[i++];
            } else {
                data[k++] = right[j++];
            }
        }

        while (i < left.length) {
            data[k++] = left[i++];
        }

        while (j < right.length) {
            data[k++] = right[j++];
        }
    }
}
