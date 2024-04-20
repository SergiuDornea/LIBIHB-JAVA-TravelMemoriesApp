package com.sergiu.libihb_java.presentation.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DiscoverViewModel extends ViewModel {
    private final MountainRepository mountainRepository;

    @Inject
    public DiscoverViewModel(MountainRepository mountainRepository) {
        this.mountainRepository = mountainRepository;
    }

    public LiveData<CurrentMountain> observeCurrentMountainById(String id) {
        Flowable<CurrentMountain> flowable = mountainRepository.getCurrentMountainById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
