package com.sergiu.libihb_java.domain.model;


import android.os.Parcel;
import android.os.Parcelable;

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

    protected TravelMemory(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        imageList = in.createStringArrayList();
        memoryName = in.readString();
        memoryDescription = in.readString();
        placeLocationName = in.readString();
        coordinates = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<TravelMemory> CREATOR = new Creator<TravelMemory>() {
        @Override
        public TravelMemory createFromParcel(Parcel in) {
            return new TravelMemory(in);
        }

        @Override
        public TravelMemory[] newArray(int size) {
            return new TravelMemory[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeStringList(imageList);
        dest.writeString(memoryName);
        dest.writeString(memoryDescription);
        dest.writeString(placeLocationName);
        dest.writeParcelable(coordinates, flags);
        dest.writeLong(dateOfTravel.getTime());
    }
}
