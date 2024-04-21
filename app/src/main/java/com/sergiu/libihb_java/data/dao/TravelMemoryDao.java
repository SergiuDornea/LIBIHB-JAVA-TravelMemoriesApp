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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTravelMemory(TravelMemory travelMemory);

    @Update
    Completable updateTravelMemory(TravelMemory travelMemory);

    @Query("SELECT * from memories")
    Flowable<List<TravelMemory>> getMemories();

    @Query("SELECT * FROM memories WHERE id = :memoryId")
    Flowable<TravelMemory> getMemoryById(long memoryId);

    @Query("SELECT * FROM memories WHERE is_favorite = 1")
    Flowable<List<TravelMemory>> getAllFavoriteMemories();

    @Query("UPDATE memories SET is_favorite = :isFavorite WHERE id = :memoryId")
    Completable updateIsFavorite(long memoryId, boolean isFavorite);

    @Query("SELECT is_favorite FROM memories WHERE id = :memoryId")
    Flowable<Boolean> isMemoryInFavorites(long memoryId);

    @Delete
    Completable deleteTravelMemory(TravelMemory travelMemory);

    @Query("DELETE from memories")
    Completable deleteAll();
}
