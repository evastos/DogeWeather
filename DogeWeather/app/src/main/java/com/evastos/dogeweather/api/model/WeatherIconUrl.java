package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class WeatherIconUrl.
 *
 * @author Eva
 */
public class WeatherIconUrl {

    @SerializedName(Const.VALUE)
    private String value;

    public WeatherIconUrl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
