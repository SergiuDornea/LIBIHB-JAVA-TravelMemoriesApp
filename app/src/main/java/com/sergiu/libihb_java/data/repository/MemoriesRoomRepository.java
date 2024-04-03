package com.sergiu.libihb_java.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MemoriesRoomRepository {
    private final TravelMemoryDao dao;
    private final MutableLiveData<List<String>> listOfImgUri = new MutableLiveData<>();
    private final MutableLiveData<String> memoryName = new MutableLiveData<>();
    private final MutableLiveData<String> memoryDescription = new MutableLiveData<>();
    private final MutableLiveData<String> placeLocationName = new MutableLiveData<>();
    private final MutableLiveData<LatLng> coordinates = new MutableLiveData<>();
    private final MutableLiveData<Date> dateOfTravel = new MutableLiveData<>();

    @Inject
    public MemoriesRoomRepository(TravelMemoryDao travelMemoryDao) {
        this.dao = travelMemoryDao;
    }

    public MutableLiveData<List<String>> observeListOfImgUri() {
        return listOfImgUri;
    }

    public MutableLiveData<String> observeMemoryName() {
        return memoryName;
    }


    public MutableLiveData<String> observeMemoryDescription() {
        return memoryDescription;
    }

    public MutableLiveData<String> observePlaceLocationName() {
        return placeLocationName;
    }

    public MutableLiveData<LatLng> observeCoordinates() {
        return coordinates;
    }

    public MutableLiveData<Date> observeDateOfTravel() {
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

    public void insertTravelMemory(TravelMemory travelMemory) {
        dao.insertTravelMemory(travelMemory);
    }

    public void updateTravelMemory(TravelMemory travelMemory) {
        dao.updateTravelMemory(travelMemory);
    }

    public LiveData<List<TravelMemory>> getMemories() {
        return dao.getMemories();
    }

    public void deleteTravelMemory(TravelMemory travelMemory) {
        dao.deleteTravelMemory(travelMemory);
    }

    public void deleteAll() {
        dao.deleteAll();
    }
}
