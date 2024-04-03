package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MemoryOverviewViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public MemoryOverviewViewModel(MemoriesRoomRepository memoriesRoomRepository){
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public LiveData<String> observePlaceLocationName(){
        return memoriesRoomRepository.getPlaceLocationName();
    }

    public LiveData<List<String>> observeListOfImgUri() {
        return memoriesRoomRepository.getListOfImgUri();
    }
    public LiveData<String> observeMemoryName() {
        return memoriesRoomRepository.getMemoryName();
    }
    public LiveData<String> observeMemoryDescription() {
        return memoriesRoomRepository.getMemoryDescription();
    }
    public LiveData<LatLng> observeCoordinates() {
        return memoriesRoomRepository.getCoordinates();
    }
    public LiveData<Date> observeDateOfTravel() {
        return memoriesRoomRepository.getDateOfTravel();
    }
}