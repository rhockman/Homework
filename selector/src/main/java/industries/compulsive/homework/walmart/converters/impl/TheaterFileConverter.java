package industries.compulsive.homework.walmart.converters.impl;

import industries.compulsive.homework.walmart.converters.TheaterConverter;
import industries.compulsive.homework.walmart.types.TheaterLayout;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TheaterFileConverter implements TheaterConverter<File> {

    public TheaterLayout convert(File theaterFile) throws Throwable {
        List<String> lines = Arrays.asList(FileUtils.readFileToString(theaterFile, "UTF-8").split("\\r?\\n"));
        return new TheaterListConverter().convert(lines);
    }
}
