package TSP.algorithms;

import TSP.main;
import TSP.models.City;
import TSP.models.Solver;

//15 nearest cities
public class TSPCandidateList {

    private int[][] distances;
    private City[] cities;
    private static final int N = 15;
    private Solver solver;

    public TSPCandidateList(City[] cities, Solver solver) {
        this.distances = solver.getDistances();
        this.cities = cities;
        this.solver = solver;
    }

    public void nearestCities() {
        int[][] tmp = distances.clone();
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = tmp[i].clone();
        }

        for (City c : cities) {
            int index;
            for (int i = 0; i < N; i++) {
                index = findMin(tmp, c.getId());
                //c.addCandidate(distances[c.getId()][i]);
                c.addCandidate(index);
                tmp[c.getId()][index] = 0;

            }
        }
    }

    //Find min id
    public static int findMin(int array[][], int cityID) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < array[cityID].length; i++) {
            int tmp = array[cityID][i];

            if (tmp < min && tmp > 0) {
                min = tmp;
                index = i;
            }
        }

        return index;
    }
}
