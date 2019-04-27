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

import java.util.ArrayList;
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
        cities = fileReader.readFile(classloader.getResourceAsStream(Utilities.fileTSP));

        if (cities != null) {
            //TSP Minimum Spanning tree
            //TODO cambiare arraylist di città in un array
            City[] cityArray = new City[cities.size()];
            cityArray = cities.toArray(cityArray);

            //Matrix[][] of distances
            distances = TSPMatrixDistances.getMatrixDistances(cityArray);

            //Array cities position support
            positions = new int[cityArray.length];
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
            TSPSimulatedAnnealing tspSimulatedAnnealing;
            long SEED;
            double temp;
            double alpha;

            int cont = 0;
            while (cont < 1) {

                SEED = System.currentTimeMillis();
                //SEED = 1556182944483l;
                Random rand = new Random();
                rand.setSeed(SEED);

                temp = rand.nextInt(150 - 100 + 1) + 100;
                alpha = 0.90 + (1 - 0.9) * rand.nextDouble();

                //Best distance after simulated annealing: 8857
                //Error: 0.5791505791505791%
                //Temperature: 148.0
                //Alpha: 0.9054248337075167
                //SEED: 1556145362740

                tspSimulatedAnnealing = new TSPSimulatedAnnealing(SEED, temp, alpha, rand);
                City[] simulatedAnnealingTour = tspSimulatedAnnealing.simulatedAnnealing(twoOptTour);

                //Solution solution = new Solution(Utilities.fileName, SEED, simulatedAnnealingTour);
                Utilities.writeSolutions(Utilities.fileName, Utilities.bestKnown, simulatedAnnealingTour);
                cont++;
            }


        }
    }
}
