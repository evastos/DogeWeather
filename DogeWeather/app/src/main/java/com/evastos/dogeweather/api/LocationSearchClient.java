package com.evastos.dogeweather.api;

import android.content.Context;

import com.evastos.dogeweather.api.model.LocationSearchRequest;
import com.evastos.dogeweather.api.parser.LocationSearchResponseParser;

/**
 * The Class LocalWeatherClient.
 *
 * @see com.evastos.dogeweather.api.BaseClient
 *
 * @author Eva
 */
public class LocationSearchClient extends BaseClient {

    /* API URL */
    private static final String API_URL = BASE_API_URL + "/search.ashx";

    public LocationSearchClient(Context context) {
        super(context, API_URL, HttpMethod.GET, new LocationSearchResponseParser());
    }

    public void getLocations(LocationSearchRequest locationSearchRequest) {
        runClient(locationSearchRequest);
    }

    @Override
    protected boolean isClientSuccess(String response) {
        return response != null;
    }
}
