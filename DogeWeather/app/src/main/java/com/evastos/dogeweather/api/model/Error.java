package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Error.
 *
 * @author Eva
 */
public class Error {

    @SerializedName(Const.MSG)
    private String message;

    public String getMessage() {
        return message;
    }
}
