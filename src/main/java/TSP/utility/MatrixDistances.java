package TSP.utility;

import TSP.models.City;

public class MatrixDistances {

    public static int[][] getMatrixDistances(City[] cities) {
        int len = cities.length;
        int distances[][] = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                distances[i][j] = cities[i].getDistanceCity(cities[j]);
            }
        }
        return distances;
    }
}
