package industries.compulsive.homework.walmart;

import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.types.SeatAssignment;
import industries.compulsive.homework.walmart.types.TheaterLayout;

import java.util.List;

public interface SeatSelector {
    List<SeatAssignment> assignSeats(TheaterLayout theaterLayout, List<Reservation> reservationList);
}
