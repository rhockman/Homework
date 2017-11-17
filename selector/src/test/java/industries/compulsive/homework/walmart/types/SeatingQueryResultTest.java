package industries.compulsive.homework.walmart.types;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SeatingQueryResultTest extends TestCase {
    private SeatingQueryResult buildStandardSeatingQueryResult() {

        List<Seat> seats = new ArrayList<Seat>(3);
        seats.add(new Seat("A", 1, 1));
        seats.add(new Seat("A", 2, 1));
        seats.add(new Seat("A", 3, 1));

        Row row = new Row("A", seats);

        SeatingQueryResult seatingQueryResult = new SeatingQueryResult(
                row,
                3,
                3,
                seats,
                0,
                2
        );

        return seatingQueryResult;
    }

    public void testGetPartyCountSeated() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals(3, seatingQueryResult.getPartyCountSeated());
    }

    public void testGetSeatedQualitySum() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals(3, seatingQueryResult.getSeatedQualitySum());
    }

    public void testGetCandidateSeats() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals(3, seatingQueryResult.getCandidateSeats().size());
    }

    public void testGetLeftBound() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals(0, seatingQueryResult.getLeftBound());
    }

    public void testGetRightBound() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals(2, seatingQueryResult.getRightBound());
    }

    public void testGetRow() throws Exception {
        SeatingQueryResult seatingQueryResult = buildStandardSeatingQueryResult();
        assertEquals("A", seatingQueryResult.getRow().getRowName());
    }

}