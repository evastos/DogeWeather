package com.evastos.dogeweather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateUtils.
 *
 * @author Eva
 */
public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat("EEE");

    /**
     * Parses String to a Date using "yyy-MM-dd" format.
     *
     * @see java.util.Date
     *
     * @param dateString date String
     * @return date if successful, else {@code null}
     */
    public static Date parseString(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets day of week using date.
     *
     * @see java.util.Date
     *
     * @param date the date
     * @return String representing day of week, e.g. Mon, Tue, Sat
     */
    public static String getDayOfWeek(Date date) {
        return DAY_OF_WEEK_FORMAT.format(date);
    }

    /**
     * Formats date to a String using format "yyyy-MM-dd"
     *
     * @see java.util.Date
     *
     * @param date the date
     * @return formatted date
     */
    public static String formatsDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
