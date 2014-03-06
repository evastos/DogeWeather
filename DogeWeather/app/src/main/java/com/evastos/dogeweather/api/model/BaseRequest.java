package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.application.DogeWeatherApp;

/**
 * The Class AreaName.
 *
 * @author Eva
 */
public class BaseRequest {

    private static final String API_KEY = DogeWeatherApp.getWorldWeatherOnlineApiKey();

    private static final ResponseFormat RESPONSE_FORMAT = ResponseFormat.JSON;

    private static final String YES = "yes";

    private static final String NO = "no";

    private int numOfDays = 2; // default

    private LocationQuery locationQuery;

    protected BaseRequest(LocationQuery locationQuery) {
        this.locationQuery = locationQuery;

    }

    protected BaseRequest(int numOfDays, LocationQuery locationQuery) {
        this.numOfDays = numOfDays;
        this.locationQuery = locationQuery;

    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public LocationQuery getLocationQuery() {
        return locationQuery;
    }

    public void setLocationQuery(LocationQuery locationQuery) {
        this.locationQuery = locationQuery;
    }

    /**
     * Converts boolean value to URL param.
     *
     * @param value
     * @return parameter
     */
    protected String convertBoolean(boolean value) {
        if (value == true) {
            return YES;
        } else {
            return NO;
        }
    }

    /**
     * Builds URL using parameters from BaseRequest.
     *
     * @param baseUrl the URL
     * @return URL with parameters
     */
    public String addParamsToUrl(String baseUrl) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?key=").append(API_KEY).append("&q=").append(locationQuery.toString()).append("&format=").append(RESPONSE_FORMAT.toString());
        if (numOfDays > 0 && numOfDays != 2) {
            stringBuilder.append("&num_of_days=").append(numOfDays);
        }
        return stringBuilder.toString();
    }
}
