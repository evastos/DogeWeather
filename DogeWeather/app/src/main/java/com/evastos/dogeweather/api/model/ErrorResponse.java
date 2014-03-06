package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ErrorResponse.
 *
 * @author Eva
 */
public class ErrorResponse {

	@SerializedName(Const.DATA)
    private ErrorResponseData data;

    public ErrorResponseData getData() {
        return data;
    }

    public String getMessage() {
        return getData().getErrorList().get(0).getMessage();
    }
}
