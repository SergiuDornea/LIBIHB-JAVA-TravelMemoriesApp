package com.sergiu.libihb_java.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.presentation.utils.converters.LatLngConverter;
import com.sergiu.libihb_java.presentation.utils.converters.ListIntegerConverter;

import java.util.List;

@Entity(tableName = "memories")
@TypeConverters({ListIntegerConverter.class, LatLngConverter.class})
public class TravelMemory {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "image_list")
    private List<Integer> imageList;
    @ColumnInfo(name = "memroy_name")
    private String memoryName;
    @ColumnInfo(name = "memory_description")
    private String memoryDescription;
    @ColumnInfo(name = "place_location_name")
    private String placeLocationName;
    @ColumnInfo(name = "coordinates")
    private LatLng coordinates;
    @ColumnInfo(name = "date_of_travel")
    private String dateOfTravel;

    public TravelMemory(List<Integer> imageList, String memoryName, LatLng coordinates, String dateOfTravel, String placeLocationName) {
        this.imageList = imageList;
        this.memoryName = memoryName;
        this.coordinates = coordinates;
        this.dateOfTravel = dateOfTravel;
        this.placeLocationName = placeLocationName;
    }


    public List<Integer> getImageList() {
        return imageList;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public String getPlaceLocationName() {
        return placeLocationName;
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

    public void setImageList(List<Integer> imageList) {
        this.imageList = imageList;
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

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }
}
