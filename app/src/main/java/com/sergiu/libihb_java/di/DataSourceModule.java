package com.sergiu.libihb_java.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.sergiu.libihb_java.data.datasource.EducationRemoteDataSource;
import com.sergiu.libihb_java.data.datasource.MemoriesRemoteDataSource;
import com.sergiu.libihb_java.data.datasource.MountainRemoteDataSource;
import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
import com.sergiu.libihb_java.network.MountainApi;
import com.sergiu.libihb_java.network.WeatherApi;
import com.sergiu.libihb_java.presentation.utils.JsonConversionUtil;

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
    public MemoriesRemoteDataSource provideMemoriesRemoteDataSource(FirebaseFirestore fStore, FirebaseStorage firebaseStorage, FirebaseAuth firebaseAuth) {
        return new MemoriesRemoteDataSource(fStore, firebaseStorage, firebaseAuth);
    }

    @Singleton
    @Provides
    public EducationRemoteDataSource provideEducationRemoteDataSource(FirebaseStorage firebaseStorage, JsonConversionUtil jsonConversionUtil) {
        return new EducationRemoteDataSource(firebaseStorage, jsonConversionUtil);
    }
}
