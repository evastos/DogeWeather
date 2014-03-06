package com.evastos.dogeweather.utils;

/**
 * The Class WeatherUtils.
 *
 * @author Eva
 */
public class WeatherUtils {

    private static final String CELSIUS_DEGREE = "\u2103";

    private static final String FAHRENHEIT_DEGREE = "\u2109";

    private static final String NO_WIND = "Still";

    private static final String NO_PRECIPITATION = "None";

    private static final String KILOMETERS_PER_HOUR = "kph";

    private static final String MILES_PER_HOUR = "mph";

    private static final String MILLIMETERS = "mm";

    private static String formatCelsius(final int temperatureValue) {
        return String.valueOf(temperatureValue) + CELSIUS_DEGREE;
    }

    private static String formatFahrenheit(final int temperatureValue) {
        return String.valueOf(temperatureValue) + FAHRENHEIT_DEGREE;
    }

    /**
     * Formats temperature. Returns description of temperature using units
     *
     * @param celsiusValue temperature value in Celsius degree
     * @param fahrenheitValue temperature value in Fahrenheit degree
     * @param units units
     * @return temperature description using units
     *
     * @see com.evastos.dogeweather.utils.Units
     */
    public static String formatTemperature(final int celsiusValue, final int fahrenheitValue,
                                           final Units units) {
        switch (units) {
            case IMPERIAL:
                return formatFahrenheit(fahrenheitValue);
            case METRIC:
                return formatCelsius(celsiusValue);
            default:
                return NO_WIND;
        }
    }

    private static String formatKph(final int windSpeed) {
        return String.valueOf(windSpeed) + KILOMETERS_PER_HOUR;
    }

    private static String formatMph(final int windSpeed) {
        return String.valueOf(windSpeed) + MILES_PER_HOUR;
    }

    /**
     * Formats wind speed. Returns description of wind parameters using units
     *
     * @param speedInKph wind speed in kph
     * @param speedInMph wind speed in mph
     * @param windDir16Point wind direction in 16 point compass
     * @param units units
     * @return wind description using units
     *
     * @see com.evastos.dogeweather.utils.Units
     */
    public static String formatWindSpeed(final int speedInKph, final int speedInMph,
                                         final String windDir16Point, final Units units) {
        if (speedInKph > 0) {

            switch (units) {
                case IMPERIAL:
                    return formatMph(speedInMph) + " " + windDir16Point;
                case METRIC:
                    return formatKph(speedInKph) + " " + windDir16Point;
                default:
                    return NO_WIND;
            }
        } else {
            return NO_WIND;
        }

    }

    /**
     * Formats precipitation. Returns precipitation description in millimeters
     *
     * @param precipitationInMm precipitation value in millimeters
     * @return precipitation description using millimeters
     */
    public static String formatPrecipitation(final float precipitationInMm) {
        if (precipitationInMm > 0) {
            return String.valueOf(precipitationInMm) + MILLIMETERS;
        } else {
            return NO_PRECIPITATION;
        }
    }
}
