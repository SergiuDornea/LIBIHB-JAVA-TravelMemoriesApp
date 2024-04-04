package com.sergiu.libihb_java.domain.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.presentation.utils.converters.DateConverter;
import com.sergiu.libihb_java.presentation.utils.converters.LatLngConverter;
import com.sergiu.libihb_java.presentation.utils.converters.ListStringConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "memories")
@TypeConverters({ListStringConverter.class, LatLngConverter.class, DateConverter.class})
public class TravelMemory implements Parcelable {
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
            String placeLocationName) {
        this.imageList = imageList;
        this.memoryName = memoryName;
        this.memoryDescription = memoryDescription;
        this.coordinates = coordinates;
        this.dateOfTravel = dateOfTravel;
        this.placeLocationName = placeLocationName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeStringList(imageList);
        parcel.writeString(memoryName);
        parcel.writeString(memoryDescription);
        parcel.writeString(placeLocationName);
        parcel.writeParcelable(coordinates, i);
        parcel.writeLong(dateOfTravel.getTime());
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

    public void setImageList(List<String> imageList) {
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

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }
}
