import java.util.ArrayList;
import java.util.Random;

public class TSPNearestNeighbour {

    private int numberOfNodes;

    public City[] nearestNeighbor(int distances[][], ArrayList<City> cities) {
        //numero di città da visitare
        numberOfNodes = cities.size();
        City nn[] = new City[cities.size()];
        int k = 0;

        //Parto da un città a caso
        Random random = new Random();
        //int randomAccess = random.nextInt(numberOfNodes);
        int randomAccess = 0;
        int firstCity = randomAccess;

        //la setto come visitata
        cities.get(randomAccess).setVisited(true);
        nn[k] = cities.get(randomAccess);
        k++;

        int nextCity = randomAccess;
        int element;
        int min;
        boolean minFlag = false;
        int count = 0;
        int totCost = 0;

        System.out.println("First city: " + cities.get(randomAccess).getId() + "\t");

        //Finchè non visito tutte le città
        while (count < numberOfNodes) {

            //città da visitare
            element = cities.get(nextCity).getId();
            min = Integer.MAX_VALUE;

            //Controllo la distanza minima fra la distanza visitata e tutte le altre
            for (int i = 0; i < numberOfNodes; i++) {

                //controllo di non confrontare la distanza fra la stessa città
                //controllo che la città non si ancora stata visitata
                if (i != element && !cities.get(i).isVisited()) {

                    //se la distanza minima è maggiore della distanza con l'attuale città la setto come nuova minima
                    if (min > distances[element][i]) {
                        min = distances[element][i];

                        //prossima città da visitare
                        nextCity = i;
                        //quando trovo la distanza minima setto minFlag = true
                        minFlag = true;
                    }
                }
            }

            if (minFlag) {
                System.out.println(nextCity + "\t");
                //setto città come visitata
                cities.get(nextCity).setVisited(true);
                //calcolo del costo totale
                totCost += min;

                nn[k] = cities.get(nextCity);
                k++;

                minFlag = false;
                continue;
            }

            count++;
        }
        //sommo ultima città con quella di partenza per chiudere il cerchio
        totCost += distances[nextCity][firstCity];
        for (City c:cities) {
            c.setVisited(false);
        }
        return nn;
    }

}
