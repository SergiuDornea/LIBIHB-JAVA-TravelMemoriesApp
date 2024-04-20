package com.sergiu.libihb_java.di;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.sergiu.libihb_java.data.datastore.DiskDataStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataStoreModule {
    private static final String PREFERENCES_DATA_STORE = "preferences_data_store";

    @Provides
    @Singleton
    public RxDataStore<Preferences> provideRxDataStore(Context context) {
        return new RxPreferenceDataStoreBuilder(context, PREFERENCES_DATA_STORE).build();
    }

    @Provides
    public DiskDataStore provideDiskDataStore(RxDataStore<Preferences> dataStore) {
        return new DiskDataStore(dataStore);
    }
}
