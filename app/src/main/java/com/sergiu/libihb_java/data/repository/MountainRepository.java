package com.sergiu.libihb_java.data.repository;

import com.sergiu.libihb_java.LIBIHBApp;
import com.sergiu.libihb_java.data.datasource.MountainRemoteDataSource;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.domain.model.mountain.MountainResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class MountainRepository {
    private final MountainRemoteDataSource mountainRemoteDataSource;

    @Inject
    public MountainRepository(MountainRemoteDataSource mountainRemoteDataSource) {
        this.mountainRemoteDataSource = mountainRemoteDataSource;
    }

    public Flowable<MountainResult> getAllMountains() {
        return mountainRemoteDataSource.getAllMountains();
    }
}
