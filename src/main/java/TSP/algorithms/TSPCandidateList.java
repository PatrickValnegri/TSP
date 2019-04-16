package TSP.algorithms;

import TSP.main;
import TSP.models.City;
import TSP.models.Distance;

import java.util.Arrays;

public class TSPCandidateList {

    private Distance[][] distances;
    private City[] cities;
    private static final int N = 15;

    public TSPCandidateList(City[] cities) {
        this.distances = main.getDistances();
        this.cities = cities;
    }

    public void nearestCities() {
        Distance[][] tmp = distances;
        sortEachRow(tmp);

        for (City c: cities) {
            for (int i = 0; i < N; i ++) {
                c.addCandidate(distances[c.getId()][i].getCity());
            }
            System.out.println();
        }
    }

    public static void sortEachRow(Distance array[][]) {
        for(Distance[] row : array) {
            Arrays.sort(row);
        }
    }
}
