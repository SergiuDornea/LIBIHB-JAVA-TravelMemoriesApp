package com.sergiu.libihb_java.di;

import static com.sergiu.libihb_java.presentation.utils.Constants.MOUNTAIN_API_BASE_URL;
import static com.sergiu.libihb_java.presentation.utils.Constants.MOUNTAIN_API_HOST;
import static com.sergiu.libihb_java.presentation.utils.Constants.MOUNTAIN_API_HOST_NAME;
import static com.sergiu.libihb_java.presentation.utils.Constants.MOUNTAIN_API_KEY;
import static com.sergiu.libihb_java.presentation.utils.Constants.MOUNTAIN_RAPID_API;
import static com.sergiu.libihb_java.presentation.utils.Constants.OPEN_WEATHER_BASE_URL;

import com.sergiu.libihb_java.network.MountainApi;
import com.sergiu.libihb_java.network.WeatherApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    @Singleton
    @Provides
    public MountainApi provideMountainApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader(MOUNTAIN_RAPID_API, MOUNTAIN_API_KEY)
                            .addHeader(MOUNTAIN_API_HOST_NAME, MOUNTAIN_API_HOST)
                            .build();
                    return chain.proceed(request);

                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOUNTAIN_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(MountainApi.class);
    }
}
