package industries.compulsive.homework.walmart.types;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RowTest extends TestCase {

    private Row buildSmallRow() {
        List<Seat> seatList = new ArrayList<>();
        seatList.add(new Seat("S", 1, 4));
        seatList.add(new Seat("S", 2, 5));
        seatList.add(new Seat("S", 3, 4));
        Row row = new Row("S", seatList);

        return row;
    }

    private Row buildMediumRow() {
        List<Seat> seatList = new ArrayList<>();
        seatList.add(new Seat("M", 1, 3));
        seatList.add(new Seat("M", 2, 4));
        seatList.add(new Seat("M", 3, 4));
        seatList.add(new Seat("M", 4, 5));
        seatList.add(new Seat("M", 5, 6));
        seatList.add(new Seat("M", 6, 5));
        seatList.add(new Seat("M", 7, 4));
        seatList.add(new Seat("M", 8, 4));
        seatList.add(new Seat("M", 9, 3));
        Row row = new Row("M", seatList);

        return row;
    }

    @Test
    public void testSetsConstructorParameters() throws Exception {
        Row row = buildSmallRow();
        assertEquals("S", row.getRowName());
        assertEquals(3, row.getSeats().length);
    }

    @Test
    public void testUsesMemoizedSeatingQueries() {
        Row row = buildSmallRow();

        List<SeatingQueryResult> firstResult = row.querySeating(1);
        List<SeatingQueryResult> secondResult = row.querySeating(1);

        //Make sure these are the same object, not that they have the same value.  Object equality means that the
        // memoization method is working correctly and subsequent calls with the same reservation size are O(1).
        assertTrue(firstResult == secondResult);
    }

    @Test
    public void testSeatsWhoItCan() {
        Row row = buildSmallRow();

        List<SeatingQueryResult> seatingQueryResults = row.querySeating(5);

        assertEquals(1, seatingQueryResults.size());
        assertEquals(3, seatingQueryResults.get(0).getCandidateSeats().size());
        assertEquals(13, seatingQueryResults.get(0).getSeatedQualitySum());
    }

    @Test
    public void testSeatsNoOneWhenZeroReservation() {
        Row row = buildSmallRow();
        Reservation reservation = new Reservation("R001", 0);

        List<SeatingQueryResult> zeroResult = row.querySeating(0);
        assertEquals(0, zeroResult.get(0).getCandidateSeats().size());

    }

    @Test public void testPicksTheMiddleSeatFirst() {
        Row row = buildMediumRow();
        Reservation reservation = new Reservation("R001", 1);

        List<SeatingQueryResult> seatingQueryResults = row.querySeating(reservation.getSeatQuantity());
        assertEquals(6, seatingQueryResults.get(0).getSeatedQualitySum());

    }

    @Test
    public void testMaintainsMemoWhenZeroReservation() {
        Row row = buildSmallRow();
        Reservation reservation = new Reservation("R001", 0);

        List<SeatingQueryResult> zeroResult = row.querySeating(0);
        List<SeatingQueryResult> firstResult = row.querySeating(1);

        row.assignReservation(zeroResult.get(0), reservation);

        List<SeatingQueryResult> secondResult = row.querySeating(1);
        assertEquals(2, row.memoizedSeatingQueries.size());

        assertTrue(firstResult == secondResult);
    }

    @Test
    public void testClearsMemoWhenNonZeroReservation() {
        Row row = buildSmallRow();
        Reservation reservation = new Reservation("R001", 1);

        List<SeatingQueryResult> queryResults = row.querySeating(1);

        assertEquals(1, row.memoizedSeatingQueries.size());
        row.assignReservation(queryResults.get(0), reservation);
        assertEquals(0, row.memoizedSeatingQueries.size());
    }

    @Test
    public void testAssignProducesSeating() {
        Row row = buildSmallRow();
        Reservation reservation = new Reservation("R001", 1);

        List<SeatingQueryResult> seatingQueryResults = row.querySeating(reservation.getSeatQuantity());
        row.assignReservation(seatingQueryResults.get(0), reservation);

        assertEquals(5, reservation.getSeatAssignments().stream().mapToInt(Seat::getQuality).sum());
    }

    @Test
    public void testProducesSecondaryChunks() {
        Row row = buildMediumRow();
        Reservation pristineReservation = new Reservation("R001", 4);
        Reservation dirtyReservation = new Reservation("R002", 3);

        List<SeatingQueryResult> pristineResults = row.querySeating(pristineReservation.getSeatQuantity());
        assertEquals(1, pristineResults.size());

        row.assignReservation(pristineResults.get(0), pristineReservation);

        List<SeatingQueryResult> dirtyResults = row.querySeating(dirtyReservation.getSeatQuantity());
        assertEquals(2, dirtyResults.size());
        assertEquals(7, dirtyResults.get(0).getSeatedQualitySum());
        assertEquals(11, dirtyResults.get(1).getSeatedQualitySum());
    }

    @Test
    public void testProducesSecondaryChunksWithCenteredResults() {
        Row row = buildMediumRow();
        Reservation pristineReservation = new Reservation("R001", 3);
        Reservation dirtyReservation = new Reservation("R002", 3);

        List<SeatingQueryResult> pristineResults = row.querySeating(pristineReservation.getSeatQuantity());
        assertEquals(1, pristineResults.size());

        row.assignReservation(pristineResults.get(0), pristineReservation);

        List<SeatingQueryResult> dirtyResults = row.querySeating(dirtyReservation.getSeatQuantity());
        assertEquals(2, dirtyResults.size());
        assertEquals(11, dirtyResults.get(0).getSeatedQualitySum());
        assertEquals(11, dirtyResults.get(1).getSeatedQualitySum());
    }

    @Test
    public void testUpdatesIndiciesOnMultipleAssignments() {
        Row row = buildMediumRow();
        Reservation firstReservation = new Reservation("R001", 1);
        Reservation secondReservation = new Reservation("R002", 1);
        Reservation thirdReservation = new Reservation("R003", 1);

        List<SeatingQueryResult> firstResults = row.querySeating(firstReservation.getSeatQuantity());
        assertEquals("M5", firstResults.get(0).getCandidateSeats().get(0).getName());
        assertEquals(6, firstResults.get(0).getSeatedQualitySum());
        assertEquals(4, firstResults.get(0).getLeftBound());
        assertEquals(4, firstResults.get(0).getRightBound());
        row.assignReservation(firstResults.get(0), firstReservation);


        List<SeatingQueryResult> secondResults = row.querySeating(secondReservation.getSeatQuantity());
        assertEquals("M4", secondResults.get(0).getCandidateSeats().get(0).getName());
        assertEquals(3, secondResults.get(0).getLeftBound());
        assertEquals(3, secondResults.get(0).getRightBound());
        assertEquals("M6", secondResults.get(1).getCandidateSeats().get(0).getName());
        assertEquals(5, secondResults.get(1).getLeftBound());
        assertEquals(5, secondResults.get(1).getRightBound());

        row.assignReservation(secondResults.get(0), secondReservation);
        assertEquals(row.leftIndex, secondResults.get(0).getLeftBound());
        assertEquals("M4", secondReservation.getSeatAssignments().get(0).getName());

        List<SeatingQueryResult> thirdResults = row.querySeating(thirdReservation.getSeatQuantity());
        row.assignReservation(thirdResults.get(1), thirdReservation);
        assertEquals("M6", thirdReservation.getSeatAssignments().get(0).getName());
        assertEquals(5, thirdResults.get(1).getRightBound());
        assertEquals(row.rightIndex, thirdResults.get(1).getRightBound());
    }
}
