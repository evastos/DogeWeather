package com.evastos.dogeweather.api.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class ResponseParser.
 */
public abstract class ResponseParser<T> {

    private Gson mGson;

	/**
	 * Parses the response.
	 * 
	 * @param response
	 *            the response
	 * @return the object
	 */
	public abstract T parseResponse(Object response);

    protected Gson getGson() {
        if (mGson == null) {
            GsonBuilder builder = new GsonBuilder();
            mGson = builder.create();
        }
        return mGson;
    }

}
