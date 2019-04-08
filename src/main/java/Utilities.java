public class Utilities {

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


    public static double getError(int approx) {
        double sub = approx - bestUsed;
        double div = sub / bestUsed;
        return div * 100;
    }

    public static int getTotalDistance(City[] cities) {
        int distance = 0;
        for (int i = 0; i < cities.length; i++) {
            City starting = cities[i];
            City destination;
            if (i + 1 < cities.length) {
                destination = cities[i + 1];
            } else {
                destination = cities[0];
            }
            distance += getDistanceBetweenCities(starting, destination);
        }
        return distance;
    }

    public static int getDistanceBetweenCities(City a, City b) {
        int[][] distances = main.getDistances();
        return distances[a.getId()][b.getId()];
    }




}
