package com.evastos.dogeweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.api.model.Weather;
import com.evastos.dogeweather.utils.Units;

import java.util.List;

/**
 * The Class WeatherFOrecastHolder.
 *
 * Layout holder for daily weather forecast.
 *
 * @author Eva
 *
 * @see com.evastos.dogeweather.view.WeatherForecastLayout
 *
 */
public class WeatherForecastHolder extends LinearLayout {

    public WeatherForecastHolder(Context context) {
        this(context, null, 0);
    }

    public WeatherForecastHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherForecastHolder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setWeatherForecastList(List<Weather> weatherList, final Units units) {
        removeAllViews();
        setWeightSum(weatherList.size()-1);

        final LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            for (Weather weather : weatherList) {
                if (weatherList.indexOf(weather) > 0) {
                    WeatherForecastLayout weatherForecastLayout = (WeatherForecastLayout)
                            inflater.inflate(R.layout.layout_weather_forecast, this, false);
                    WeatherForecastLayout.LayoutParams params =
                            new WeatherForecastLayout.LayoutParams(0,
                                    WeatherForecastLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    addView(weatherForecastLayout, params);
                    weatherForecastLayout.setWeatherForecast(weather, units);
                }
            }
        }
    }

    /**
     * Invokes conversion of weather params to units for each forecast layout it holds.
     *
     * @see com.evastos.dogeweather.utils.Units
     *
     * @param units new units
     */
    public void setUnits(final Units units) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof WeatherForecastLayout) {
                ((WeatherForecastLayout) child).setUnits(units);
            }
        }
    }

}
