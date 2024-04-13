package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class MemoryFormState {
    private final List<String> listOfImgUri;
    private final String memoryName;
    private final String memoryDescription;
    private final String placeLocationName;
    private final String placeCountryName;
    private final String placeAdminName;
    private final LatLng coordinates;
    private final Date dateOfTravel;
    private final String listOfImgUriError;
    private final String memoryNameError;
    private final String memoryDescriptionError;
    private final String placeLocationNameError;
    private final String placeCountryNameError;
    private final String placeAdminNameError;
    private final String coordinatesError;
    private final String dateOfTravelError;

    public MemoryFormState(
            @NotNull List<String> listOfImgUri,
            @NotNull String memoryName,
            @NotNull String memoryDescription,
            @NotNull String placeLocationName,
            @NotNull String placeCountryName,
            String placeAdminName,
            @NotNull LatLng coordinates,
            @NotNull Date dateOfTravel,
            String listOfImgUriError,
            String memoryNameError,
            String memoryDescriptionError,
            String placeLocationNameError,
            String placeCountryNameError,
            String placeAdminNameError,
            String coordinatesError,
            String dateOfTravelError
    ) {
        this.listOfImgUri = listOfImgUri;
        this.memoryName = memoryName;
        this.memoryDescription = memoryDescription;
        this.placeLocationName = placeLocationName;
        this.placeCountryName = placeCountryName;
        this.placeAdminName = placeAdminName;
        this.coordinates = coordinates;
        this.dateOfTravel = dateOfTravel;
        this.listOfImgUriError = listOfImgUriError;
        this.memoryNameError = memoryNameError;
        this.memoryDescriptionError = memoryDescriptionError;
        this.placeLocationNameError = placeLocationNameError;
        this.placeCountryNameError = placeCountryNameError;
        this.placeAdminNameError = placeAdminNameError;
        this.coordinatesError = coordinatesError;
        this.dateOfTravelError = dateOfTravelError;
    }

    public List<String> getListOfImgUri() {
        return listOfImgUri;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public String getMemoryDescription() {
        return memoryDescription;
    }

    public String getPlaceLocationName() {
        return placeLocationName;
    }

    public String getPlaceCountryName() {
        return placeCountryName;
    }

    public String getPlaceAdminName() {
        return placeAdminName;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public String getListOfImgUriError() {
        return listOfImgUriError;
    }

    public String getMemoryNameError() {
        return memoryNameError;
    }

    public String getMemoryDescriptionError() {
        return memoryDescriptionError;
    }

    public String getPlaceLocationNameError() {
        return placeLocationNameError;
    }

    public String getPlaceCountryNameError() {
        return placeCountryNameError;
    }

    public String getPlaceAdminNameError() {
        return placeAdminNameError;
    }

    public String getCoordinatesError() {
        return coordinatesError;
    }

    public String getDateOfTravelError() {
        return dateOfTravelError;
    }
}
