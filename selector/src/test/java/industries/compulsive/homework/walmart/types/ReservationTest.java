package industries.compulsive.homework.walmart.types;

import junit.framework.TestCase;

public class ReservationTest extends TestCase {

    public void testParsesSeatQuantityString() {
        Reservation reservation = new Reservation("R001", "3");

        assertEquals(3, reservation.getSeatQuantity());
    }

    public void testAssignsConstructorParameters() {
        Reservation reservation = new Reservation("R001", 3);

        assertEquals("R001", reservation.getReservationNumber());
        assertEquals(3, reservation.getSeatQuantity());
        assertEquals(0, reservation.getSeatAssignments().size());
    }

    public void testAssignSeat() throws Exception {
        Reservation reservation = new Reservation("R001", 3);

        Seat seat = new Seat("A", 1,1);

        reservation.assignSeat(seat);

        assertEquals(1, reservation.getSeatAssignments().size());
        assertEquals(seat, reservation.getSeatAssignments().get(0));
    }


}