package TSP.models;

public class Solution {
    private String fileName;
    private long SEED;
    private City[] tour;

    public Solution(String fileName, long SEED, City[] tour) {
        this.fileName = fileName;
        this.SEED = SEED;
        this.tour = tour;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSEED() {
        return SEED;
    }

    public City[] getTour() {
        return tour;
    }
}
