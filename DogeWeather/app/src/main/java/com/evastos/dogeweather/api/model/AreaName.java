package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AreaName.
 *
 * @author Eva
 */
public class AreaName {

    @SerializedName(Const.VALUE)
    private String value;

    public AreaName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
