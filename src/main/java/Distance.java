class Distance implements Comparable<Distance>{
    private int distance;
    private City city;

    public Distance(int distance, City city) {
        this.distance = distance;
        this.city = city;
    }

    public int getDistance() {
        return distance;
    }

    public City getCity() {
        return city;
    }

    @Override
    public int compareTo(Distance o) {
        if (this.getCity().getId() == o.getCity().getId())
            return 0;

        return 1;
    }
}