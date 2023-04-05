package nl.civity.orion.contextbroker.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    /**
     * Must be an ISO8601 compliant string of the UTC timestamp
     * @param dateTime
     */
    public static String dateTimeToString(ZonedDateTime dateTime) {
        return dateTime
                .withZoneSameInstant( ZoneId.of("UTC") )
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }
}
