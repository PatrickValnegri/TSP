package TSP;

import TSP.algorithms.TSPCandidateList;
import TSP.algorithms.TSPMinimumSpanningTree;
import TSP.algorithms.TSPNearestNeighbour;
import TSP.algorithms.TSPSimulatedAnnealing;
import TSP.models.City;
import TSP.optimizators.TSP2Opt;
import TSP.utility.FileReader;
import TSP.utility.TSPMatrixDistances;
import TSP.utility.Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class main {

    //Distances between cities
    // TODO spostare nella classe utilities
    public static int[][] distances;
    public static int[][] getDistances() {
        return distances;
    }

    public static int[] positions;
    public static void setPositions(int[] positions) {
        main.positions = positions;
    }

    public static void main(String[] args) {
        //ArrayList with cities
        ArrayList<City> cities;

        //TSP reader
        FileReader fileReader = new FileReader();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        cities = fileReader.readFile(classloader.getResourceAsStream(Utilities.fileUsed));

        if (cities != null) {
            //TSP Minimum Spanning tree
            //TODO cambiare arraylist di città in un array
            City[] cityArray = new City[cities.size()];
            cityArray = cities.toArray(cityArray);

            //Matrix[][] of distances
            distances = TSPMatrixDistances.getMatrixDistances(cityArray);

            //Array cities position support
            positions = new  int[cityArray.length];
            for (int i = 0; i < cityArray.length; i++) {
                positions[cityArray[i].getId()] = i;
            }

            //TSP nearest neighbor
            TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();

            //2 opt
            TSP2Opt tsp2Opt = new TSP2Opt();

            //Minimum spanning tree
            TSPMinimumSpanningTree tspMinimumSpanningTree = new TSPMinimumSpanningTree(cityArray);
            tspMinimumSpanningTree.MST();

            //Candidate list nearest cities
            TSPCandidateList tspCandidateList = new TSPCandidateList(cityArray);
            tspCandidateList.nearestCities();

            City[] nearestNeighborTour = tspNearestNeighbour.nearestNeighbor(distances, cityArray);
            System.out.println("Best distance after nearest neighbor: " + Utilities.getTotalDistance(nearestNeighborTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(nearestNeighborTour)) + "%");

            City[] twoOptTour = tsp2Opt.twoOpt(nearestNeighborTour);
            System.out.println("Best distance after 2opt: " + Utilities.getTotalDistance(twoOptTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(twoOptTour)) + "%");

            //TSP simlated annealing
            TSPSimulatedAnnealing tspSimulatedAnnealing = new TSPSimulatedAnnealing();
            long SEED;

            int cont = 0;
            while (cont < 2) {

                //SEED = System.currentTimeMillis();
                SEED = 1556089269554l;
                Random rand = new Random(SEED);

                tspSimulatedAnnealing.simulatedAnnealing(twoOptTour, SEED, rand);

                cont++;
                }
        }
    }
}
