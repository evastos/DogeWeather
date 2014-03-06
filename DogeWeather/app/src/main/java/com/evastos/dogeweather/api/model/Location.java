package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class Location.
 *
 * @author Eva
 */
public class Location {

    @SerializedName(Const.AREA_NAME)
    private List<AreaName> areaNameList;
    @SerializedName(Const.COUNTRY)
    private List<Country> countryList;
    @SerializedName(Const.REGION)
    private List<Region> regionList;
    @SerializedName(Const.WEATHER_URL)
    private List<WeatherUrl> weatherUrlList;
    @SerializedName(Const.LATITUDE)
    private float latitude;
    @SerializedName(Const.LONGITUDE)
    private float longitude;
    @SerializedName(Const.POPULATION)
    private long population;

    private List<AreaName> getAreaNameList() {
        return areaNameList;
    }

    private List<Country> getCountryList() {
        return countryList;
    }

    private List<Region> getRegionList() {
        return regionList;
    }

    private List<WeatherUrl> getWeatherUrlList() {
        return weatherUrlList;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public long getPopulation() {
        return population;
    }

    public String getAreaName() {
        return getAreaNameList().get(0).getValue();
    }

    public String getCountry() {
        return getCountryList().get(0).getValue();
    }

    public String getWeatherUrl() {
        return getWeatherUrlList().get(0).getValue();
    }
}
