package com.sergiu.libihb_java.domain.model.weather;

public class CurrentWeather {
    public static final CurrentWeather CURRENT_EMPTY_WEATHER = new CurrentWeather("", "", "", "", "", "", "", "", "", "", "");

    private final String temp;
    private final String feelsLike;
    private final String tempMin;
    private final String tempMax;
    private final String pressure;
    private final String humidity;
    private final String sunrise;
    private final String sunset;
    private final String main;
    private final String description;
    private final String speed;

    public CurrentWeather(
            String temp,
            String feelsLike,
            String tempMin,
            String tempMax,
            String pressure,
            String humidity,
            String sunrise,
            String sunset,
            String main,
            String description,
            String speed) {

        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.main = main;
        this.description = description;
        this.speed = speed;
    }

    public String getTemp() {
        return temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getSpeed() {
        return speed;
    }
}
