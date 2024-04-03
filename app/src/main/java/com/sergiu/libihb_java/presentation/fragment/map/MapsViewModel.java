package com.sergiu.libihb_java.presentation.fragment.map;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapsViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public MapsViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public void setCoordinates(LatLng latLng) {
        memoriesRoomRepository.setCoordinates(latLng);
    }

    public void setPlaceLocationName(String locationName) {
        memoriesRoomRepository.setPlaceLocationName(locationName);
    }
}
