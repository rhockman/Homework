package industries.compulsive.homework.walmart.types;

import industries.compulsive.homework.walmart.exceptions.InvalidSeatException;
import junit.framework.TestCase;

public class SeatTest extends TestCase {
    public void testSetReservation() throws Exception {
        Seat seat = new Seat("A1", 1, 1);
        Reservation reservation = new Reservation("R001", 1);

        seat.setReservation(reservation);

        assertEquals(reservation, seat.getReservation());
    }

    public void testCompareTo() throws Exception {
        assertEquals(-1, new Seat("A", 1, 0).compareTo(new Seat("A",2, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("B", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("B",1, 0)));
    }

    //I prefer this syntax, but my version of JUnit doesn't seem to support it
    // @Test(expected =  InvalidSeatException.class)
    public void testCompareToInvalidRegex() throws Exception {
        Seat goodSeat = new Seat("A", 1, 0);
        Seat badSeat = new Seat("A-", 1, 0);

        try {
            goodSeat.compareTo(badSeat);
            fail();
        } catch (InvalidSeatException ise) {
            assertNotNull(ise);
        }
    }

    public void testEquals() throws Exception {
        Seat seatOne = new Seat("A", 1, 1);
        Seat seatTwo = new Seat("A", 1, 2);

        assertTrue(seatOne.equals(seatTwo));
    }

    public void testNotEquals() throws Exception {
        Seat seatOne = new Seat("A", 1, 1);
        Seat seatTwo = new Seat("A", 2, 1);

        assertFalse(seatOne.equals(seatTwo));
    }
}