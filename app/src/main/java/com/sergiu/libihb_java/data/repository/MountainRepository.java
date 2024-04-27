package com.sergiu.libihb_java.data.repository;

import android.util.Log;

import com.sergiu.libihb_java.data.dao.CurrentMountainDao;
import com.sergiu.libihb_java.data.datasource.MountainRemoteDataSource;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MountainRepository {
    private static final String TAG = MountainRepository.class.getSimpleName();
    private final MountainRemoteDataSource mountainRemoteDataSource;
    private final CurrentMountainDao currentMountainDao;
    private final DiskDataStore diskDataStore;

    @Inject
    public MountainRepository(
            MountainRemoteDataSource mountainRemoteDataSource,
            CurrentMountainDao currentMountainDao,
            DiskDataStore diskDataStore
    ) {
        this.mountainRemoteDataSource = mountainRemoteDataSource;
        this.currentMountainDao = currentMountainDao;
        this.diskDataStore = diskDataStore;
    }

    public Flowable<List<CurrentMountain>> getAllMountains() {
        if (dataIsExpired(diskDataStore.getDiscoverExpireDate())) {
            diskDataStore.writeDiscoverExpireDate();
            return mountainRemoteDataSource.getAllCurrentMountains()
                    .observeOn(Schedulers.io())
                    .flatMap(data -> Completable.fromAction(() -> {
                        for (CurrentMountain mountain : data) {
                            currentMountainDao.insertCurrentMountain(mountain).blockingAwait();
                        }
                    }).andThen(currentMountainDao.getCurrentMountainList()));
        } else {
            return currentMountainDao.getCurrentMountainList();
        }
    }

    public Flowable<CurrentMountain> getCurrentMountainById(String id) {
        if (dataIsExpired(diskDataStore.getDiscoverExpireDate())) {
            diskDataStore.writeDiscoverExpireDate();
            return mountainRemoteDataSource.getCurrentMountainById(id)
                    .doOnNext(currentMountainDao::insertCurrentMountain);
        } else {
            return currentMountainDao.getCurrentMountainById(id);
        }
    }

    private boolean dataIsExpired(Date date) {
        return new Date().after(date);
    }
}
