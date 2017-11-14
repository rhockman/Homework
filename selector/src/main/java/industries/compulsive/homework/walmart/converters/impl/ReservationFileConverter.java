package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.converters.ReservationConverter;
import industries.compulsive.homework.walmart.exceptions.InvalidReservationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationFileConverter implements ReservationConverter<File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationFileConverter.class);

    public List<Reservation> convert(File reservationsFile) throws InvalidReservationException {
        final List<Reservation> reservations = new LinkedList<>();
        long lineNumber = 0;

        try(final Scanner scanner = new Scanner(reservationsFile)) {
            final Pattern validReservation = Pattern.compile("^(?<reservationNumber>R\\d{3}) (?<seatQuantity>\\d+)$");
            String line;

            while(scanner.hasNextLine()) {
                lineNumber++;
                line = scanner.nextLine();
                final Matcher matcher = validReservation.matcher(line);

                if(matcher.find()) {
                    reservations.add(new Reservation(
                            matcher.group("reservationNumber"),
                            matcher.group("seatQuantity")));

                } else {
                    throw new InvalidReservationException(String.format("Invalid Reservation on line %d", lineNumber));
                }
            }
            scanner.close();
        } catch (IOException ioex) {
            LOGGER.error("Error accessing reservations file", ioex);
        }

        return reservations;
    }
}
