package com.sergiu.libihb_java.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.sergiu.libihb_java.data.database.RemoteDatabase;
import com.sergiu.libihb_java.data.datasource.MountainRemoteDataSource;
import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
import com.sergiu.libihb_java.network.MountainApi;
import com.sergiu.libihb_java.network.WeatherApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourceModule {

    @Singleton
    @Provides
    public WeatherRemoteDataSource provideWeatherRemoteDataSource(WeatherApi weatherApi) {
        return new WeatherRemoteDataSource(weatherApi);
    }

    @Singleton
    @Provides
    public MountainRemoteDataSource provideMountainRemoteDataSource(MountainApi mountainApi) {
        return new MountainRemoteDataSource(mountainApi);
    }

    @Singleton
    @Provides
    public RemoteDatabase provideRemoteDatabase(FirebaseFirestore fStore, FirebaseStorage firebaseStorage, FirebaseAuth firebaseAuth) {
        return new RemoteDatabase(fStore, firebaseStorage, firebaseAuth);
    }
}
