public class Stop {
    private String stopId;
    private String name;

    public Stop(String stopId, String name) {
        this.stopId = stopId;
        this.name = name;
    }

    public String getStopId() {
        return stopId;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
