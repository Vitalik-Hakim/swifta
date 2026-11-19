import java.time.LocalDateTime;
import java.util.List;

public class EventRoute implements Route {
    private String routeId;
    private String name;
    private List<Stop> stops;
    private String eventName;
    private int closingMinutes;

    public EventRoute(String routeId, String name, List<Stop> stops, String eventName, int closingMinutes) {
        this.routeId = routeId;
        this.name = name;
        this.stops = stops;
        this.eventName = eventName;
        this.closingMinutes = closingMinutes;
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
        LocalDateTime closingTime = departure.minusMinutes(closingMinutes);
        return now.isBefore(closingTime);
    }
}
