package com.evastos.dogeweather.api.model;

/**
 * The Enum ResponseFormat.
 *
 * Represents enum for response format.
 *
 * @author Eva
 */
public enum ResponseFormat {

    JSON("json"), XML("xml"), CSV("csv");

    private String formatString;

    ResponseFormat(String formatString) {
        this.formatString = formatString;
    }

    @Override
    public String toString() {
        return this.formatString;
    }
}
