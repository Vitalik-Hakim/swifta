import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TownRoute implements Route {
    private String routeId;
    private String name;
    private List<Stop> stops;
    private Map<Stop, Double> fares;

    public TownRoute(String routeId, String name, List<Stop> stops, Map<Stop, Double> fares) {
        this.routeId = routeId;
        this.name = name;
        this.stops = stops;
        this.fares = fares;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getName() {
        return name;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public double calculateFare(Stop dropOff) {
        Double fare = fares.get(dropOff);
        if (fare == null) {
            throw new IllegalArgumentException("No fare for " + dropOff.getName() + " on " + name);
        }
        return fare;
    }

    public boolean isBookingOpen(LocalDateTime now, LocalDateTime departure) {
        return now.isBefore(departure);
    }
}
