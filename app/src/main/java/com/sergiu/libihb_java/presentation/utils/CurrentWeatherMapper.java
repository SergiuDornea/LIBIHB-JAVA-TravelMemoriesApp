package com.sergiu.libihb_java.presentation.utils;

import static com.sergiu.libihb_java.domain.model.weather.CurrentWeather.CURRENT_EMPTY_WEATHER;

import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;
import com.sergiu.libihb_java.domain.model.weather.RootWeather;

public final class CurrentWeatherMapper {
    public static CurrentWeather mapRemoteResponseToCurrentWeather(RootWeather rootWeather) {
        if (rootWeather == null
                || rootWeather.getMain() == null
                || rootWeather.getSys() == null
                || rootWeather.getWeather() == null) return CURRENT_EMPTY_WEATHER;

        return new CurrentWeather(
                rootWeather.getMain().getTemp(),
                rootWeather.getMain().getFeelsLike(),
                rootWeather.getMain().getTempMin(),
                rootWeather.getMain().getTempMax(),
                rootWeather.getMain().getPressure(),
                rootWeather.getMain().getHumidity(),
                rootWeather.getSys().getSunrise(),
                rootWeather.getSys().getSunset(),
                rootWeather.getWeather().get(0).getMain(),
                rootWeather.getWeather().get(0).getDescription(),
                rootWeather.getWind().getSpeed()
        );
    }
}
