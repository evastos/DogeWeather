package com.evastos.dogeweather.local_weather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evastos.dogeweather.R;
import com.evastos.dogeweather.api.BaseClient;
import com.evastos.dogeweather.api.ClientProgressListener;
import com.evastos.dogeweather.api.ClientResultListener;
import com.evastos.dogeweather.api.LocalWeatherClient;
import com.evastos.dogeweather.api.model.CurrentCondition;
import com.evastos.dogeweather.api.model.LocalWeather;
import com.evastos.dogeweather.api.model.LocalWeatherRequest;
import com.evastos.dogeweather.api.model.LocationQuery;
import com.evastos.dogeweather.api.model.Weather;
import com.evastos.dogeweather.application.BaseActivity;
import com.evastos.dogeweather.utils.Const;
import com.evastos.dogeweather.utils.NetworkUtils;
import com.evastos.dogeweather.utils.Preferences;
import com.evastos.dogeweather.utils.Units;
import com.evastos.dogeweather.utils.WeatherUtils;
import com.evastos.dogeweather.view.WeatherForecastHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * The Class LocalWeatherFragment.
 *
 * @author Eva
 */
public class LocalWeatherFragment extends Fragment implements
        LocalWeatherActivity.OnRefreshListener, LocalWeatherActivity.OnUnitsChangeListener {

    private static final int DAYS_OF_FORECAST = 5;

    private String mLocation;

    private Units mUnits;

    private LocalWeatherClient mLocalWeatherClient;

    private LocalWeather mLocalWeather;

    private TextView mTextViewTemperature;

    private TextView mTextViewTemperatureMin;

    private TextView mTextViewTemperatureMax;

    private TextView mTextViewWeatherDescription;

    private TextView mTextViewObservationTime;

    private ImageView mImageViewWeatherIcon;

    private TextView mTextViewWind;

    private TextView mTextViewPrecipitation;

    private WeatherForecastHolder mWeatherForecastHolder;

    private View mContentView;

    private View mLoadingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_local_weather, container, false);

        mLoadingView = rootView.findViewById(R.id.layout_local_weather_loading);
        mContentView = rootView.findViewById(R.id.layout_local_weather_content);

        mTextViewTemperature = (TextView) rootView.findViewById(R.id.text_view_temperature);
        mTextViewTemperatureMax = (TextView) rootView.findViewById(R.id.text_view_temperature_max);
        mTextViewTemperatureMin = (TextView) rootView.findViewById(R.id.text_view_temperature_min);
        mTextViewWeatherDescription = (TextView) rootView.findViewById(R.id.text_view_weather_description);
        mTextViewObservationTime = (TextView) rootView.findViewById(R.id.text_view_observation_time);
        mImageViewWeatherIcon = (ImageView) rootView.findViewById(R.id.image_view_weather_icon);
        mTextViewWind = (TextView) rootView.findViewById(R.id.text_view_wind);
        mTextViewPrecipitation = (TextView) rootView.findViewById(R.id.text_view_precipitation);
        mWeatherForecastHolder = (WeatherForecastHolder) rootView.findViewById(R.id.weather_forecast_holder);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLocation = getArguments().getString(Const.LOCATION_EXTRA);

        mUnits = Preferences.getInstance(getActivity()).getUnits();

        mLocalWeatherClient = new LocalWeatherClient(getActivity());
        mLocalWeatherClient.setProgressListener(mLocalWeatherProgressListener);
        mLocalWeatherClient.setResultListener(mLocalWeatherResultListener);

        getLocalWeather();
    }

    @Override
    public void onDetach() {
        mLocalWeatherClient.setProgressListener(null);
        mLocalWeatherClient.setResultListener(null);
        super.onDetach();
    }

    private ClientResultListener<LocalWeather> mLocalWeatherResultListener =
            new ClientResultListener<LocalWeather>() {

                @Override
                public void onSuccess(BaseClient client, LocalWeather localWeather) {
                    mLocalWeather = localWeather;
                    if (isAdded()) {
                        setLocalWeather(localWeather);
                    }
                }

                @Override
                public void onError(BaseClient client, String errorMessage) {
                    if (isAdded()) {
                        ((BaseActivity) getActivity()).showErrorDialog(errorMessage);
                    }
                }
            };

    private ClientProgressListener mLocalWeatherProgressListener = new ClientProgressListener() {

        @Override
        public void onStart(BaseClient client) {
            showLoadingIndicator();

        }

        @Override
        public void onFinish(BaseClient client) {
            hideLoadingIndicator();
        }

    };

    private void showLoadingIndicator() {
        mContentView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);

    }

    private void hideLoadingIndicator() {
        mContentView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        getLocalWeather();
    }

    private void getLocalWeather() {
        if (NetworkUtils.isConnected(getActivity())) {
            mLocalWeatherClient.getWeatherData(new LocalWeatherRequest(DAYS_OF_FORECAST, new LocationQuery(mLocation)));
        }
    }

    private void setLocalWeather(final LocalWeather localWeather) {
        setCurrentCondition(localWeather.getCurrentCondition(), localWeather.getWeatherToday());
        setWeatherForecast(localWeather.getWeatherList());
    }

    private void setCurrentCondition(final CurrentCondition currentCondition, final Weather weatherToday) {

        mTextViewTemperature.setText(WeatherUtils.formatTemperature(currentCondition.getTempC(),
                currentCondition.getTempF(), mUnits));

        mTextViewTemperatureMax.setText(WeatherUtils.formatTemperature(weatherToday.getTempMaxC(), weatherToday.getTempMaxF(), mUnits));
        mTextViewTemperatureMin.setText(WeatherUtils.formatTemperature(weatherToday.getTempMinC(), weatherToday.getTempMinF(), mUnits));

        mTextViewWeatherDescription.setText(currentCondition.getWeatherDescription());

        String lastUpdate = getString(R.string.label_last_update) + " " + currentCondition.getObservationTime();
        mTextViewObservationTime.setText(lastUpdate);

        ImageLoader.getInstance().displayImage(currentCondition.getWeatherIconUrl(), mImageViewWeatherIcon);
        String wind = getString(R.string.label_wind) + " " + WeatherUtils.formatWindSpeed(currentCondition.getWindSpeedKmph(), currentCondition.getWindSpeedMiles(), currentCondition.getWindDirection16Point(), mUnits);
        mTextViewWind.setText(wind);

        String precipitation = getString(R.string.label_precipitation) + " " +
                WeatherUtils.formatPrecipitation(currentCondition.getPrecipitationMM());
        mTextViewPrecipitation.setText(precipitation);

    }

    private void setWeatherForecast(final List<Weather> weatherForecastList) {
        mWeatherForecastHolder.setWeatherForecastList(weatherForecastList, mUnits);
    }

    @Override
    public void onUnitsChanged(Units units) {
        setUnits(units);
    }

    private void setUnits(final Units units) {
        mUnits = units;

        if (mLocalWeather != null) {
            final CurrentCondition currentCondition = mLocalWeather.getCurrentCondition();
            final Weather weatherToday = mLocalWeather.getWeatherToday();
            mTextViewTemperature.setText(WeatherUtils.formatTemperature(currentCondition.getTempC(),
                    currentCondition.getTempF(), units));
            mTextViewTemperatureMin.setText(WeatherUtils.formatTemperature(weatherToday.getTempMinC(),
                    weatherToday.getTempMinF(), units));
            mTextViewTemperatureMax.setText(WeatherUtils.formatTemperature(weatherToday.getTempMaxC(),
                    weatherToday.getTempMaxF(), units));
            String wind = getString(R.string.label_wind) + " " + WeatherUtils.formatWindSpeed(currentCondition.getWindSpeedKmph(), currentCondition.getWindSpeedMiles(), currentCondition.getWindDirection16Point(), units);
            mTextViewWind.setText(wind);
            mWeatherForecastHolder.setUnits(units);
        } else {
            getLocalWeather();
        }

    }
}
