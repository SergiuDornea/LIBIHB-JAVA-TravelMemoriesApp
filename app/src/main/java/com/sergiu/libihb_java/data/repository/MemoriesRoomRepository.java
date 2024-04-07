package com.sergiu.libihb_java.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MemoriesRoomRepository {
    private final TravelMemoryDao dao;
    private final MutableLiveData<List<String>> listOfImgUri = new MutableLiveData<>();
    private final MutableLiveData<String> memoryName = new MutableLiveData<>();
    private final MutableLiveData<String> memoryDescription = new MutableLiveData<>();
    private final MutableLiveData<String> placeLocationName = new MutableLiveData<>();
    private final MutableLiveData<String> placeCountryName = new MutableLiveData<>();
    private final MutableLiveData<String> placeAdminName = new MutableLiveData<>();
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

    public MutableLiveData<String> observePlaceCountryName() {
        return placeCountryName;
    }

    public MutableLiveData<String> observePlaceAdminName() {
        return placeAdminName;
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

    public void setPlaceCountryName(String countryName) {
        placeCountryName.setValue(countryName);
    }

    public void setPlaceAdminName(String adminName) {
        placeAdminName.setValue(adminName);
    }

    public void setCoordinates(LatLng latLng) {
        coordinates.setValue(latLng);
    }

    public void setDateOfTravel(Date date) {
        dateOfTravel.setValue(date);
    }

    public List<String> getImageList() {
        return listOfImgUri.getValue();
    }

    public String getMemoryName() {
        return memoryName.getValue();
    }

    public LatLng getCoordinates() {
        return coordinates.getValue();
    }

    public Date getDateOfTravel() {
        return dateOfTravel.getValue();
    }

    public String getPlaceLocationName() {
        return placeLocationName.getValue();
    }

    public String getPlaceAdminName() {
        return placeAdminName.getValue();
    }

    public String getPlaceCountryName() {
        return placeCountryName.getValue();
    }

    public String getMemoryDescription() {
        return memoryDescription.getValue();
    }

    //DAO METHODS
    public Completable insertTravelMemory(TravelMemory travelMemory) {
        return dao.insertTravelMemory(travelMemory);
    }

    public Flowable<List<TravelMemory>> getMemories() {
        return dao.getMemories();
    }

    public Flowable<TravelMemory> getMemoryById(Long memoryId) {
        return dao.getMemoryById(memoryId);
    }
}
