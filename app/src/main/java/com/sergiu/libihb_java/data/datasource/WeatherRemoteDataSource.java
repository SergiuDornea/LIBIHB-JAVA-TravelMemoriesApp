package com.sergiu.libihb_java.data.datasource;

import static com.sergiu.libihb_java.domain.model.weather.CurrentWeather.CURRENT_EMPTY_WEATHER;
import static com.sergiu.libihb_java.presentation.utils.Constants.OPEN_WEATHER_API_KEY;

import android.util.Log;

import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;
import com.sergiu.libihb_java.network.WeatherApi;
import com.sergiu.libihb_java.presentation.utils.MapperUtil;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class WeatherRemoteDataSource {
    private static final String TAG = WeatherRemoteDataSource.class.getSimpleName();
    private final WeatherApi weatherApi;

    @Inject
    public WeatherRemoteDataSource(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Flowable<CurrentWeather> getCurrentWeatherByLatAndLng(String latitude, String longitude, String units) {
        return weatherApi.getWeatherByLatAndLng(latitude, longitude, units, OPEN_WEATHER_API_KEY)
                .map(MapperUtil::mapRemoteResponseToCurrentWeather)
                .onErrorReturnItem(CURRENT_EMPTY_WEATHER)
                .doOnError(throwable -> Log.e(TAG, "Error: " + throwable));
    }
}
