package com.sergiu.libihb_java.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.sergiu.libihb_java.presentation.utils.JsonConversionUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public JsonConversionUtil provideJsonUtil(Gson gson) {
        return new JsonConversionUtil(gson);
    }
}
