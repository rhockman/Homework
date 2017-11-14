package industries.compulsive.homework.walmart.types;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Reservation {
    private String reservationNumber;
    private int seatQuantity;
    private Set<Seat> seatAssignments;

    public String getReservationNumber() {
        return reservationNumber;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public Reservation(String reservationNumber, String seatQuantity) {
        this(reservationNumber, Integer.parseInt(seatQuantity));
    }

    public Reservation(String reservationNumber, int seatQuantity) {
        this.reservationNumber = reservationNumber;
        this.seatQuantity = seatQuantity;

        seatAssignments = new HashSet<>(seatQuantity);
    }

    public String toString() {
        return String.format("%s - %d", getReservationNumber(), getSeatQuantity());
    }

    public void assignSeat(Seat seat) {
        seatAssignments.add(seat);
    }

    public List<Seat> getSeatAssignments() {
        return seatAssignments.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
