package com.evastos.dogeweather.api.model;

import com.evastos.dogeweather.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The Class CurrentCondition.
 *
 * @author Eva
 */
public class CurrentCondition {

    @SerializedName(Const.CLOUD_COVER)
    private int cloudCover;
    @SerializedName(Const.HUMIDITY)
    private int humidity;
    @SerializedName(Const.OBSERVATION_TIME)
    private String observationTime;
    @SerializedName(Const.PRECIP_MM)
    private float precipitationMM;
    @SerializedName(Const.PRESSURE)
    private int pressure;
    @SerializedName(Const.TEMP_C)
    private int tempC;
    @SerializedName(Const.TEMP_F)
    private int tempF;
    @SerializedName(Const.VISIBILITY)
    private int visibility;
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
    @SerializedName(Const.WIND_SPEED_KMPH)
    private int windSpeedKmph;
    @SerializedName(Const.WIND_SPEED_MILES)
    private int windSpeedMiles;

    public CurrentCondition(int cloudCover, int humidity, String observationTime, float precipitationMM, int pressure, int tempC, int tempF, int visibility, int weatherCode, List<WeatherDescription> weatherDescriptionList, List<WeatherIconUrl> weatherIconUrlList, String windDirection16Point, int windDirectionDegree,  int windSpeedKmph, int windSpeedMiles) {
        this.cloudCover = cloudCover;
        this.humidity = humidity;
        this.observationTime = observationTime;
        this.precipitationMM = precipitationMM;
        this.pressure = pressure;
        this.tempC = tempC;
        this.tempF = tempF;
        this.visibility = visibility;
        this.weatherCode = weatherCode;
        this.weatherDescriptionList = weatherDescriptionList;
        this.weatherIconUrlList = weatherIconUrlList;
        this.windDirection16Point = windDirection16Point;
        this.windDirectionDegree = windDirectionDegree;
        this.windSpeedKmph = windSpeedKmph;
        this.windSpeedMiles = windSpeedMiles;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public float getPrecipitationMM() {
        return precipitationMM;
    }

    public int getPressure() {
        return pressure;
    }

    public int getTempC() {
        return tempC;
    }

    public int getTempF() {
        return tempF;
    }

    public int getVisibility() {
        return visibility;
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

    public int getWindSpeedKmph() {
        return windSpeedKmph;
    }

    public int getWindSpeedMiles() {
        return windSpeedMiles;
    }

    public String getWeatherDescription() {
        return getWeatherDescriptionList().get(0).getValue();
    }

    public String getWeatherIconUrl() {
        return getWeatherIconUrlList().get(0).getValue();
    }
}
