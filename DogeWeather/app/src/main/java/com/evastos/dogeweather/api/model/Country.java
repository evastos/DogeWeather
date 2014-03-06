package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Country.
 *
 * @author Eva
 */
public class Country {

    @SerializedName(Const.VALUE)
    private String value;

    public Country(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
