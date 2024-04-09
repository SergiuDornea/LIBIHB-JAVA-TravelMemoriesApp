package com.sergiu.libihb_java.di;

import com.sergiu.libihb_java.data.datasource.WeatherRemoteDataSource;
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
}
