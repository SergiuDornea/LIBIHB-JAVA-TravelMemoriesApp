package com.sergiu.libihb_java.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;

@Database(entities = {TravelMemory.class}, version = 2)
public abstract class MemoriesDatabase extends RoomDatabase {
    public abstract TravelMemoryDao travelMemoryDao();
}
