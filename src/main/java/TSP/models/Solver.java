package TSP.models;

import TSP.algorithms.TSPCandidateList;
import TSP.algorithms.TSPMinimumSpanningTree;
import TSP.algorithms.TSPNearestNeighbour;
import TSP.algorithms.TSPSimulatedAnnealing;
import TSP.optimizators.TSP2Opt;
import TSP.utility.FileReader;
import TSP.utility.MatrixDistances;
import TSP.utility.Utilities;

import java.util.ArrayList;
import java.util.Random;

public class Solver {

    private int[][] distances;
    private int[] positions;

    public int[][] getDistances() {
        return distances;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions.clone();
    }

    public void solve(String filename, int bestKnown, long SEED) {

        String fileTSP = filename + ".tsp";

        System.out.println("FILE: " + fileTSP);
        System.out.println("BEST KNOWN: " + bestKnown);

        //ArrayList with cities
        ArrayList<City> cities;

        //Parse file and build list of cities
        FileReader fileReader = new FileReader();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        cities = fileReader.readFile(classloader.getResourceAsStream(fileTSP));

        if (cities != null) {

            //convert list of cities into array
            City[] cityArray = new City[cities.size()];
            cityArray = cities.toArray(cityArray);

            //Matrix[][] of distances
            distances = MatrixDistances.getMatrixDistances(cityArray);

            //Array of cities position support
            positions = new int[cityArray.length];
            for (int i = 0; i < cityArray.length; i++) {
                positions[cityArray[i].getId()] = i;
            }

            //nearest neighbor
            TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour(this);

            //2opt
            TSP2Opt tsp2Opt = new TSP2Opt(this);

            //Minimum spanning tree
            TSPMinimumSpanningTree tspMinimumSpanningTree = new TSPMinimumSpanningTree(cityArray, this);
            tspMinimumSpanningTree.MST();

            //Candidate list nearest cities
            TSPCandidateList tspCandidateList = new TSPCandidateList(cityArray, this);
            tspCandidateList.nearestCities();

            //run nearest neighbor
            City[] nearestNeighborTour = tspNearestNeighbour.nearestNeighbor(cityArray);
            //System.out.println("Best distance after nearest neighbor: " + Utilities.getTotalDistance(nearestNeighborTour));
            //System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(nearestNeighborTour), bestKnown) + "%");

            //run 2opt
            City[] twoOptTour = tsp2Opt.twoOpt(nearestNeighborTour);
            //System.out.println("Best distance after 2opt: " + Utilities.getTotalDistance(twoOptTour));
            //System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(twoOptTour), bestKnown) + "%");

            //simulated annealing
            TSPSimulatedAnnealing tspSimulatedAnnealing;
            double temp;
            double alpha;

            //Generate random with seed
            Random rand = new Random();
            rand.setSeed(SEED);

            temp = rand.nextInt(150 - 100 + 1) + 100;
            alpha = 0.90 + (1 - 0.9) * rand.nextDouble();

            //run simulated annealing
            tspSimulatedAnnealing = new TSPSimulatedAnnealing(SEED, temp, alpha, rand, fileTSP, bestKnown, this);
            City[] simulatedAnnealingTour = tspSimulatedAnnealing.simulatedAnnealing(twoOptTour);

            //Solution solution = new Solution(Utilities.fileName, SEED, simulatedAnnealingTour);
            Utilities.writeSolutions(filename, bestKnown, simulatedAnnealingTour);
        }
    }


}
