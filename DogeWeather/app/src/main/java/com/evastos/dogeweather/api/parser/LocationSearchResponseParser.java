package com.evastos.dogeweather.api.parser;

import com.evastos.dogeweather.api.model.LocationSearch;

/**
 * The Class LocationSearchResponseParser.
 *
 * @see com.evastos.dogeweather.api.parser.ResponseParser
 *
 * @author Eva
 */
public class LocationSearchResponseParser extends ResponseParser<LocationSearch> {

    public LocationSearchResponseParser() {
    }

    @Override
	public LocationSearch parseResponse(Object response) {
		return getGson().fromJson(response.toString(), LocationSearch.class);
	}

}
