package com.sergiu.libihb_java.domain.model;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TravelMemory {
    private List<Integer> imageList;
    private String memoryName;
    private String memoryDescription;
    private String placeLocationName;
    private LatLng coordinates;
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

}
