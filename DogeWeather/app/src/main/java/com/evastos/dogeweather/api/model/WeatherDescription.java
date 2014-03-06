package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class WeatherDescription.
 *
 * @author Eva
 */
public class WeatherDescription {

    @SerializedName(Const.VALUE)
    private String value;

    public WeatherDescription(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "WeatherDescription{" +
                "value='" + value + '\'' +
                '}';
    }
}
