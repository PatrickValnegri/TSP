package TSP;

import TSP.models.Solver;

public class main {

    //Distances between cities
    //public static int[][] distances;

    //public static int[][] getDistances() {
    //    return distances;
    //}

    //public static int[] positions;

    //public static void setPositions(int[] positions) {
    //    main.positions = positions;
    //}

    public static void main(String[] args) {

        Solver solver = new Solver();
        solver.solve("ch130", 6110, 1556192027286l);

        Solver solver1 = new Solver();
        solver1.solve("d198", 15780, 1556192564752l);

        /*
        //String filename
        String filename = Utilities.fileName;
        String filenameTSP = filename + ".tsp";
        int bestKnown = Utilities.bestKnown;

        //ArrayList with cities
        ArrayList<City> cities;

        //TSP reader
        FileReader fileReader = new FileReader();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        cities = fileReader.readFile(classloader.getResourceAsStream(Utilities.fileTSP));

        if (cities != null) {
            //TSP Minimum Spanning tree
            City[] cityArray = new City[cities.size()];
            cityArray = cities.toArray(cityArray);

            //Matrix[][] of distances
            distances = MatrixDistances.getMatrixDistances(cityArray);

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

            City[] nearestNeighborTour = tspNearestNeighbour.nearestNeighbor(cityArray);
            System.out.println("Best distance after nearest neighbor: " + Utilities.getTotalDistance(nearestNeighborTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(nearestNeighborTour), bestKnown) + "%");

            City[] twoOptTour = tsp2Opt.twoOpt(nearestNeighborTour);
            System.out.println("Best distance after 2opt: " + Utilities.getTotalDistance(twoOptTour));
            System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(twoOptTour), bestKnown) + "%");

            //TSP simlated annealing
            TSPSimulatedAnnealing tspSimulatedAnnealing;
            long SEED;
            double temp;
            double alpha;

            SEED = System.currentTimeMillis();
            //SEED = 1556182944483l;
            Random rand = new Random();
            rand.setSeed(SEED);

            temp = rand.nextInt(150 - 100 + 1) + 100;
            alpha = 0.90 + (1 - 0.9) * rand.nextDouble();

            tspSimulatedAnnealing = new TSPSimulatedAnnealing(SEED, temp, alpha, rand, filenameTSP, bestKnown);
            City[] simulatedAnnealingTour = tspSimulatedAnnealing.simulatedAnnealing(twoOptTour);

            //Solution solution = new Solution(Utilities.fileName, SEED, simulatedAnnealingTour);
            Utilities.writeSolutions(Utilities.fileName, Utilities.bestKnown, simulatedAnnealingTour);
            */


    }

}
