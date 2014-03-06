package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Region.
 *
 * @author Eva
 */
public class Region {

    @SerializedName(Const.VALUE)
    private String value;

    public Region(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
