package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class LocationSearchRequest.
 *
 * Client request for LocationSearchClient.
 *
 * @author Eva
 */
public class LocationSearchRequest extends BaseRequest {

    @SerializedName(Const.POPULAR)
    private boolean popular = false;

    public boolean hasPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public LocationSearchRequest(LocationQuery locationQuery) {
        super(locationQuery);
    }

    public LocationSearchRequest(LocationQuery locationQuery, boolean popular) {
        super(locationQuery);
        this.popular = popular;
    }

    @Override
    public String addParamsToUrl(String baseUrl) {
        if (popular == true) {
            StringBuilder builder = new StringBuilder(super.addParamsToUrl(baseUrl));
            builder.append("&popular=").append(convertBoolean(popular));
            return builder.toString();
        } else {
            return super.addParamsToUrl(baseUrl);
        }

    }
}
