package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.exceptions.InvalidReservationException;
import industries.compulsive.homework.walmart.types.Reservation;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReservationFileConverterTest extends TestCase {
    public void testThrowsExceptionWithInvalidFile() throws Exception {
        try {
            ReservationFileConverter reservationFileConverter = new ReservationFileConverter();
            reservationFileConverter.convert(new File("a fake file"));
            fail();
        } catch (IOException ioex) {
            assertNotNull(ioex);
        }
    }

    public void testMatchesValidReservations() {
        String reservationContents = String.format("R001 2%nR002 3%nR003 4");

        ReservationFileConverter reservationFileConverter = new ReservationFileConverter();
        List<Reservation> reservations = reservationFileConverter.convert(reservationContents);

        assertEquals(3, reservations.size());
        assertEquals("R001", reservations.get(0).getReservationNumber());
        assertEquals("R002", reservations.get(1).getReservationNumber());
        assertEquals("R003", reservations.get(2).getReservationNumber());
    }

    public void testThrowsExceptionOnInvalidReservations() {
        String reservationContents = String.format("R001 2%ns002 3%nR003 4");

        ReservationFileConverter reservationFileConverter = new ReservationFileConverter();
        try {
            reservationFileConverter.convert(reservationContents);
            fail();
        } catch (InvalidReservationException ire) {
            assertNotNull(ire);
        }

    }
}