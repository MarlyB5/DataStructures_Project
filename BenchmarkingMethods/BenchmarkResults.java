package BenchmarkingMethods;

public class BenchmarkResults {

    private final String structure;   // Treap / AVLTreeMap / TreeMap
    private final String operation;   // insert / search_hit / search_miss / remove / traversal
    private final String inputType;   // random / sorted / reverse / partial
    private final int size;           // number of elements
    private final long timeNs;        // average time in nanoseconds

    public BenchmarkResults(String structure, String operation, String inputType, int size, long timeNs) {
        this.structure = structure;
        this.operation = operation;
        this.inputType = inputType;
        this.size = size;
        this.timeNs = timeNs;
    }

    public String getStructure() {
        return structure;
    }

    public String getOperation() {
        return operation;
    }

    public String getInputType() {
        return inputType;
    }

    public int getSize() {
        return size;
    }

    public long getTimeNs() {
        return timeNs;
    }

    // Returns result as CSV line

    public String toCSV() {
        return structure + "," + operation + "," + inputType + "," + size + "," + timeNs;
    }


    @Override
    public String toString() {
        return String.format(
                "%-12s | %-12s | %-10s | size=%-6d | time=%d ns",
                structure, operation, inputType, size, timeNs
        );
    }
}