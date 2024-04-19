package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.data.dao.CurrentMountainDao;
import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.data.datasource.MountainRemoteDataSource;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class MountainRepository {
    private final MountainRemoteDataSource mountainRemoteDataSource;
    private final CurrentMountainDao currentMountainDao;

    @Inject
    public MountainRepository(
            MountainRemoteDataSource mountainRemoteDataSource,
            CurrentMountainDao currentMountainDao
    ) {
        this.mountainRemoteDataSource = mountainRemoteDataSource;
        this.currentMountainDao = currentMountainDao;
    }

    public Flowable<List<CurrentMountain>> getAllMountains() {
        return mountainRemoteDataSource.getAllMountains();
    }
}
