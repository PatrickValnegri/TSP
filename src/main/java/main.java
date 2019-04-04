import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class main {

    final static String file1 = "ch130.tsp"; //0.0
    final static String file2 = "d198.tsp"; //0.0
    final static String file3 = "eil76.tsp"; //0.0
    final static String file4 = "fl1577.tsp";
    final static String file5 = "kroA100.tsp"; //0.0
    final static String file6 = "lin318.tsp"; //0.005
    final static String file7 = "pcb442.tsp";
    final static String file8 = "pr439.tsp";
    final static String file9 = "rat783.tsp";
    final static String file10 = "u1060.tsp";

    final static int best1 = 6110;
    final static int best2 = 15780;
    final static int best3 = 538;
    final static int best4 = 22249;
    final static int best5 = 21282;
    final static int best6 = 42029;
    final static int best7 = 50788;
    final static int best8 = 107217;
    final static int best9 = 8806;
    final static int best10 = 224094;

    final static String fileUsed = file10;
    final static int bestUsed = best10;

    private static final String FILENAME = "C:\\Users\\pvaln\\OneDrive\\Documenti\\SUPSI\\TerzoAnno\\SecondoSemestre\\Algoritmi\\Coppa\\risultati.txt";

    public static double getError(int approx) {
        double sub = approx - bestUsed;
        double div = sub / bestUsed;
        return div * 100;
    }

    public static int getDistance(City[] cities) {
        int distance = 0;
        for (int i = 0; i < cities.length; i++) {
            City starting = cities[i];
            City destination;
            if (i + 1 < cities.length) {
                destination = cities[i + 1];
            } else {
                destination = cities[0];
            }
            distance += starting.getDistanceCity(destination);
        }
        return distance;
    }


    public static void main(String[] args) {
        //ArrayList with cities
        ArrayList<City> cities;

        //Class loader
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //TSP reader
        TSPReader tspReader = new TSPReader();
        cities = tspReader.readFile(classloader.getResourceAsStream(fileUsed));

        //Distances
        int[][] distances;

        //TSP nearest neighbor
        TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();

        //2 opt
        TSP2Opt tsp2Opt = new TSP2Opt();

        //Simulated annealing

        long SEED;
        int cont = 0;

        int temperature;
        double alpha;

        if (cities != null) {
            distances = TSPMatrixDistances.getMatrixDistances(cities);

            TSPSimulatedAnnealing tspSimulatedAnnealing = new TSPSimulatedAnnealing();

            City[] nearestNeighborTour = tspNearestNeighbour.nearestNeighbor(distances, cities);
            System.out.println("Best distance after nearest neighbor: " + getDistance(nearestNeighborTour));
            System.out.println("Error: " + getError(getDistance(nearestNeighborTour)) + "%");

            City[] twoOptTour = tsp2Opt.twoOpt(nearestNeighborTour);
            System.out.println("Best distance after 2opt: " + getDistance(twoOptTour));
            System.out.println("Error: " + getError(getDistance(twoOptTour)) + "%");

            while (cont < 3) {

                SEED = System.currentTimeMillis();
                Random rand = new Random(SEED);

                temperature = rand.nextInt(150 - 100 + 1) + 100;
                alpha = 0.90 + (1 - 0.9) * rand.nextDouble();
                City[] simulatedAnnealingTour = tspSimulatedAnnealing.simulatedAnnealing(twoOptTour, temperature, alpha, rand);
                System.out.println("Best distance after simulated annealing: " + getDistance(simulatedAnnealingTour));
                System.out.println("Error: " + getError(getDistance(simulatedAnnealingTour)) + "%");
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
                printWriter.printf("File: %s \n", fileUsed);
                printWriter.printf("Best: %d \n", bestUsed);
                printWriter.printf("Best distance calculated: %d \n", getDistance(simulatedAnnealingTour));
                printWriter.printf("Error is: %f%s \n", getError(getDistance(simulatedAnnealingTour)), "%");
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
