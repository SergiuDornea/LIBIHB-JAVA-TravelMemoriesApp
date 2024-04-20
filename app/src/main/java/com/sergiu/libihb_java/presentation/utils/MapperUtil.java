package com.sergiu.libihb_java.presentation.utils;

import static com.sergiu.libihb_java.domain.model.mountain.CurrentMountain.CURRENT_EMPTY_MOUNTAIN;
import static com.sergiu.libihb_java.domain.model.weather.CurrentWeather.CURRENT_EMPTY_WEATHER;

import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;
import com.sergiu.libihb_java.domain.model.weather.RootWeather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class MapperUtil {
    public static CurrentWeather mapRemoteResponseToCurrentWeather(RootWeather rootWeather) {
        if (rootWeather == null
                || rootWeather.getMain() == null
                || rootWeather.getSys() == null
                || rootWeather.getWeather() == null) return CURRENT_EMPTY_WEATHER;

        return new CurrentWeather(
                Integer.toString((int) rootWeather.getMain().getTemp()),
                Integer.toString((int) rootWeather.getMain().getFeelsLike()),
                Integer.toString((int) rootWeather.getMain().getTempMin()),
                Integer.toString((int) rootWeather.getMain().getTempMax()),
                Integer.toString(rootWeather.getMain().getPressure()),
                Integer.toString(rootWeather.getMain().getHumidity()),
                getTimeFromInt(rootWeather.getSys().getSunrise()),
                getTimeFromInt(rootWeather.getSys().getSunset()),
                rootWeather.getWeather().get(0).getMain(),
                rootWeather.getWeather().get(0).getDescription(),
                Double.toString(rootWeather.getWind().getSpeed())
        );
    }

    private static String getTimeFromInt(int time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:dd", Locale.getDefault());
        Date unformattedDate = new Date((long) time * 1000);
        return sdf.format(unformattedDate);
    }

    public static CurrentMountain mapMountainToCurrentMountain(Mountain mountain) {
        if (mountain.getId() == null) {
            return CURRENT_EMPTY_MOUNTAIN;
        } else {
            return new CurrentMountain(
                    mountain.getId(),
                    mountain.getName(),
                    mountain.getDescription(),
                    mountain.getAltitude(),
                    mountain.isHasDeathZone(),
                    mountain.getLocation(),
                    mountain.getFirstClimber(),
                    mountain.getFirstClimbedDate(),
                    mountain.getMountainImg(),
                    mountain.getCountryFlagImg());
        }
    }
}
