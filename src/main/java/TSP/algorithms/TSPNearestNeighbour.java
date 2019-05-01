package TSP.algorithms;

import TSP.main;
import TSP.models.City;
import TSP.models.Solver;

public class TSPNearestNeighbour {

    private int numberOfNodes;
    private Solver solver;

    public TSPNearestNeighbour(Solver solver) {
        this.solver = solver;
    }

    public City[] nearestNeighbor(City[] cities) {
        numberOfNodes = cities.length;
        City nn[] = new City[numberOfNodes];
        int k = 0;

        int firstCity = 0;
        cities[firstCity].setVisited(true);
        nn[k] = cities[firstCity];
        k++;

        int nextCity = firstCity;
        int element;
        int min;
        boolean minFlag = false;
        int count = 0;
        int totCost = 0;

        //System.out.println("First city: " + cities[firstCity].getId() + "\t");

        while (count < numberOfNodes) {

            //città da visitare
            element = cities[nextCity].getId();
            min = Integer.MAX_VALUE;

            //Controllo la distanza minima fra la distanza visitata e tutte le altre
            for (int i = 0; i < numberOfNodes; i++) {

                //controllo di non confrontare la distanza fra la stessa città
                //controllo che la città non si ancora stata visitata
                if (i != element && !cities[i].isVisited()) {

                    //se la distanza minima è maggiore della distanza con l'attuale città la setto come nuova minima
                    if (min > solver.getDistances()[element][i]) {
                        min = solver.getDistances()[element][i];

                        //prossima città da visitare
                        nextCity = i;
                        //quando trovo la distanza minima setto minFlag = true
                        minFlag = true;
                    }
                }
            }

            if (minFlag) {
                //System.out.println(nextCity + "\t");
                cities[nextCity].setVisited(true);
                totCost += min;

                nn[k] = cities[nextCity];
                k++;

                minFlag = false;
                continue;
            }

            count++;
        }

        //totCost += distances[nextCity][firstCity];

        //reset delle città visitate
        for (City c : cities) {
            c.setVisited(false);
        }

        //Aggiorno posizioni città dopo nearest neighbor
        for (int i = 0; i < cities.length; i++)
            solver.getPositions()[nn[i].getId()] = i;

        return nn;
    }

}
