package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.data.datastore.DiskDataStore;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class SettingsRepository {
    private final DiskDataStore diskDataStore;

    @Inject
    public SettingsRepository(DiskDataStore diskDataStore) {
        this.diskDataStore = diskDataStore;
    }

    public void saveDiscoverNumberTilesSetting(int numberOfTiles) {
        diskDataStore.writeDiscoverTitleSetting(numberOfTiles);
    }

    public Flowable<Integer> getDiscoverTitleSetting() {
        return diskDataStore.getDiscoverTitleSetting();
    }

    public void saveUnitOfMeasurementSetting(String unit) {
        diskDataStore.writeUnitOfMeasurementSetting(unit);
    }

    public Flowable<String> getUnitOfMeasurementSetting() {
        return diskDataStore.getUnitOfMeasurementSetting();
    }
}
