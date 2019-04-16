package TSP.models;

import java.util.TreeSet;

public class City implements Comparable<City>{
    private int id;
    private double latitude;
    private double longitude;
    private boolean isVisited;
    private TreeSet<City> candidateList;

    public City(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isVisited = false;
        candidateList = new TreeSet<>();
    }

    public City() {
    }

    public int getDistanceCity(City city) {
        double x2 = city.getLatitude();
        double x1 = this.getLatitude();

        double y2 = city.getLongitude();
        double y1 = this.getLongitude();

        return (int) (0.5 + (Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public TreeSet<City> getCandidateList() {
        return candidateList;
    }

    public void addCandidate(City city) {
        this.candidateList.add(city);
    }

    @Override
    public int compareTo(City o) {
        if (this.id == o.id)
            return 0;

        return 1;
    }
}
