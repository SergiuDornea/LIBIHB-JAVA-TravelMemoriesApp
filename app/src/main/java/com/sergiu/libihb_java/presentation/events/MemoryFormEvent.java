package com.sergiu.libihb_java.presentation.events;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public abstract class MemoryFormEvent {

    public static final MemoryFormEvent SubmitClicked = new MemoryFormEvent() {
    };

    public static class ImgListChanged extends MemoryFormEvent {
        public final List<String> imgList;

        public ImgListChanged(List<String> imgList) {
            this.imgList = imgList;
        }
    }

    public static class MemoryNameChanged extends MemoryFormEvent {
        public final String memoryName;

        public MemoryNameChanged(String memoryName) {
            this.memoryName = memoryName;
        }
    }

    public static class MemoryDescriptionChanged extends MemoryFormEvent {
        public final String memoryDescription;

        public MemoryDescriptionChanged(String memoryDescription) {
            this.memoryDescription = memoryDescription;
        }
    }

    public static class MemoryPlaceLocationNameChanged extends MemoryFormEvent {
        public final String placeLocationName;

        public MemoryPlaceLocationNameChanged(String placeLocationName) {
            this.placeLocationName = placeLocationName;
        }
    }

    public static class MemoryPlaceCountryNameChanged extends MemoryFormEvent {
        public final String memoryPlaceCountryName;

        public MemoryPlaceCountryNameChanged(String memoryPlaceCountryName) {
            this.memoryPlaceCountryName = memoryPlaceCountryName;
        }
    }

    public static class MemoryPlaceAdminNameChanged extends MemoryFormEvent {
        public final String placeLocationAdminName;

        public MemoryPlaceAdminNameChanged(String placeLocationAdminName) {
            this.placeLocationAdminName = placeLocationAdminName;
        }
    }

    public static class MemoryCoordinatesChanged extends MemoryFormEvent {
        public final LatLng coordinates;

        public MemoryCoordinatesChanged(LatLng coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class MemoryDateOfTravelChanged extends MemoryFormEvent {
        public final Date dateOfTravel;

        public MemoryDateOfTravelChanged(Date dateOfTravel) {
            this.dateOfTravel = dateOfTravel;
        }
    }
}
