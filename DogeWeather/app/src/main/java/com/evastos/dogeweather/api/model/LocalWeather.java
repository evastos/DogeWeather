package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class LocalWeather.
 *
 * @author Eva
 */
public class LocalWeather {

    @SerializedName(Const.DATA)
    private LocalWeatherData data;

    public LocalWeather(LocalWeatherData data) {
        this.data = data;
    }

    private LocalWeatherData getData() {
        return data;

    }

    public CurrentCondition getCurrentCondition() {
        return getData().getCurrentConditionList().get(0);
    }

    public List<Weather> getWeatherList() {
        return getData().getWeatherList();
    }

    public Weather getWeatherToday() {
        return getWeatherList().get(0);
    }
}
