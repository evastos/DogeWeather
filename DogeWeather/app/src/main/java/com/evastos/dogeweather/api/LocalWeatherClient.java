package com.evastos.dogeweather.api;

import android.content.Context;

import com.evastos.dogeweather.api.model.LocalWeatherRequest;
import com.evastos.dogeweather.api.parser.LocalWeatherResponseParser;

/**
 * The Class LocalWeatherClient.
 *
 * @see com.evastos.dogeweather.api.BaseClient
 *
 * @author Eva
 */
public class LocalWeatherClient extends BaseClient {

    /* API URL */
    private static final String API_URL = BASE_API_URL + "/weather.ashx";

    public LocalWeatherClient(Context context) {
        super(context, API_URL, HttpMethod.GET, new LocalWeatherResponseParser());
    }

    public void getWeatherData(LocalWeatherRequest localWeatherRequest) {
        runClient(localWeatherRequest);
    }
}
