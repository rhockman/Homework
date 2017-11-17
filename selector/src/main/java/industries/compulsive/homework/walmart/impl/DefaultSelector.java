package industries.compulsive.homework.walmart.impl;


import industries.compulsive.homework.walmart.SeatSelector;
import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.types.Row;
import industries.compulsive.homework.walmart.types.SeatingQueryResult;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultSelector implements SeatSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSelector.class);

    public void assignSeats(TheaterLayout theaterLayout, List<Reservation> reservationList) {
        Collections.sort(reservationList, Comparator.comparing(Reservation::getSeatQuantity).reversed());

        for (Reservation reservation : reservationList) {
            int toBeSeated = reservation.getSeatQuantity();

            while (toBeSeated > 0) {
                SeatingQueryResult maxResult = null;
                int maxQuality = 0;
                int currentQuality;

                for (Row row : theaterLayout.getRows()) {
                    List<SeatingQueryResult> seatingQueryResults = row.querySeating(toBeSeated);
                    for (SeatingQueryResult result : seatingQueryResults) {
                        if (result.getPartyCountSeated() < reservation.getSeatQuantity()) {
                            //If the entire party doesn't fit, reduce the quality of all seats by one for comparison.
                            currentQuality = result.getSeatedQualitySum() - result.getPartyCountSeated();
                        } else {
                            currentQuality = result.getSeatedQualitySum();
                        }

                        if (maxQuality < currentQuality) {
                            maxResult = result;
                            maxQuality = currentQuality;
                        }
                    }
                }

                if (maxResult != null) {
                    maxResult.getRow().assignReservation(maxResult, reservation);
                    toBeSeated -= maxResult.getPartyCountSeated();
                }

                if(maxResult == null || maxQuality == 0){
                    //No more seats available in the theater
                    break;
                }
            }
        }
    }
}
