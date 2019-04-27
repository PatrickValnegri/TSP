package TSP.algorithms;

import TSP.main;
import TSP.models.City;
import TSP.optimizators.TSP2Opt;
import TSP.utility.Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TSPSimulatedAnnealing {

    //private static long SEED;
    private long SEED;
    private double temp;
    private double initTemp;
    private double alpha;
    private Random random;

    public TSPSimulatedAnnealing(long SEED, double temp, double alpha, Random random) {
        this.SEED = SEED;
        this.temp = temp;
        this.alpha = alpha;
        this.random = random;
        this.initTemp = temp;
    }

    public City[] simulatedAnnealing(City[] cities) {
        City[] current = cities;
        City[] best = current;
        Random rand = random;

        //temp = rand.nextInt(150 - 100 + 1) + 100;
        //alpha = 0.90 + (1 - 0.9) * rand.nextDouble();

        //Best distance after simulated annealing: 8856
        //Error: 0.5677946854417443%
        //Temperature: 150
        //Alpha: 0.9637245980190913
        //SEED: 1556101793451

        initTemp = temp;

        int bestL = Utilities.getTotalDistance(best);
        int currentL;
        int candidateL;
        int delta;

        Instant start = Instant.now();
        Duration max = Duration.ofSeconds(174);

        int a = 0;
        while (start.plus(max).isAfter(Instant.now())) {

            for (int i = 0; i < 100; i++) {
                int[] distanceBackup = main.positions.clone();
                a++;
                City[] next = doubleBridge(current, rand);
                City[] candidate = TSP2Opt.twoOpt(next);

                currentL = Utilities.getTotalDistance(current);
                candidateL = Utilities.getTotalDistance(candidate);
                delta = candidateL - currentL;

                if (delta < 0) {
                    current = candidate;
                    currentL = candidateL;

                    if (currentL < bestL)
                        best = current.clone();
                    bestL = currentL;

                    //} else if (rand.nextDouble() < (Math.pow(Math.E,(-((double)delta)/temp)))) {
                } else if (rand.nextDouble() < getProbability(candidate, current, temp)) {
                    current = candidate.clone();
                } else {
                    //lo scambio del 2opt e doublebridge non ha portato guadagni ripristino posizioni
                    main.setPositions(distanceBackup);
                }

            }
            temp *= alpha;

        }
        System.out.println("Passati 3 min, fine simulated");
        System.out.println("Cicli: " + a);
        System.out.println("Duplicati? " + hasDuplicateCities(best));

        printInfo(best, SEED, alpha, initTemp);


        return best;
    }

    private double getProbability(City[] candidate, City[] current, double temp) {
        double delta = (double) (Utilities.getTotalDistance(candidate) - Utilities.getTotalDistance(current));
        double x = Math.exp(-delta / temp);
        return x;
    }

    private City[] doubleBridge(City[] tour,Random random) {
        Random rand = random;
        int[] randPos = new int[4];

        do {
            for (int i = 0; i < randPos.length; i++)
                randPos[i] = rand.nextInt(tour.length);
        } while (hasDuplicate(randPos));

        Arrays.sort(randPos);
        int a = randPos[0];
        int b = randPos[1];
        int c = randPos[2];
        int d = randPos[3];

        int index = 0;
        City[] doubleBridge = tour.clone();

        for (int i = 0; i <= a; i++) {
            doubleBridge[index] = tour[i];
            main.positions[doubleBridge[index].getId()] = index;
            index++;
        }

        for (int i = c + 1; i <= d; i++) {
            doubleBridge[index] = tour[i];
            main.positions[doubleBridge[index].getId()] = index;
            index++;
        }

        for (int i = b + 1; i <= c; i++) {
            doubleBridge[index] = tour[i];
            main.positions[doubleBridge[index].getId()] = index;
            index++;
        }

        for (int i = a + 1; i <= b; i++) {
            doubleBridge[index] = tour[i];
            main.positions[doubleBridge[index].getId()] = index;
            index++;
        }

        for (int i = d + 1; i < tour.length; i++) {
            doubleBridge[index] = tour[i];
            main.positions[doubleBridge[index].getId()] = index;
            index++;
        }

        //System.out.println("Index: " + index);
        //System.out.println("Length: " + (doubleBridge.length));
        //System.out.println(hasDuplicateCities(doubleBridge));

        return doubleBridge;
    }

    public boolean hasDuplicate(int[] items) {
        Set<Integer> appeared = new HashSet<Integer>();
        for (int item : items) {
            if (!appeared.add(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDuplicateCities(City[] items) {
        Set<City> appeared = new HashSet<City>();
        for (City city : items) {
            if (!appeared.add(city)) {
                return true;
            }
        }
        return false;
    }

    private void printInfo(City[] simulatedAnnealingTour, long SEED, double alpha, double initTemp) {
        System.out.println("Best distance after simulated annealing: " + Utilities.getTotalDistance(simulatedAnnealingTour));
        System.out.println("Error: " + Utilities.getError(Utilities.getTotalDistance(simulatedAnnealingTour)) + "%");
        System.out.println("Temperature: " + initTemp);
        System.out.println("Alpha: " + alpha);
        System.out.println("SEED: " + SEED);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(Utilities.FILENAME, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("File: %s \n", Utilities.fileTSP);
        printWriter.printf("Best: %d \n", Utilities.bestKnown);
        printWriter.printf("Best distance calculated: %d \n", Utilities.getTotalDistance(simulatedAnnealingTour));
        printWriter.printf("Error is: %f%s \n", Utilities.getError(Utilities.getTotalDistance(simulatedAnnealingTour)), "%");
        printWriter.printf("Temperature: %f \n", initTemp);
        printWriter.printf("Alpha: %f \n", alpha);
        printWriter.printf("SEED: %d \n", SEED);
        printWriter.printf("\n");
        printWriter.close();
    }

}
