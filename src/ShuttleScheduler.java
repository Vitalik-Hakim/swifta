import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShuttleScheduler {
    private Map<String, Booking> bookings;
    private List<Shuttle> shuttles;

    public ShuttleScheduler() {
        bookings = new HashMap<>();
        shuttles = new ArrayList<>();
    }

    public void addShuttle(Shuttle sh) {
        shuttles.add(sh);
    }

    public List<Shuttle> getShuttles() {
        return shuttles;
    }

    public Booking book(Student s, Shuttle sh, Stop dropOff) {
        if (!sh.getRoute().isBookingOpen(LocalDateTime.now(), sh.getDepartureTime())) {
            throw new IllegalStateException("Bookings are closed for shuttle " + sh.getShuttleId());
        }

        for (Booking b : bookings.values()) {
            if (b.getStudent().equals(s) && b.getShuttle() == sh && !b.getStatus().equals("CANCELLED")) {
                throw new IllegalStateException(s.getName() + " already has a booking on shuttle " + sh.getShuttleId());
            }
        }

        String id = "B" + (bookings.size() + 1);
        Booking booking = new Booking(id, s, sh, dropOff);

        if (sh.hasFreeSeat()) {
            sh.addPassenger(s);
        } else {
            sh.joinWaitlist(s);
            booking.setStatus("WAITLISTED");
        }

        bookings.put(id, booking);
        return booking;
    }

    public void cancel(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("No booking with ID " + bookingId);
        }
        if (booking.getStatus().equals("CANCELLED")) {
            throw new IllegalStateException("Booking " + bookingId + " is already cancelled");
        }

        Shuttle sh = booking.getShuttle();
        if (booking.getStatus().equals("CONFIRMED")) {
            Student promoted = sh.removePassenger(booking.getStudent());
            if (promoted != null) {
                // Find the promoted student's waitlisted booking and confirm it
                for (Booking b : bookings.values()) {
                    if (b.getShuttle() == sh && b.getStudent().equals(promoted)
                            && b.getStatus().equals("WAITLISTED")) {
                        b.setStatus("CONFIRMED");
                    }
                }
            }
        } else {
            sh.getWaitlist().remove(booking.getStudent());
        }
        booking.cancel();
    }

    public List<Student> getPassengers(Shuttle sh) {
        return sh.getPassengers();
    }

    public void saveToFile(String path) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(path));
        for (Booking b : bookings.values()) {
            Student s = b.getStudent();
            out.println(b.getBookingId() + "," + s.getStudentId() + "," + s.getName() + "," + s.getEmail()
                    + "," + b.getShuttle().getShuttleId() + "," + b.getDropOffStop().getStopId()
                    + "," + b.getStatus());
        }
        out.close();
    }

    public void loadFromFile(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String line = in.readLine();
        while (line != null) {
            String[] parts = line.split(",");
            Student s = new Student(parts[1], parts[2], parts[3]);
            Shuttle sh = findShuttle(parts[4]);
            Stop stop = findStop(sh, parts[5]);
            String status = parts[6];

            Booking booking = new Booking(parts[0], s, sh, stop);
            booking.setStatus(status);
            if (status.equals("CONFIRMED")) {
                sh.addPassenger(s);
            } else if (status.equals("WAITLISTED")) {
                sh.joinWaitlist(s);
            }
            bookings.put(parts[0], booking);
            line = in.readLine();
        }
        in.close();
    }

    private Shuttle findShuttle(String shuttleId) {
        for (Shuttle sh : shuttles) {
            if (sh.getShuttleId().equals(shuttleId)) {
                return sh;
            }
        }
        throw new IllegalArgumentException("Unknown shuttle " + shuttleId);
    }

    private Stop findStop(Shuttle sh, String stopId) {
        for (Stop stop : sh.getRoute().getStops()) {
            if (stop.getStopId().equals(stopId)) {
                return stop;
            }
        }
        throw new IllegalArgumentException("Unknown stop " + stopId);
    }
}
