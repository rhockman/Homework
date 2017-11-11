package industries.compulsive.homework.walmart.types;

public class Reservation {
        private String reservationNumber;
        private int seatQuantity;

    public String getReservationNumber() {
        return reservationNumber;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public Reservation(String reservationNumber, String seatQuantity) {
        this.reservationNumber = reservationNumber;
        this.seatQuantity = Integer.parseInt(seatQuantity);
    }

    public Reservation(String reservationNumber, int seatQuantity) {
        this.reservationNumber = reservationNumber;
        this.seatQuantity = seatQuantity;
    }

    public String toString() {
        return String.format("%s - %d", getReservationNumber(), getSeatQuantity());
    }

}
