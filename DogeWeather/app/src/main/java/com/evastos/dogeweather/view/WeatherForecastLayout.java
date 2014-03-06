package com.evastos.dogeweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.api.model.Weather;
import com.evastos.dogeweather.utils.DateUtils;
import com.evastos.dogeweather.utils.Units;
import com.evastos.dogeweather.utils.WeatherUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;

/**
 * The Class WeatherForecastLayout.
 *
 * Layout for daily weather forecast.
 *
 * @author Eva
 *
 */
public class WeatherForecastLayout extends LinearLayout {

    private Weather mWeather;

    private TextView mTextViewDate;

    private TextView mTextViewTemperatureMin;

    private TextView mTextViewTemperatureMax;

    private ImageView mImageViewWeatherIcon;

    public WeatherForecastLayout(Context context) {
        this(context, null, 0);
    }

    public WeatherForecastLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherForecastLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        mTextViewDate = (TextView) findViewById(R.id.text_view_date);
        mTextViewTemperatureMin = (TextView) findViewById(R.id.text_view_temperature_min);
        mTextViewTemperatureMax = (TextView) findViewById(R.id.text_view_temperature_max);
        mImageViewWeatherIcon = (ImageView) findViewById(R.id.image_view_weather_icon);
    }

    /**
     * Sets weather forecast for a date. Formats weather using units.
     *
     * @see com.evastos.dogeweather.api.model.Weather
     * @see com.evastos.dogeweather.utils.Units
     *
     * @param weather the weather
     * @param units units
     */
    public void setWeatherForecast(final Weather weather, final Units units) {
        mWeather = weather;
        Date date = DateUtils.parseString(weather.getDate());
        if (date != null) {
            mTextViewDate.setText(DateUtils.getDayOfWeek(date));
        }
        mTextViewTemperatureMin.setText(WeatherUtils.formatTemperature(weather.getTempMinC(),
                weather.getTempMinF(), units));
        mTextViewTemperatureMax.setText(WeatherUtils.formatTemperature(weather.getTempMaxC(),
                weather.getTempMaxF(), units));
        ImageLoader.getInstance().displayImage(weather.getWeatherIconUrl(),
                mImageViewWeatherIcon);
    }


    /**
     * Converts weather parameters to units.
     *
     * @see com.evastos.dogeweather.utils.Units
     *
     * @param units new units
     */
    public void setUnits(final Units units) {
        if (mWeather != null) {
            mTextViewTemperatureMin.setText(WeatherUtils.formatTemperature(mWeather.getTempMinC(),
                    mWeather.getTempMinF(), units));
            mTextViewTemperatureMax.setText(WeatherUtils.formatTemperature(mWeather.getTempMaxC(),
                    mWeather.getTempMaxF(), units));
        }
    }

}
