package industries.compulsive.homework.walmart.types;

import junit.framework.TestCase;

public class SeatTest extends TestCase {
    public void testGetName() throws Exception {
    }

    public void testGetQuality() throws Exception {
    }

    public void testGetReservation() throws Exception {
    }

    public void testSetReservation() throws Exception {
    }

    public void testIsAvailable() throws Exception {
    }

    public void testEquals() throws Exception {
    }

    public void testHashCode() throws Exception {
    }

    public void testCompareTo() throws Exception {
        assertEquals(-1, new Seat("A", 1, 0).compareTo(new Seat("A",2, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("B", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("AA",1, 0)));
        assertEquals(-1, new Seat("A", 2, 0).compareTo(new Seat("B",1, 0)));
    }
}