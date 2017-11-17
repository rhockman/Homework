package industries.compulsive.homework.walmart.helpers;

import industries.compulsive.homework.walmart.types.Seat;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RowHelperTest extends TestCase {

    public void testConvertValuesToSeats() {
        List<Integer> seatValues = new ArrayList<>(Arrays.asList(1,1,2,3,2,1,1));

        List<Seat> seats = RowHelper.convertValuesToSeats("A", seatValues);

        assertEquals(7, seats.size());
        assertEquals(1, seats.get(0).getQuality());
        assertEquals(3, seats.get(3).getQuality());
        assertEquals("A2", seats.get(1).getName());
    }

    public void testToAlphabetic() {
        assertEquals("A", RowHelper.toAlphabetic(0));
        assertEquals("AA", RowHelper.toAlphabetic(26));
        assertEquals("-A", RowHelper.toAlphabetic(-1));

    }


}