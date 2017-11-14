package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.converters.TheaterConverter;
import industries.compulsive.homework.walmart.helpers.RowHelper;
import industries.compulsive.homework.walmart.types.Row;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TheaterListConverter implements TheaterConverter<List<String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TheaterListConverter.class);

    public TheaterLayout convert(List<String> theaterRows) {
        List<Row> rows = new LinkedList<>();
        int lineNumber = 0;

        for (String theaterRow : theaterRows) {
            lineNumber++;
            rows.add(new Row(RowHelper.toAlphabetic(lineNumber - 1),
                    RowHelper.convertValuesToSeats(RowHelper.toAlphabetic(lineNumber-1),
                            Arrays.stream(theaterRow.split(","))
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList()))));

        }

        return new TheaterLayout(rows);
    }
}
