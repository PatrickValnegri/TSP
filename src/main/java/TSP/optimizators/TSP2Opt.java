package TSP.optimizators;

import TSP.main;
import TSP.models.City;
import TSP.utility.Utilities;

import java.util.*;

public class TSP2Opt {

    private static int numberOfNodes;

    public static int getGain(City[] tour, int i, int j) {
        int afteri = i == tour.length - 1 ? 0 : i + 1;
        int afterj = j == tour.length - 1 ? 0 : j + 1;

        int dist1 = (Utilities.getDistanceBetweenCities(tour[i], tour[afteri]) + Utilities.getDistanceBetweenCities(tour[j], tour[afterj]));
        int dist2 = (Utilities.getDistanceBetweenCities(tour[i], tour[j]) + Utilities.getDistanceBetweenCities(tour[afteri], tour[afterj]));

        //int dist1 = (tour[i].getDistanceCity(tour[a]) + tour[j].getDistanceCity(tour[b]));
        //int dist2 = (tour[a].getDistanceCity(tour[j]) + tour[i].getDistanceCity(tour[b]));

        return dist2 - dist1;
    }

    public static City[] twoOpt(City[] cities) {

        numberOfNodes = cities.length;
        int bestGain = -1;
        int gain;
        int best_i = 0;
        int best_j = 0;

        City[] bestTour = cities;
        //int count = 0;

        while (bestGain < 0) {
            bestGain = 0;

            for (int i = 0; i < numberOfNodes; i++) {
                //for (int j = i + 1; j < numberOfNodes; j++) {
                Iterator<Integer> iterator = cities[i].getCandidateList().iterator();

                while (iterator.hasNext()) {
                    int j = main.positions[iterator.next()];

                    if (j != i) {
                        gain = getGain(bestTour, i, j);

                        if (gain < bestGain) {
                            bestGain = gain;
                            best_i = i;
                            best_j = j;
                        }
                    }
                }
                //count++;
            }
            if (bestGain < 0) {
                bestTour = swap(bestTour, best_i, best_j);
            }
        }

        //System.out.println(hasDuplicateCities(bestTour));
        return bestTour;
    }

    public static City[] swap(City[] cities, int i, int j) {
        //da 0 a i
        //i -> j => j -> i + 1
        //i + 1 -> j + 1
        //j + 1 a length -1

        boolean swapped = false;
        if (j < i) {
            int x = i;
            i = j;
            j = x;
            swapped = true;
        }

        for (int index = 0; i + 1 + index <= (((i + 1 + j) / 2)); index++) {
            City city = cities[i + 1 + index];
            cities[i + 1 + index] = cities[j - index];
            cities[j - index] = city;
            main.positions[cities[i + 1 + index].getId()] = i + 1 + index;
            main.positions[cities[j - index].getId()] = j - index;
        }

        if (swapped) {
            int len = cities.length;

            //Collections.reverse(Arrays.asList(cities));
            for (int k = 0; k < len ; k++) {
                City city = cities[k];
                cities[k] = cities[len - k - 1];
                cities[len - k - 1] = city;
                main.positions[cities[k].getId()] = k;
                main.positions[cities[len - k - 1].getId()] = len-k-1;
            }
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
