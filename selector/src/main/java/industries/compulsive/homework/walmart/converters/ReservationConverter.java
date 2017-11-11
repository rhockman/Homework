package industries.compulsive.homework.walmart.converters;

import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.exceptions.InvalidReservationException;

import java.util.List;

public interface ReservationConverter<T> {
    List<Reservation> convert(T reservationsType) throws InvalidReservationException;
}
