import java.util.ArrayList;

public class TSPMatrixDistances {

    public static int[][] getMatrixDistances(ArrayList<City> cities) {
        int distances [][] = new int[cities.size()][cities.size()];

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distances[i][j] = cities.get(i).getDistanceCity(cities.get(j));
            }
        }
        return distances;
    }
}
