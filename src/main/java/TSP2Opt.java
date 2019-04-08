import java.util.HashSet;
import java.util.Set;

public class TSP2Opt {

    private static int numberOfNodes;

    public static int getGain(City[] tour, int i, int j) {
        int a = i == 0 ? tour.length - 1 : i - 1;
        int b = j == tour.length -1 ? 0 : j + 1;

        int dist1 = (Utilities.getDistanceBetweenCities(tour[i], tour[a]) + Utilities.getDistanceBetweenCities(tour[j], tour[b]));
        int dist2 = (Utilities.getDistanceBetweenCities(tour[a], tour[j]) + Utilities.getDistanceBetweenCities(tour[i], tour[b]));

        //int dist1 = (tour[i].getDistanceCity(tour[a]) + tour[j].getDistanceCity(tour[b]));
        //int dist2 = (tour[a].getDistanceCity(tour[j]) + tour[i].getDistanceCity(tour[b]));

        return dist2 - dist1;
    }

    public static City[] twoOpt(City[] tour) {

        numberOfNodes = tour.length;
        int bestGain = -1;
        int gain;
        int best_i = 0;
        int best_j = 0;

        City[] bestTour = tour;

        int count = 0;

        while (bestGain < 0) {
            bestGain = 0;

            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = i + 1; j < numberOfNodes-1; j++) {
                    gain = getGain(bestTour, i, j);

                    if (gain < bestGain) {
                        bestGain = gain;
                        best_i = i;
                        best_j = j;
                    }
                }
                count++;
            }
            if (bestGain < 0) {
                bestTour = swap(bestTour, best_i, best_j);
            }
        }

        //System.out.println(hasDuplicateCities(bestTour));
        //System.out.println("Contatore cicli: " + count);
        return bestTour;
    }

    public static City[] swap(City[] cities, int i, int j) {

        int t = 0;
        for (int index = 0; index <= ((double)((j-i)/2)); index++) {
            City city = cities[i+t];
            cities[i+t] = cities[j-t];
            cities[j-t] = city;

            t++;
        }

        return cities;
    }

    public static boolean hasDuplicateCities(City[] items) {
        Set<City> appeared = new HashSet<City>();
        for (City city : items) {
            if (!appeared.add(city)) {
                return true;
            }
        }
        return false;
    }


}
