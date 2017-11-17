package industries.compulsive.homework.walmart.types;

import industries.compulsive.homework.walmart.exceptions.InvalidSeatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Seat implements Comparable<Seat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Seat.class);

    private String name;
    private String rowName;
    private int column;

    private int quality;
    private Reservation reservation;

    public String getName() {
        return name;
    }

    public int getQuality() {
        return quality;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(final Reservation reservation) {
        this.reservation = reservation;
        reservation.assignSeat(this);
    }

    public Seat(final String rowName, int column, final int quality) {
        this.name = String.format("%s%d", rowName, column);
        this.rowName = rowName;
        this.column = column;
        this.quality = quality;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Seat seat = (Seat) o;

        return name.equals(seat.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    @Override
    public int compareTo(Seat o) {
        int result = 0;

        final Pattern pattern = Pattern.compile("^(\\w+)(\\d+)$");

        final Matcher thisMatcher = pattern.matcher(getName());
        final Matcher otherMatcher = pattern.matcher(o.getName());

        if (thisMatcher.find() && otherMatcher.find()) {

            if (getRowName().compareTo(o.getRowName()) == 0) {
                result = Integer.compare(getColumn(), o.getColumn());
            } else {
                if (getRowName().length() < o.getRowName().length()) {
                    result = -1;
                } else {
                    result = thisMatcher.group(1).compareTo(otherMatcher.group(1));
                }
            }
        } else {
            throw new InvalidSeatException(String.format("Could not parse one or more of the seats during " +
                    "Seat::compareTo.  Inputs are '%s' and '%s", getName(), o.getName()));
        }

        return result;
    }

    public String getRowName() {
        return rowName;
    }

    public int getColumn() {
        return column;
    }
}
