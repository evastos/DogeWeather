package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class Weather.
 *
 * @author Eva
 */
public class Weather {

    @SerializedName(Const.DATE)
    private String date;
    @SerializedName(Const.PRECIP_MM)
    private float precipitationMM;
    @SerializedName(Const.TEMP_MAX_C)
    private int tempMaxC;
    @SerializedName(Const.TEMP_MAX_F)
    private int tempMaxF;
    @SerializedName(Const.TEMP_MIN_C)
    private int tempMinC;
    @SerializedName(Const.TEMP_MIN_F)
    private int tempMinF;
    @SerializedName(Const.WEATHER_CODE)
    private int weatherCode;
    @SerializedName(Const.WEATHER_DESC)
    private List<WeatherDescription> weatherDescriptionList;
    @SerializedName(Const.WEATHER_ICON_URL)
    private List<WeatherIconUrl> weatherIconUrlList;
    @SerializedName(Const.WIND_DIR_16_POINT)
    private String windDirection16Point;
    @SerializedName(Const.WIND_DIR_DEGREE)
    private int windDirectionDegree;
    @SerializedName(Const.WIND_DIRECTION)
    private String windDirection;
    @SerializedName(Const.WIND_SPEED_KMPH)
    private int windSpeedKmph;
    @SerializedName(Const.WIND_SPEED_MILES)
    private int windSpeedMiles;

    public Weather(String date, float precipitationMM, int tempMaxC, int tempMaxF, int tempMinC, int tempMinF, int weatherCode, List<WeatherDescription> weatherDescriptionList, List<WeatherIconUrl> weatherIconUrlList, String windDirection16Point, int windDirectionDegree, String windDirection, int windSpeedKmph, int windSpeedMiles) {
        this.date = date;
        this.precipitationMM = precipitationMM;
        this.tempMaxC = tempMaxC;
        this.tempMaxF = tempMaxF;
        this.tempMinC = tempMinC;
        this.tempMinF = tempMinF;
        this.weatherCode = weatherCode;
        this.weatherDescriptionList = weatherDescriptionList;
        this.weatherIconUrlList = weatherIconUrlList;
        this.windDirection16Point = windDirection16Point;
        this.windDirectionDegree = windDirectionDegree;
        this.windDirection = windDirection;
        this.windSpeedKmph = windSpeedKmph;
        this.windSpeedMiles = windSpeedMiles;
    }

    public int getWindSpeedMiles() {
        return windSpeedMiles;
    }

    public String getDate() {
        return date;
    }

    public float getPrecipitationMM() {
        return precipitationMM;
    }

    public int getTempMaxC() {
        return tempMaxC;
    }

    public int getTempMaxF() {
        return tempMaxF;
    }

    public int getTempMinC() {
        return tempMinC;
    }

    public int getTempMinF() {
        return tempMinF;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    private List<WeatherDescription> getWeatherDescriptionList() {
        return weatherDescriptionList;
    }

    private List<WeatherIconUrl> getWeatherIconUrlList() {
        return weatherIconUrlList;
    }

    public String getWindDirection16Point() {
        return windDirection16Point;
    }

    public int getWindDirectionDegree() {
        return windDirectionDegree;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public int getWindSpeedKmph() {
        return windSpeedKmph;
    }

    public String getWeatherIconUrl() {
        return getWeatherIconUrlList().get(0).getValue();
    }

    public String getWeatherDescription() {
        return getWeatherDescriptionList().get(0).getValue();
    }

}