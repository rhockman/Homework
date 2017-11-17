package industries.compulsive.homework.walmart.types;

import java.util.List;

public class SeatingQueryResult {
    private int partyCountSeated;
    private int seatedQualitySum;

    /**
     * Index of the left-most element in this SeatingQueryResult
     */
    private int leftBound;

    /**
     * Index of the right-most element in this SeatingQueryResult
     */
    private int rightBound;
    private List<Seat> candidateSeats;
    private Row row;


    public int getPartyCountSeated() {
        return partyCountSeated;
    }

    public int getSeatedQualitySum() {
        return seatedQualitySum;
    }

    public List<Seat> getCandidateSeats() {
        return candidateSeats;
    }

    public int getLeftBound() {
        return leftBound;
    }


    public int getRightBound() {
        return rightBound;
    }

    public Row getRow() {
        return row;
    }

    public SeatingQueryResult(Row row, int partyCountSeated, int seatedQualitySum, List<Seat>
            candidateSeats, int leftBound, int rightBound) {
        this.row = row;
        this.partyCountSeated = partyCountSeated;
        this.seatedQualitySum = seatedQualitySum;
        this.candidateSeats = candidateSeats;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

}
