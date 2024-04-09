package com.sergiu.libihb_java.network;

import com.sergiu.libihb_java.domain.model.weather.RootWeather;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("data/2.5/weather")
    Flowable<RootWeather> getWeatherByLatAndLng(@Query("lat") String lat,
                                                @Query("lon") String lon,
                                                @Query("units") String unit,
                                                @Query("appid") String appId);
}
