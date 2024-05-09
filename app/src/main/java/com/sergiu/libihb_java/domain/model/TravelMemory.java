package com.sergiu.libihb_java.domain.model;

import static com.sergiu.libihb_java.presentation.utils.Constants.DATE_FORMAT_PATTERN;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.sergiu.libihb_java.presentation.utils.converters.DateConverter;
import com.sergiu.libihb_java.presentation.utils.converters.ListStringConverter;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "memories")
@TypeConverters({ListStringConverter.class, DateConverter.class})
public class TravelMemory {
    @PrimaryKey()
    @NotNull
    private String id;
    @ColumnInfo(name = "image_list")
    private List<String> imageList;
    @ColumnInfo(name = "memroy_name")
    private String memoryName;
    @ColumnInfo(name = "memory_description")
    private String memoryDescription;
    @ColumnInfo(name = "place_location_name")
    private String placeLocationName;
    @ColumnInfo(name = "place_country_name")
    private String placeCountryName;
    @ColumnInfo(name = "place_admin_area_name")
    private String placeAdminAreaName;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "date_of_travel")
    private Date dateOfTravel;
    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite = false;

    public TravelMemory(
            List<String> imageList,
            String memoryName,
            String memoryDescription,
            double latitude,
            double longitude,
            Date dateOfTravel,
            String placeLocationName,
            String placeCountryName,
            String placeAdminAreaName) {
        this.imageList = imageList;
        this.memoryName = memoryName;
        this.memoryDescription = memoryDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateOfTravel = dateOfTravel;
        this.placeLocationName = placeLocationName;
        this.placeCountryName = placeCountryName;
        this.placeAdminAreaName = placeAdminAreaName;
        id = "";
    }

    public TravelMemory() {

    }

    public List<String> getImageList() {
        return imageList;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
        return dateFormat.format(dateOfTravel);
    }

    public String getPlaceLocationName() {
        return placeLocationName;
    }

    public String getPlaceCountryName() {
        return placeCountryName;
    }

    public String getPlaceAdminAreaName() {
        return placeAdminAreaName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getMemoryDescription() {
        return memoryDescription;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setMemoryName(String memoryName) {
        this.memoryName = memoryName;
    }

    public void setMemoryDescription(String memoryDescription) {
        this.memoryDescription = memoryDescription;
    }

    public void setPlaceLocationName(String placeLocationName) {
        this.placeLocationName = placeLocationName;
    }

    public void setPlaceCountryName(String placeCountryName) {
        this.placeCountryName = placeCountryName;
    }

    public void setPlaceAdminAreaName(String placeAdminAreaName) {
        this.placeAdminAreaName = placeAdminAreaName;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
