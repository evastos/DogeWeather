package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class ErrorResponseData.
 *
 * @author Eva
 */
public class ErrorResponseData {

    @SerializedName(Const.ERROR)
    private List<Error> errorList;

    public List<Error> getErrorList() {
        return errorList;
    }
}
