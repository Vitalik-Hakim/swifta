import java.time.LocalDateTime;
import java.util.List;

public interface Route {
    String getRouteId();

    String getName();

    List<Stop> getStops();

    double calculateFare(Stop dropOff);

    boolean isBookingOpen(LocalDateTime now, LocalDateTime departure);
}
