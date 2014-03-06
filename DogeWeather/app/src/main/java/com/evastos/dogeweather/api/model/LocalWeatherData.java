package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class LocalWeatherData.
 *
 * @author Eva
 */
public class LocalWeatherData {

    @SerializedName(Const.CURRENT_CONDITION)
    private List<CurrentCondition> currentConditionList;
    @SerializedName(Const.REQUEST)
    private List<Request> requestList;
    @SerializedName(Const.WEATHER)
    private List<Weather> weatherList;

    public LocalWeatherData(List<CurrentCondition> currentConditionList, List<Request> requestList, List<Weather> weatherList) {
        this.currentConditionList = currentConditionList;
        this.requestList = requestList;
        this.weatherList = weatherList;
    }

    public List<CurrentCondition> getCurrentConditionList() {
        return currentConditionList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }
}
