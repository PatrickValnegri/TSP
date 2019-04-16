package TSP;

import TSP.algorithms.TSPCandidateList;
import TSP.algorithms.TSPMinimumSpanningTree;
import TSP.algorithms.TSPNearestNeighbour;
import TSP.algorithms.TSPSimulatedAnnealing;
import TSP.models.City;
import TSP.models.Distance;
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

    private static final String FILENAME = "C:\\Users\\pvaln\\OneDrive\\Documenti\\SUPSI\\TerzoAnno\\SecondoSemestre\\Algoritmi\\Coppa\\risultati.txt";

    //Distances between cities //TODO spostare nella classe utilities
    public static Distance[][] distances;

    public static Distance[][] getDistances() {
        return distances;
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


            //TSP nearest neighbor
            TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();

            //2 opt
            TSP2Opt tsp2Opt = new TSP2Opt();

            //Matrix[][] of distances
            distances = TSPMatrixDistances.getMatrixDistances(cityArray);

            TSPMinimumSpanningTree tspMinimumSpanningTree = new TSPMinimumSpanningTree(cityArray);
            tspMinimumSpanningTree.MST();

            //Candidate list nearest cities
            TSPCandidateList tspCandidateList = new TSPCandidateList(cityArray);
            tspCandidateList.nearestCities();

            //Distances inside a candidate list
            Iterator<City> itr = cityArray[0].getCandidateList().iterator();
            while (itr.hasNext()) {
                System.out.print(distances[cityArray[0].getId()][itr.next().getId()].getDistance() + " ");
            }

            //TSP simlated annealing
            TSPSimulatedAnnealing tspSimulatedAnnealing = new TSPSimulatedAnnealing();
            long SEED;
            int cont = 0;
            int temperature;
            double alpha;

            City[] nearestNeighborTour = tspNearestNeighbour.nearestNeighbor(distances, cities);
            System.out.println("Best distance after nearest neighbor: " + Utilities.getTotalDistance(nearestNeighborTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(nearestNeighborTour)) + "%");

            City[] twoOptTour = tsp2Opt.twoOpt(nearestNeighborTour);
            System.out.println("Best distance after 2opt: " + Utilities.getTotalDistance(twoOptTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(twoOptTour)) + "%");

            while (cont < 2) {

                //SEED = System.currentTimeMillis();
                SEED = 1554791770434L;
                Random rand = new Random(SEED);

                //temperature = rand.nextInt(150 - 100 + 1) + 100;
                //alpha = 0.90 + (1 - 0.9) * rand.nextDouble();
                temperature = 122;
                alpha = 0.931877;

                City[] simulatedAnnealingTour = tspSimulatedAnnealing.simulatedAnnealing(twoOptTour, temperature, alpha, rand);
                System.out.println("Best distance after simulated annealing: " + Utilities.getTotalDistance(simulatedAnnealingTour));
                System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(simulatedAnnealingTour)) + "%");
                System.out.println("Temperature: " + temperature);
                System.out.println("Alpha: " + alpha);
                System.out.println("SEED: " + SEED);

                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(FILENAME, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.printf("File: %s \n", Utilities.fileUsed);
                printWriter.printf("Best: %d \n", Utilities.bestUsed);
                printWriter.printf("Best distance calculated: %d \n", Utilities.getTotalDistance(simulatedAnnealingTour));
                printWriter.printf("Error is: %f%s \n", Utilities.getError(Utilities.getTotalDistance(simulatedAnnealingTour)), "%");
                printWriter.printf("Temperature: %d \n", temperature);
                printWriter.printf("Alpha: %f \n", alpha);
                printWriter.printf("SEED: %d \n", SEED);
                printWriter.printf("\n");
                printWriter.close();

                cont++;
            }
        }
    }
}
