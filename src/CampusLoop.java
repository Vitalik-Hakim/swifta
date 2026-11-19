import java.time.LocalDateTime;
import java.util.List;

public class CampusLoop implements Route {
    private String routeId;
    private String name;
    private List<Stop> stops;

    public CampusLoop(String routeId, String name, List<Stop> stops) {
        this.routeId = routeId;
        this.name = name;
        this.stops = stops;
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
        return 0.0;
    }

    public boolean isBookingOpen(LocalDateTime now, LocalDateTime departure) {
        return now.isBefore(departure);
    }
}
