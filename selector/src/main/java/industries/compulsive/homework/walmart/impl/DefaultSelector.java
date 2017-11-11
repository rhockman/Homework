package industries.compulsive.homework.walmart.impl;


import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.types.SeatAssignment;
import industries.compulsive.homework.walmart.SeatSelector;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class DefaultSelector implements SeatSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSelector.class);

    public List<SeatAssignment> assignSeats(TheaterLayout theaterLayout, List<Reservation> reservationList) {

        return new LinkedList<>();

    }

}
