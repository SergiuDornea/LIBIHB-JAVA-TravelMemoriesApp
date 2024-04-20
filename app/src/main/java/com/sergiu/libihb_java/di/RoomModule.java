package com.sergiu.libihb_java.di;

import android.content.Context;

import androidx.room.Room;

import com.sergiu.libihb_java.data.dao.CurrentMountainDao;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.data.database.DiskDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {
    @Provides
    @Singleton
    public DiskDatabase provideMemoriesDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, DiskDatabase.class, "disk_database").build();
    }

    @Provides
    public TravelMemoryDao provideTravelMemoryDao(DiskDatabase diskDatabase) {
        return diskDatabase.travelMemoryDao();
    }

    @Provides
    public CurrentMountainDao provideCurrentMountainDao(DiskDatabase diskDatabase) {
        return diskDatabase.currentMountainDao();
    }
}
