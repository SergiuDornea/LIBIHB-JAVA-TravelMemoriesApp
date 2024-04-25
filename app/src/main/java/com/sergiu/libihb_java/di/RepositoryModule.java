package com.sergiu.libihb_java.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;
import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.data.repository.EmergencyRepository;
import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.data.repository.SettingsRepository;
import com.sergiu.libihb_java.data.repository.WeatherRepository;

import java.util.concurrent.Executor;

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
    public MemoriesRepository provideMemoriesRoomRepository(TravelMemoryDao travelMemoryDao) {
        return new MemoriesRepository(travelMemoryDao);
    }

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore fStore, Executor executor) {
        return new AuthRepository(firebaseAuth, fStore, executor);
    }

    @Provides
    @Singleton
    public WeatherRepository provideWeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource) {
        return new WeatherRepository(weatherRemoteDataSource);
    }

    @Provides
    @Singleton
    public EmergencyRepository provideEmergencyRepository(DiskDataStore diskDataStore) {
        return new EmergencyRepository(diskDataStore);
    }

    @Provides
    @Singleton
    public SettingsRepository provideSettingsRepository(DiskDataStore diskDataStore) {
        return new SettingsRepository(diskDataStore);
    }
}
