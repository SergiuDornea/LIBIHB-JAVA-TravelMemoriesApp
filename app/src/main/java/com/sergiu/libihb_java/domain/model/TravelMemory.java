package com.sergiu.libihb_java.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.presentation.utils.converters.DateConverter;
import com.sergiu.libihb_java.presentation.utils.converters.LatLngConverter;
import com.sergiu.libihb_java.presentation.utils.converters.ListStringConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "memories")
@TypeConverters({ListStringConverter.class, LatLngConverter.class, DateConverter.class})
public class TravelMemory {
    private static final String DATE_FORMAT_PATTERN = "dd MMM yyyy";
    @PrimaryKey(autoGenerate = true)
    private Long id;
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
    @ColumnInfo(name = "coordinates")
    private LatLng coordinates;
    @ColumnInfo(name = "date_of_travel")
    private Date dateOfTravel;

    public TravelMemory(
            List<String> imageList,
            String memoryName,
            String memoryDescription,
            LatLng coordinates,
            Date dateOfTravel,
            String placeLocationName,
            String placeCountryName,
            String placeAdminAreaName) {
        this.imageList = imageList;
        this.memoryName = memoryName;
        this.memoryDescription = memoryDescription;
        this.coordinates = coordinates;
        this.dateOfTravel = dateOfTravel;
        this.placeLocationName = placeLocationName;
        this.placeCountryName = placeCountryName;
        this.placeAdminAreaName = placeAdminAreaName;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public LatLng getCoordinates() {
        return coordinates;
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

    public Long getId() {
        return id;
    }

    public String getMemoryDescription() {
        return memoryDescription;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
