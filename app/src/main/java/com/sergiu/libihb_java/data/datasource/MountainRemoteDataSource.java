package com.sergiu.libihb_java.data.datasource;

import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.network.MountainApi;
import com.sergiu.libihb_java.presentation.utils.MapperUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MountainRemoteDataSource {
    private final MountainApi mountainApi;

    @Inject
    public MountainRemoteDataSource(MountainApi mountainApi) {
        this.mountainApi = mountainApi;
    }

    public @NonNull Flowable<List<CurrentMountain>> getAllCurrentMountains() {
        return mountainApi.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mountains -> {
                    List<CurrentMountain> currentMountains = new ArrayList<>();
                    for (Mountain mountain : mountains) {
                        currentMountains.add(MapperUtil.mapMountainToCurrentMountain(mountain));
                    }
                    return currentMountains;
                });
    }

    public Flowable<CurrentMountain> getCurrentMountainById(String id) {
        return mountainApi.getMountainById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MapperUtil::mapMountainToCurrentMountain);
    }
}
