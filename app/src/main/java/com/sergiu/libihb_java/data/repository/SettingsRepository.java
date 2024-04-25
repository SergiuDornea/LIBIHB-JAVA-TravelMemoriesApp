package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.data.datastore.DiskDataStore;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class SettingsRepository {
    private final DiskDataStore diskDataStore;

    @Inject
    public SettingsRepository(DiskDataStore diskDataStore) {
        this.diskDataStore = diskDataStore;
    }

    public Completable saveDiscoverNumberTilesSetting(int numberOfTiles) {
        return diskDataStore.writeDiscoverTitleSetting(numberOfTiles);
    }

    public Flowable<Integer> getDiscoverTitleSetting() {
        return diskDataStore.getDiscoverTitleSetting();
    }

    public Completable saveUnitOfMeasurementSetting(String unit) {
        return diskDataStore.writeUnitOfMeasurementSetting(unit);
    }

    public Flowable<String> getUnitOfMeasurementSetting() {
        return diskDataStore.getUnitOfMeasurementSetting();
    }
}
