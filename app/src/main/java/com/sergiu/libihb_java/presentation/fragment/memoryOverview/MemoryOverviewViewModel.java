package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

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

    public MutableLiveData<String> getPlaceLocationName(){
        return memoriesRoomRepository.getPlaceLocationName();
    }

    public MutableLiveData<List<String>> getListOfImgUri() {
        return memoriesRoomRepository.getListOfImgUri();
    }
    public MutableLiveData<String> getMemoryName() {
        return memoriesRoomRepository.getMemoryName();
    }
    public MutableLiveData<String> getMemoryDescription() {
        return memoriesRoomRepository.getMemoryDescription();
    }
    public MutableLiveData<LatLng> getCoordinates() {
        return memoriesRoomRepository.getCoordinates();
    }
    public MutableLiveData<Date> getDateOfTravel() {
        return memoriesRoomRepository.getDateOfTravel();
    }
}