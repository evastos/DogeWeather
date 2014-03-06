package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class LocationSearchData.
 *
 * @author Eva
 */
public class LocationSearchData {

    @SerializedName(Const.RESULT)
    private List<Location> locationList;

    public List<Location> getLocationList() {
        return locationList;
    }
}
