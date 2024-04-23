package com.sergiu.libihb_java.data.repository;

import android.util.Pair;

import com.sergiu.libihb_java.data.datastore.DiskDataStore;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class EmergencyRepository {
    private final DiskDataStore diskDataStore;

    @Inject
    public EmergencyRepository(DiskDataStore diskDataStore) {
        this.diskDataStore = diskDataStore;
    }

    public Completable writeEmergencyContact(String name, String phone) {
        return diskDataStore.writeEmergencyContact(name, phone);
    }

    public Flowable<Pair<String, String>> getEmergencyContact() {
        return diskDataStore.getEmergencyContact();
    }
}
