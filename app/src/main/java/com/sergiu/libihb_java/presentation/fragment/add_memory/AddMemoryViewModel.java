package com.sergiu.libihb_java.presentation.fragment.add_memory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddMemoryViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;
    private final MutableLiveData<List<String>> listOfImgUri = new MutableLiveData<>();
    private final MutableLiveData<String> memoryName = new MutableLiveData<>();
    private final MutableLiveData<String> memoryDescription = new MutableLiveData<>();
    private final MutableLiveData<String> placeLocationName = new MutableLiveData<>();
    private final MutableLiveData<LatLng> coordinates = new MutableLiveData<>();
    private final MutableLiveData<Date> dateOfTravel = new MutableLiveData<>();

    @Inject
    public AddMemoryViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public MutableLiveData<List<String>> getListOfImgUri() {
        return listOfImgUri;
    }
    public MutableLiveData<String> getMemoryName() {
        return memoryName;
    }
    public MutableLiveData<String> getMemoryDescription() {
        return memoryDescription;
    }
    public MutableLiveData<String> getPlaceLocationName() {
        return placeLocationName;
    }
    public MutableLiveData<LatLng> getCoordinates() {
        return coordinates;
    }
    public MutableLiveData<Date> getDateOfTravel() {
        return dateOfTravel;
    }

    public void setListOfImgUri(List<String> list) {
        listOfImgUri.setValue(list);
    }
    public void setMemoryName(String name) {
        memoryName.setValue(name);
    }
    public void setMemoryDescription(String description) {
        memoryDescription.setValue(description);
    }
    public void setPlaceLocationName(String locationName) {
        placeLocationName.setValue(locationName);
    }
    public void setCoordinates(LatLng latLng) {
       coordinates.setValue(latLng);
    }
    public void setDateOfTravel(Date date) {
       dateOfTravel.setValue(date);
    }


    public void saveMemory(TravelMemory travelMemory) {
        memoriesRoomRepository.insertTravelMemory(travelMemory);
    }
}
