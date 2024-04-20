package com.sergiu.libihb_java.domain.model.mountain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "mountains")
public class CurrentMountain {
    @PrimaryKey()
    @NotNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "altitude")
    private String altitude;
    @ColumnInfo(name = "has_death_zone")
    private boolean hasDeathZone;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "first_climber")
    private String firstClimber;
    @ColumnInfo(name = "first_climber_date")
    private String firstClimbedDate;
    @ColumnInfo(name = "mountain_img")
    private String mountainImg;
    @ColumnInfo(name = "country_flag_img")
    private String countryFlagImg;

    public CurrentMountain(
            @NonNull String id,
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

    public String getAltitude() {
        return altitude;
    }

    public boolean getHasDeathZone() {
        return hasDeathZone;
    }

    public String getLocation() {
        return location;
    }

    public String getMountainImg() {
        return mountainImg;
    }

    public String getDescription() {
        return description;
    }

    public String getFirstClimber() {
        return firstClimber;
    }

    public String getFirstClimbedDate() {
        return firstClimbedDate;
    }

    public String getCountryFlagImg() {
        return countryFlagImg;
    }
}
