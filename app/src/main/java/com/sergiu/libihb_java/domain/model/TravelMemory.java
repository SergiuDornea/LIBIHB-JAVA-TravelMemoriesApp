package com.sergiu.libihb_java.domain.model;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TravelMemory {
    private List<Integer> imageList;
    private String placeName;
    private LatLng coordinates;
    private String placeLocationName;
    private String dateOfTravel;

    public TravelMemory(List<Integer> imageList, String placeName, LatLng coordinates, String dateOfTravel,String placeLocationName) {
        this.imageList = imageList;
        this.placeName = placeName;
        this.coordinates = coordinates;
        this.dateOfTravel = dateOfTravel;
        this.placeLocationName = placeLocationName;
    }


    public List<Integer> getImageList() {
        return imageList;
    }

    public String getPlaceName() {
        return placeName;
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
