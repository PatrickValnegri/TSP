package TSP.utility;

import TSP.models.City;
import TSP.models.Distance;

public class TSPMatrixDistances {

    public static Distance[][] getMatrixDistances(City[] cities) {
        int len = cities.length;
        Distance distances [][] = new Distance[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                distances[i][j] = new Distance(cities[i].getDistanceCity(cities[j]), cities[j]) ;
            }
        }
        return distances;
    }
}
