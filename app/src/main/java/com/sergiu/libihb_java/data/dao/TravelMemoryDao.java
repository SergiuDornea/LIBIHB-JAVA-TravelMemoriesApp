package com.sergiu.libihb_java.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TravelMemoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertTravelMemory(TravelMemory travelMemory);

    @Update
    Completable updateTravelMemory(TravelMemory travelMemory);

    @Query("SELECT * from memories")
    Flowable<List<TravelMemory>> getMemories();

    @Delete
    Completable deleteTravelMemory(TravelMemory travelMemory);

    @Query("DELETE from memories")
    Completable deleteAll();
}
