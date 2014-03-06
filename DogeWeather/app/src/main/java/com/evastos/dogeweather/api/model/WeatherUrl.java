package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class WeatherUrl.
 *
 * @author Eva
 */
public class WeatherUrl {

    @SerializedName(Const.VALUE)
    private String value;

    public String getValue() {
        return value;
    }

}
