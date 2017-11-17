package industries.compulsive.homework.walmart.converters;

import industries.compulsive.homework.walmart.types.TheaterLayout;

public interface TheaterConverter<T> {
    TheaterLayout convert(T theaterType) throws Throwable;
}
