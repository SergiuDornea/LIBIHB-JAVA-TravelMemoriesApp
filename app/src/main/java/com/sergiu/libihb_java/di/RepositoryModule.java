package com.sergiu.libihb_java.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
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
    public MemoriesRoomRepository provideMemoriesRoomRepository(TravelMemoryDao travelMemoryDao) {
        return new MemoriesRoomRepository(travelMemoryDao);
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
}
