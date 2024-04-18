package com.sergiu.libihb_java.domain.model.mountain;

import com.google.gson.annotations.SerializedName;

public class Mountain {
    @SerializedName("_id")
    private String id;
    private String name;
    private String description;
    private String altitude;
    @SerializedName("has_death_zone")
    private boolean hasDeathZone;
    private String location;
    @SerializedName("first_climber")
    private String firstClimber;
    @SerializedName("first_climbed_date")
    private String firstClimbedDate;
    @SerializedName("mountain_img")
    private String mountainImg;
    @SerializedName("country_flag_img")
    private String countryFlagImg;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAltitude() {
        return altitude;
    }

    public boolean isHasDeathZone() {
        return hasDeathZone;
    }

    public String getLocation() {
        return location;
    }

    public String getFirstClimber() {
        return firstClimber;
    }

    public String getFirstClimbedDate() {
        return firstClimbedDate;
    }

    public String getMountainImg() {
        return mountainImg;
    }

    public String getCountryFlagImg() {
        return countryFlagImg;
    }
}
