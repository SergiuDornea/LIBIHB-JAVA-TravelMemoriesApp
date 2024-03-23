package com.sergiu.libihb_java.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.List;

@Dao
public interface TravelMemoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTravelMemory(TravelMemory travelMemory);

    @Update
    void updateTravelMemory(TravelMemory travelMemory);

    @Query("SELECT * from memories")
    LiveData<List<TravelMemory>> getMemories();

    @Delete
    void deleteTravelMemory(TravelMemory travelMemory);

    @Query("DELETE from memories")
    void deleteAll();
}
