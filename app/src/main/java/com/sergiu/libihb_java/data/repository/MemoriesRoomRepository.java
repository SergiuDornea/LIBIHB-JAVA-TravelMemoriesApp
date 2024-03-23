package com.sergiu.libihb_java.data.repository;

import androidx.lifecycle.LiveData;

import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.List;

import javax.inject.Inject;

public class MemoriesRoomRepository {
    private TravelMemoryDao dao;

    @Inject
    public MemoriesRoomRepository(TravelMemoryDao travelMemoryDao){
        this.dao = travelMemoryDao;
    }

    public void insertTravelMemory(TravelMemory travelMemory){
        dao.insertTravelMemory(travelMemory);
    }

    public void updateTravelMemory(TravelMemory travelMemory){
        dao.updateTravelMemory(travelMemory);
    }

    public LiveData<List<TravelMemory>> getMemories(){
        return dao.getMemories();
    }

    public void deleteTravelMemory(TravelMemory travelMemory){
        dao.deleteTravelMemory(travelMemory);
    }

    public void deleteAll(){
        dao.deleteAll();
    }
}
