package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class WeatherRepository {
    private final WeatherRemoteDataSource weatherRemoteDataSource;

    @Inject
    public WeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource) {
        this.weatherRemoteDataSource = weatherRemoteDataSource;
    }

    public Flowable<CurrentWeather> getCurrentWeatherByLatAndLng(String latitude, String longitude, String unit) {
        return weatherRemoteDataSource.getCurrentWeatherByLatAndLng(latitude, longitude, unit);
    }
}
