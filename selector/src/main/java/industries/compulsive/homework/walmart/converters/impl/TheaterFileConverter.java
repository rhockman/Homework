package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.converters.TheaterConverter;
import industries.compulsive.homework.walmart.helpers.RowHelper;
import industries.compulsive.homework.walmart.types.Row;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TheaterFileConverter implements TheaterConverter<File> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TheaterFileConverter.class);

    public TheaterLayout convert(File theaterFile) {
        List<Row> rows = new LinkedList<>();
        int lineNumber = 0;

        try (final Scanner scanner = new Scanner(theaterFile)) {
            String line;

            try {
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    line = scanner.nextLine();

                    rows.add(new Row(RowHelper.toAlphabetic(lineNumber),
                            RowHelper.convertValuesToSeats(RowHelper.toAlphabetic(lineNumber-1),
                                    Arrays.stream(line.split(","))
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList()))));

                }
            } catch (Throwable t) {
                LOGGER.error("Problem parsing theater layout file on line {}", lineNumber, t);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find the specified theater file {}", theaterFile.getAbsolutePath(), e);
        }

        return new TheaterLayout(rows);
    }
}
