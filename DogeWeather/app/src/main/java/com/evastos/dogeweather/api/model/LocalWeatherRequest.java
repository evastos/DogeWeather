package com.evastos.dogeweather.api.model;

/**
 * The Class LocalWeatherRequest.
 *
 * Client request for LocalWeatherClient.
 *
 * @author Eva
 */
public class LocalWeatherRequest extends BaseRequest {

    private Date date;
    private boolean includeLocation = false;

    public LocalWeatherRequest(LocationQuery locationQuery) {
        super(locationQuery);
    }

    public LocalWeatherRequest(int numOfDays, LocationQuery locationQuery) {
        super(numOfDays, locationQuery);
    }

    public LocalWeatherRequest(int numOfDays, LocationQuery locationQuery, Date date, boolean includeLocation) {
        super(numOfDays, locationQuery);
        this.date = date;
        this.includeLocation = includeLocation;
    }

    @Override
    public String addParamsToUrl(String baseUrl) {
        StringBuilder stringBuilder = new StringBuilder(super.addParamsToUrl(baseUrl));
        if (date != null) {
            stringBuilder.append("&date=").append(date.toString());
        }
        if (includeLocation) {
            stringBuilder.append("&includeLocation=").append(convertBoolean(includeLocation));
        }
        return stringBuilder.toString();
    }
}
