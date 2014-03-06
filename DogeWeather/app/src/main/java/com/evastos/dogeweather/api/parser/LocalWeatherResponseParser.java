package com.evastos.dogeweather.api.parser;

import com.evastos.dogeweather.api.model.LocalWeather;

/**
 * The Class LocalWeatherResponseParser.
 *
 * @see com.evastos.dogeweather.api.parser.ResponseParser
 *
 * @author Eva
 */
public class LocalWeatherResponseParser extends ResponseParser<LocalWeather> {

    public LocalWeatherResponseParser() {
    }

    @Override
	public LocalWeather parseResponse(Object response) {
		return getGson().fromJson(response.toString(), LocalWeather.class);
	}

}
