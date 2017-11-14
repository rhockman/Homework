package industries.compulsive.homework.walmart.types;

import java.util.*;

public class Row {
    private String rowName;
    private Seat[] seats;
    private boolean pristine;
    private int leftIndex;
    private int rightIndex;
    private Map<Integer, List<SeatingQueryResult>> memoizedSeatingQueries;

    public String getRowName() {
        return rowName;
    }

    public Row(String rowName, List<Seat> seatList) {
        this.rowName = rowName;
        this.memoizedSeatingQueries = new HashMap<>();
        this.seats = new Seat[seatList.size()];
        seatList.toArray(seats);
        pristine = true;
    }

    public List<SeatingQueryResult> querySeating(int reservationSize) {
        List<SeatingQueryResult> queryResults;

        if (memoizedSeatingQueries.containsKey(reservationSize)) {
            queryResults = memoizedSeatingQueries.get(reservationSize);
        } else {
            if (pristine) {
                if (reservationSize <= seats.length) {
                    //Center the reservation in the row.
                    final int pristineOffset = (seats.length / 2) - (reservationSize / 2);
                    final List<Seat> candidateSeats = new ArrayList<>(reservationSize);
                    int qualitySum = 0;

                    for (int i = 0; i < reservationSize; i++) {
                        candidateSeats.add(seats[i + pristineOffset]);
                        qualitySum += seats[i + pristineOffset].getQuality();
                    }

                    SeatingQueryResult seatingQueryResult =
                            new SeatingQueryResult(this, reservationSize, qualitySum, candidateSeats, pristineOffset,
                                    pristineOffset + reservationSize);

                    queryResults = Collections.singletonList(seatingQueryResult);
                } else {
                    int qualitySum = 0;
                    for (Seat seat : seats) {
                        qualitySum += seat.getQuality();
                    }

                    SeatingQueryResult seatingQueryResult =
                            new SeatingQueryResult(
                                    this,
                                    seats.length,
                                    qualitySum,
                                    Arrays.asList(seats),
                                    0,
                                    seats.length - 1);
                    queryResults = Collections.singletonList(seatingQueryResult);
                }
            } else {
                queryResults = new LinkedList<>();

                //Determine left result:
                int seatsToAssign = Math.min(leftIndex, reservationSize);
                int quality = 0;
                Seat currentSeat;
                final List<Seat> leftSideSeats = new ArrayList<>(seatsToAssign);

                for (int i = 1; i <= seatsToAssign; i++) {
                    currentSeat = seats[leftIndex - i];
                    leftSideSeats.add(currentSeat);
                    quality += currentSeat.getQuality();
                }

                queryResults.add(new SeatingQueryResult(this, seatsToAssign, quality, leftSideSeats, leftIndex -
                        seatsToAssign, rightIndex));

                //Determine right result:
                seatsToAssign = Math.min(reservationSize, seats.length - rightIndex - 1);
                quality = 0;
                final List<Seat> rightSideSeats = new ArrayList<>(seatsToAssign);

                for (int i = 1; i <= seatsToAssign; i++) {
                    currentSeat = seats[rightIndex + i];
                    rightSideSeats.add(currentSeat);
                    quality += currentSeat.getQuality();
                }

                queryResults.add(new SeatingQueryResult(
                        this, seatsToAssign, quality, rightSideSeats, leftIndex, rightIndex + seatsToAssign
                ));
            }
            memoizedSeatingQueries.put(reservationSize, queryResults);
        }

        return queryResults;
    }

    public void assignReservation(final SeatingQueryResult seatingQueryResult, final Reservation reservation) {
        //Since we're mutating this row, we need to update our memoization next time we're queried.
        this.memoizedSeatingQueries = new HashMap<>();

        if (pristine) {
            pristine = false;
            leftIndex = seatingQueryResult.getLeftBound();
            rightIndex = seatingQueryResult.getRightBound();
        } else {
            if (seatingQueryResult.getLeftBound() < leftIndex) {
                leftIndex = seatingQueryResult.getLeftBound();
            } else {
                rightIndex = seatingQueryResult.getRightBound();
            }
        }

        seatingQueryResult.getCandidateSeats().forEach((seat) -> seat.setReservation(reservation));
    }
}
