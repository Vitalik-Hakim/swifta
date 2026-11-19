public class Booking {
    private String bookingId;
    private Student student;
    private Shuttle shuttle;
    private Stop dropOffStop;
    private double fare;
    private String status; 

    public Booking(String bookingId, Student student, Shuttle shuttle, Stop dropOffStop) {
        this.bookingId = bookingId;
        this.student = student;
        this.shuttle = shuttle;
        this.dropOffStop = dropOffStop;
        this.fare = shuttle.getRoute().calculateFare(dropOffStop);
        this.status = "CONFIRMED";
    }

    public void cancel() {
        status = "CANCELLED";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFare() {
        return fare;
    }

    public String getStatus() {
        return status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Student getStudent() {
        return student;
    }

    public Shuttle getShuttle() {
        return shuttle;
    }

    public Stop getDropOffStop() {
        return dropOffStop;
    }

    public String toString() {
        return bookingId + " " + status + " " + student.getName() + " on " + shuttle.getShuttleId()
                + " to " + dropOffStop.getName() + " GHS " + fare;
    }
}
