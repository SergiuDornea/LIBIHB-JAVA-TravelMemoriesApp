package com.sergiu.libihb_java.presentation.fragment.explore;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ExploreViewModel extends ViewModel {
    private final MountainRepository mountainRepository;

    @Inject
    public ExploreViewModel(MountainRepository mountainRepository) {
        this.mountainRepository = mountainRepository;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<CurrentMountain>> getDiscoverableMountains() {
        Flowable<List<CurrentMountain>> flowable = mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
