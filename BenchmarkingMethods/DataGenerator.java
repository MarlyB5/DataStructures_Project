package BenchmarkingMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class DataGenerator {

    // Fixed seed
    private static final long SEED = 42L;
    private static final Random RANDOM = new Random(SEED);

    private static void validateSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
    }

    // prevent instantiation
    private DataGenerator() {
    }

    public static List<Integer> generateRandom(int size) {
        validateSize(size);

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            data.add(i);
        }

        Collections.shuffle(data, RANDOM);
        return data;
    }

    public static List<Integer> generateSorted(int size) {
        validateSize(size);

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            data.add(i);
        }

        return data;
    }

    public static List<Integer> generateReverse(int size) {
        validateSize(size);

        List<Integer> data = new ArrayList<>();
        for (int i = size; i >= 1; i--) {
            data.add(i);
        }

        return data;
    }

    public static List<Integer> generatePartiallySorted(int size) {
        validateSize(size);

        List<Integer> data = generateSorted(size);

        if (size <= 1) {
            return data;
        }

        int swaps = Math.max(1, size / 10);

        for (int i = 0; i < swaps; i++) {
            int a = RANDOM.nextInt(size);
            int b = RANDOM.nextInt(size);
            Collections.swap(data, a, b);
        }

        return data;
    }

    public static List<Integer> generateMissing(List<Integer> existing) {
        if (existing == null) {
            throw new IllegalArgumentException("Existing key list cannot be null");
        }

        List<Integer> missing = new ArrayList<>(existing.size());
        int start = 1_000_000;

        for (int i = 0; i < existing.size(); i++) {
            missing.add(start + i);
        }

        return missing;
    }
    public static List<Integer> generateByType(String inputType, int size) {
        if (inputType == null) {
            throw new IllegalArgumentException("Input type cannot be null");
        }

        return switch (inputType.toLowerCase()) {
            case "random" -> generateRandom(size);
            case "sorted" -> generateSorted(size);
            case "reverse" -> generateReverse(size);
            case "partial", "partially_sorted", "partiallysorted" -> generatePartiallySorted(size);
            default -> throw new IllegalArgumentException("Unknown input type: " + inputType);
        };
    }

    public static boolean isUnique(List<Integer> data) {
        if (data == null) {
            return false;
        }

        Set<Integer> seen = new HashSet<>(data);
        return seen.size() == data.size();
    }

    public static List<Integer> copyOf(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        return new ArrayList<>(data);
    }


}