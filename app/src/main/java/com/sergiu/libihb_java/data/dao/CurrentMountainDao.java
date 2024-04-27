package com.sergiu.libihb_java.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CurrentMountainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCurrentMountain(CurrentMountain currentMountain);

    @Query("SELECT * from mountains")
    Flowable<List<CurrentMountain>> getCurrentMountainList();

    @Query("SELECT * FROM mountains WHERE id = :mountainId")
    Flowable<CurrentMountain> getCurrentMountainById(String mountainId);

    @Query("SELECT * FROM mountains WHERE (LENGTH(:name) > 0 AND name LIKE '%' || :name || '%')")
    Flowable<List<CurrentMountain>> searchCurrentMountainsByName(String name);
}