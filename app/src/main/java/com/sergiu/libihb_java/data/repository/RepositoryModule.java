package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.data.dao.TravelMemoryDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public MemoriesRoomRepository provideMemoriesRoomRepository(TravelMemoryDao travelMemoryDao) {
        return new MemoriesRoomRepository(travelMemoryDao);
    }
}
