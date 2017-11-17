package industries.compulsive.homework.walmart;

import industries.compulsive.homework.walmart.converters.impl.TheaterListConverter;
import industries.compulsive.homework.walmart.impl.DefaultSelector;
import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.types.Seat;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class DefaultSelectorTest extends TestCase {
    private TheaterLayout buildTheaterLayout() {
        TheaterListConverter theaterListConverter = new TheaterListConverter();
        List<String> rowStrings = new ArrayList<>(3);
        rowStrings.add("1,1,1,2,1,1,1");
        rowStrings.add("2,3,3,3,3,3,2");
        rowStrings.add("2,2,2,4,2,2,2");

        return theaterListConverter.convert(rowStrings);
    }

    public void testPicksMostValuableSeatFirst() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("R001", 1));

        DefaultSelector defaultSelector = new DefaultSelector();
        defaultSelector.assignSeats(buildTheaterLayout(), reservations);

        assertEquals(4, reservations.get(0).getSeatAssignments().get(0).getQuality());
    }

    public void testPicksMostValuableSeatsFirst() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("R001", 5));

        DefaultSelector defaultSelector = new DefaultSelector();
        defaultSelector.assignSeats(buildTheaterLayout(), reservations);

        assertEquals(3, reservations.get(0).getSeatAssignments().get(0).getQuality());
        assertEquals(15, reservations.get(0).getSeatAssignments().stream().mapToInt(Seat::getQuality).sum());
    }

    public void testReevaluatesRows() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("R001", 5));
        reservations.add(new Reservation("R002", 5));

        DefaultSelector defaultSelector = new DefaultSelector();
        defaultSelector.assignSeats(buildTheaterLayout(), reservations);

        assertEquals(2, reservations.get(1).getSeatAssignments().get(0).getQuality());
        assertEquals(12, reservations.get(1).getSeatAssignments().stream().mapToInt(Seat::getQuality).sum());
    }

    public void testSplitsParties() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("R001", 8));

        DefaultSelector defaultSelector = new DefaultSelector();
        defaultSelector.assignSeats(buildTheaterLayout(), reservations);

        assertEquals(23, reservations.get(0).getSeatAssignments().stream().mapToInt(Seat::getQuality).sum());
    }

    public void testDoesNotSeatWhenFullHouse() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("R001", 21));
        reservations.add(new Reservation("R002", 1));

        DefaultSelector defaultSelector = new DefaultSelector();
        defaultSelector.assignSeats(buildTheaterLayout(), reservations);

        assertEquals(21, reservations.get(0).getSeatAssignments().size());
        assertEquals(0, reservations.get(1).getSeatAssignments().size());


    }

}
