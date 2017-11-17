package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.types.TheaterLayout;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TheaterListConverterTest extends TestCase {
    public void testConvert() throws Exception {

        List<String> lines = new LinkedList<>();
        lines.add("1,1,1,1,1,1,1,1,1,1");
        lines.add("2,2,2,2,2,2,2,2,2,2");
        lines.add("1,1,1,1,1,1,1,1,1,1");

        TheaterListConverter theaterListConverter = new TheaterListConverter();
        TheaterLayout theaterLayout = theaterListConverter.convert(lines);

        assertEquals(3, theaterLayout.getRows().size());
    }

}