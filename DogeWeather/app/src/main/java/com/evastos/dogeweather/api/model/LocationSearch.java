package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class LocationSearch.
 *
 * @author Eva
 */
public class LocationSearch {

    @SerializedName(Const.SEARCH_API)
    private LocationSearchData data;

    public LocationSearchData getData() {
        return data;
    }

    public List<Location> getLocationList() {
        return getData().getLocationList();
    }
}
