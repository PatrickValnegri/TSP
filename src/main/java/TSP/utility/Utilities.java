package TSP.utility;

import TSP.main;
import TSP.models.City;
import TSP.models.Solver;

import java.io.FileWriter;
import java.io.IOException;

public class Utilities {
    final static String fileTSP1 = "ch130.tsp";
    final static String fileTSP2 = "d198.tsp";
    final static String fileTSP3 = "eil76.tsp";
    final static String fileTSP4 = "fl1577.tsp";
    final static String fileTSP5 = "kroA100.tsp";
    final static String fileTSP6 = "lin318.tsp";
    final static String fileTSP7 = "pcb442.tsp";
    final static String fileTSP8 = "pr439.tsp";
    final static String fileTSP9 = "rat783.tsp";
    final static String fileTSP10 = "u1060.tsp";

    final static String fileName1 = "ch130";
    final static String fileName2 = "d198";
    final static String fileName3 = "eil76";
    final static String fileName4 = "fl1577";
    final static String fileName5 = "kroA100";
    final static String fileName6 = "lin318";
    final static String fileName7 = "pcb442";
    final static String fileName8 = "pr439";
    final static String fileName9 = "rat783";
    final static String fileName10 = "u1060";

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

    //public final static String fileTSP = fileTSP2;
    //public final static String fileName = fileName2;
    //public final static int bestKnown = best2;

    public static final String FILENAME = "C:\\Users\\pvaln\\OneDrive\\Documenti\\SUPSI\\TerzoAnno\\SecondoSemestre\\Algoritmi\\Coppa\\risultati.txt";
    public static final String DIRPATH = "C:\\Users\\pvaln\\OneDrive\\Documenti\\SUPSI\\TerzoAnno\\SecondoSemestre\\Algoritmi\\Progetto\\Soluzioni";

    public static double getError(int approx, int bestKnown) {
        double sub = approx - bestKnown;
        double div = sub / bestKnown;
        return div * 100;
    }

    public static int getTotalDistance(City[] cities, Solver solver) {
        int distance = 0;
        for (int i = 0; i < cities.length; i++) {
            City starting = cities[i];
            City destination;
            if (i + 1 < cities.length) {
                destination = cities[i + 1];
            } else {
                destination = cities[0];
            }
            distance += getDistanceBetweenCities(starting, destination, solver);
            //distance += starting.getDistanceCity(destination);
        }
        return distance;
    }


    public static int getDistanceBetweenCities(City a, City b, Solver solver) {
        int[][] distances = solver.getDistances();
        return distances[a.getId()][b.getId()];
    }

    public static void writeSolutions(String fileName, int bestKnown, City[] tour) {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME : " + fileName + ".opt.tour").append("\n");
        sb.append("COMMENT : Optimum tour for " + fileName + ".tsp (" + bestKnown + ")").append("\n");
        sb.append("TYPE : TOUR").append("\n");
        sb.append("DIMENSION : " + tour.length).append("\n");
        sb.append("TOUR_SECTION").append("\n");
        for (City c: tour) {
            sb.append(c.getId() + 1).append("\n");
        }
        sb.append("-1").append("\n");
        sb.append("EOF");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(DIRPATH + "\\" + fileName + ".opt.tour");
            fileWriter.write(sb.toString());
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
