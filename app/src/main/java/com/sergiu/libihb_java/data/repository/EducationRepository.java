package com.sergiu.libihb_java.data.repository;

import android.util.Log;

import com.sergiu.libihb_java.data.datasource.EducationRemoteDataSource;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;
import com.sergiu.libihb_java.domain.model.Education;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EducationRepository {
    private final DiskDataStore diskDataStore;
    private final EducationRemoteDataSource educationRemoteDataSource;

    @Inject
    public EducationRepository(DiskDataStore diskDataStore, EducationRemoteDataSource educationRemoteDataSource) {
        this.diskDataStore = diskDataStore;
        this.educationRemoteDataSource = educationRemoteDataSource;
    }

    public Flowable<List<Education>> getAllEducationData() {
        if (dataIsExpired(diskDataStore.getEducationExpireDate())) {
        Log.d("rem", "getAllEducationData: REMOTE education");
        diskDataStore.writeEducationExpireDate();
        return educationRemoteDataSource.getEducationData()
                .observeOn(Schedulers.io())
                .flatMapCompletable(data -> Completable.fromAction(() -> {
                    Log.d("rem", "getAllEducationData: data " + data.get(0).getInCaseOf());
                    diskDataStore.writeEducationList(data);
                }))
                .andThen(diskDataStore.getEducationList());
        } else {
            Log.d("rem", "getAllEducationData: LOCAL education");
            return diskDataStore.getEducationList();
        }
    }

    private boolean dataIsExpired(Date date) {
        return new Date().after(date);
    }
}
