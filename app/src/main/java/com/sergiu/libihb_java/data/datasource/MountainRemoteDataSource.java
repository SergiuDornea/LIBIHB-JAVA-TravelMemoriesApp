package com.sergiu.libihb_java.data.datasource;

import android.util.Log;

import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.domain.model.mountain.MountainResult;
import com.sergiu.libihb_java.network.MountainApi;
import com.sergiu.libihb_java.presentation.utils.MapperUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public class MountainRemoteDataSource {
    private static final String TAG = MountainRemoteDataSource.class.getSimpleName();
    private final MountainApi mountainApi;

    @Inject
    public MountainRemoteDataSource(MountainApi mountainApi) {
        this.mountainApi = mountainApi;
    }

    public @NonNull Flowable<MountainResult> getAllMountains() {
        return mountainApi.getAllMountains()
                .doOnError(throwable -> Log.e(TAG, "Error: " + throwable));
    }
}
