package TSP.algorithms;

import TSP.main;
import TSP.models.City;

import java.util.ArrayList;

public class TSPMinimumSpanningTree {

    private int[][] distances;
    private City[] cities;

    public TSPMinimumSpanningTree(City[] cities) {
        this.distances = main.getDistances();
        this.cities = cities;
    }

    public void MST() {
        ArrayList<City> tree = new ArrayList<>();

        City first = cities[0];
        cities[first.getId()].setVisited(true);
        tree.add(first);

        City nearest;
        City node;

        for (int i = 0; i < cities.length - 1; i++) {
            City[] nodes = new City[tree.size()];
            nodes = tree.toArray(nodes);

            nearest = new City();
            node = new City();

            int min = Integer.MAX_VALUE;
            int tmp;

            Solution sol;

            for (City c : nodes) {
                sol = searchDistMin(c);
                tmp = sol.getMin();

                if (tmp < min) {
                    min = tmp;
                    nearest = cities[sol.getIndex()];
                    node = c;
                }
            }

            //node.addCandidate(nearest);
            //nearest.addCandidate(node);
            node.addCandidate(nearest.getId());
            nearest.addCandidate(node.getId());
            tree.add(nearest);
            cities[nearest.getId()].setVisited(true);
        }

        for (City c : cities) {
            c.setVisited(false);
        }
    }

    public Solution searchDistMin(City city) {
        int len = distances.length;
        int min = Integer.MAX_VALUE;
        int tmp;
        Solution sol = new Solution();

        for (int i = 0; i < len; i++) {
            tmp = distances[city.getId()][i];

            if (tmp < min && cities[i].isVisited() == false) {
                min = tmp;
                sol.setMin(min);
                sol.setIndex(i);
            }
        }

        //return di indice e nodo
        return sol;
    }

    private class Solution {
        private int min;
        private int index;

        public int getMin() {
            return min;
        }

        public int getIndex() {
            return index;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

}


