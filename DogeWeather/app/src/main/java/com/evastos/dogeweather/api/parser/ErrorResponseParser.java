package com.evastos.dogeweather.api.parser;

import com.evastos.dogeweather.api.model.ErrorResponse;

/**
 * The Class ErrorResponseParser.
 *
 * @see com.evastos.dogeweather.api.parser.ResponseParser
 *
 * @author Eva
 */
public class ErrorResponseParser extends ResponseParser<ErrorResponse> {

    public ErrorResponseParser() {
    }

    @Override
	public ErrorResponse parseResponse(Object response) {
		return getGson().fromJson(response.toString(), ErrorResponse.class);
	}

}
