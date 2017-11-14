package industries.compulsive.homework.walmart;

import industries.compulsive.homework.walmart.converters.ReservationConverter;
import industries.compulsive.homework.walmart.converters.TheaterConverter;
import industries.compulsive.homework.walmart.converters.impl.ReservationFileConverter;
import industries.compulsive.homework.walmart.converters.impl.TheaterFileConverter;
import industries.compulsive.homework.walmart.converters.impl.TheaterListConverter;
import industries.compulsive.homework.walmart.impl.DefaultSelector;
import industries.compulsive.homework.walmart.types.Reservation;
import industries.compulsive.homework.walmart.types.Row;
import industries.compulsive.homework.walmart.types.Seat;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cli {
    private final static String INPUT_ARGUMENT = "r";
    private final static String SEARCH_ARGUMENT = "s";
    private final static String THEATER_ARGUMENT = "t";

    private final static Logger LOGGER = LoggerFactory.getLogger(Cli.class);
    private final String reservationFilePath;
    private final String searchAlgorithm;
    private final String theaterFilePath;

    public static void main(String[] args) {
        LOGGER.info("Running main");
        Options options = new Options();

        options.addOption(
                Option.builder(INPUT_ARGUMENT)
                        .hasArg(true)
                        .numberOfArgs(1)
                        .required(true)
                        .valueSeparator('=')
                        .longOpt("reservationFilePath")
                        .desc("The fully qualified path to the input file")
                        .build());

        options.addOption(
                Option.builder(SEARCH_ARGUMENT)
                        .hasArg(true)
                        .numberOfArgs(1)
                        .required(false)
                        .valueSeparator('=')
                        .longOpt("searchAlgorithm")
                        .desc("The search algorithm to use.  Options: ")
                        .build());

        options.addOption(
                Option.builder(THEATER_ARGUMENT)
                        .hasArg(true)
                        .numberOfArgs(1)
                        .required(false)
                        .valueSeparator('=')
                        .longOpt("theater")
                        .desc("A fully qualified path to a theater seat weight file")
                        .build());

        CommandLineParser parser = new DefaultParser();


        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(INPUT_ARGUMENT)) {
                String reservationFilePath = cmd.getOptionValue(INPUT_ARGUMENT);
                String searchAlgorithm = cmd.getOptionValue(SEARCH_ARGUMENT);
                String theaterPath = cmd.getOptionValue(THEATER_ARGUMENT);

                LOGGER.info("reservationFilePath: {}", reservationFilePath);
                LOGGER.info("searchAlgorithm: {}", searchAlgorithm);
                LOGGER.info("theater: {}", theaterPath);

                Cli cli = new Cli(reservationFilePath, searchAlgorithm, theaterPath);
                cli.run();
            } else {
                printHelpAndExit(options);
            }


        } catch (ParseException pe) {
            System.out.printf("There was a problem parsing the command line arguments. %n");
            printHelpAndExit(options);
        }
    }

    public static void printHelpAndExit(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("seatpicker", options);
        System.exit(1);
    }

    public Cli(final String reservationFilePath,
               final String searchAlgorithm,
               final String theatreFilePath) {
        this.reservationFilePath = reservationFilePath;
        this.searchAlgorithm = searchAlgorithm;
        this.theaterFilePath = theatreFilePath;

        Objects.requireNonNull(reservationFilePath);

    }

    private void run() {
        File theaterFile;
        TheaterLayout theaterLayout;

        if (theaterFilePath == null) {
            List<String> lines = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream
                    ("default_theater.csv"))).lines().collect(Collectors.toList());
            final TheaterConverter<List<String>> theaterConverter = new TheaterListConverter();
            theaterLayout = theaterConverter.convert(lines);

        } else {
            theaterFile = new File(theaterFilePath);
            final TheaterConverter<File> theaterConverter = new TheaterFileConverter();
            theaterLayout = theaterConverter.convert(theaterFile);
        }

        for (Row row : theaterLayout.getRows()) {
           LOGGER.info("Row-{}: {}", row.getRowName(), row.toString());
        }

        ReservationConverter<File> reservationConverter = new ReservationFileConverter();
        List<Reservation> reservations = reservationConverter.convert(new File(reservationFilePath));

        reservations.stream().map(Reservation::toString).forEach(LOGGER::info);

        DefaultSelector selector = new DefaultSelector();
        selector.assignSeats(theaterLayout, reservations);

        for(Reservation reservation:reservations){
            String reservationDetails = String.format("%s has seats %s", reservation, reservation.getSeatAssignments()
                    .stream()
                    .map(Seat::getName).collect(Collectors.joining(", ")));

            LOGGER.info("{}", reservationDetails);

        }

    }
}
