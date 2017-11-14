package industries.compulsive.homework.walmart.helpers;

import industries.compulsive.homework.walmart.types.Seat;

import java.util.ArrayList;
import java.util.List;

public class RowHelper {
    public static List<Seat> convertValuesToSeats(String rowName, List<Integer> seatValues) {
        List<Seat> seats = new ArrayList<>(seatValues.size());
        for (int i = 0; i < seatValues.size(); i++) {
            seats.add(new Seat(rowName, i + 1, seatValues.get(i)));
        }

        return seats;
    }

    public static String toAlphabetic(int i) {
        if (i < 0) {
            return "-" + toAlphabetic(-i - 1);
        }

        int quot = i / 26;
        int rem = i % 26;
        char letter = (char) ((int) 'A' + rem);
        if (quot == 0) {
            return "" + letter;
        } else {
            return toAlphabetic(quot - 1) + letter;
        }
    }
}
