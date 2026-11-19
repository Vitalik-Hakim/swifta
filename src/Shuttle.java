import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Shuttle {
    private String shuttleId;
    private int capacity;
    private LocalDateTime departureTime;
    private Route route;
    private List<Student> passengers;
    private Queue<Student> waitlist;

    public Shuttle(String shuttleId, int capacity, LocalDateTime departureTime, Route route) {
        this.shuttleId = shuttleId;
        this.capacity = capacity;
        this.departureTime = departureTime;
        this.route = route;
        this.passengers = new ArrayList<>();
        this.waitlist = new LinkedList<>();
    }

    public boolean hasFreeSeat() {
        return passengers.size() < capacity;
    }

    public void addPassenger(Student s) {
        if (!hasFreeSeat()) {
            throw new IllegalStateException("Shuttle " + shuttleId + " is full");
        }
        passengers.add(s);
    }


    public Student removePassenger(Student s) {
        if (!passengers.remove(s)) {
            throw new IllegalArgumentException(s.getName() + " is not on shuttle " + shuttleId);
        }
        Student next = waitlist.poll();
        if (next != null) {
            passengers.add(next);
        }
        return next;
    }

    public void joinWaitlist(Student s) {
        waitlist.add(s);
    }

    public Queue<Student> getWaitlist() {
        return waitlist;
    }

    public List<Student> getPassengers() {
        return passengers;
    }

    public String getShuttleId() {
        return shuttleId;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Route getRoute() {
        return route;
    }

    public String toString() {
        return shuttleId + " - " + route.getName() + " at " + departureTime;
    }
}
