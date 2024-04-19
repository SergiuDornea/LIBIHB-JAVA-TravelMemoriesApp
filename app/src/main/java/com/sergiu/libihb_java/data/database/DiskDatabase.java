package com.sergiu.libihb_java.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sergiu.libihb_java.data.dao.CurrentMountainDao;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

@Database(entities = {TravelMemory.class, CurrentMountain.class}, version = 3)
public abstract class DiskDatabase extends RoomDatabase {
    public abstract TravelMemoryDao travelMemoryDao();

    public abstract CurrentMountainDao currentMountainDao();
}
