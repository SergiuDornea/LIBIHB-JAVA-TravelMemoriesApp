package com.sergiu.libihb_java.di;

import static com.sergiu.libihb_java.presentation.utils.Constants.OPEN_WEATHER_BASE_URL;

import com.sergiu.libihb_java.network.WeatherApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Singleton
    @Provides
    public WeatherApi provideWeatherApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WeatherApi.class);
    }
}
