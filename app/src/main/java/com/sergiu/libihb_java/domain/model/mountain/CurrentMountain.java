package com.sergiu.libihb_java.domain.model.mountain;

public class CurrentMountain {
    public static final CurrentMountain CURRENT_MOUNTAIN = new CurrentMountain("", "", "", "", false, "", "", "", "", "");

    private String id;
    private String name;
    private String description;
    private String altitude;
    private boolean hasDeathZone;
    private String location;
    private String firstClimber;
    private String firstClimbedDate;
    private String mountainImg;
    private String countryFlagImg;

    public CurrentMountain(
            String id,
            String name,
            String description,
            String altitude,
            boolean hasDeathZone,
            String location,
            String firstClimber,
            String firstClimbedDate,
            String mountainImg,
            String countryFlagImg
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.altitude = altitude;
        this.hasDeathZone = hasDeathZone;
        this.location = location;
        this.firstClimber = firstClimber;
        this.firstClimbedDate = firstClimbedDate;
        this.mountainImg = mountainImg;
        this.countryFlagImg = countryFlagImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public boolean getHasDeathZone() {
        return hasDeathZone;
    }

    public void setHasDeathZone(boolean hasDeathZone) {
        this.hasDeathZone = hasDeathZone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstClimber() {
        return firstClimber;
    }

    public void setFirstClimber(String firstClimber) {
        this.firstClimber = firstClimber;
    }

    public String getFirstClimbedDate() {
        return firstClimbedDate;
    }

    public void setFirstClimbedDate(String firstClimbedDate) {
        this.firstClimbedDate = firstClimbedDate;
    }

    public String getMountainImg() {
        return mountainImg;
    }

    public void setMountainImg(String mountainImg) {
        this.mountainImg = mountainImg;
    }

    public String getCountryFlagImg() {
        return countryFlagImg;
    }

    public void setCountryFlagImg(String countryFlagImg) {
        this.countryFlagImg = countryFlagImg;
    }
}
