package be.boomkwekerij.plant.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatDate(DateTime date, String pattern) {
        return formatter(pattern).print(date);
    }

    private static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormat.forPattern(pattern);
    }
}
