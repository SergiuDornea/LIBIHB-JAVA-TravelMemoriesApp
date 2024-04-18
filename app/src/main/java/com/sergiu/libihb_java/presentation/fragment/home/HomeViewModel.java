package com.sergiu.libihb_java.presentation.fragment.home;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;
import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.domain.model.mountain.MountainResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;
    private final MountainRepository mountainRepository;
    private final MutableLiveData<List<TravelMemory>> memoriesLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel(MemoriesRepository memoriesRepository, MountainRepository mountainRepository) {
        this.memoriesRepository = memoriesRepository;
        this.mountainRepository = mountainRepository;
        observeMemories();
    }

    @SuppressLint("CheckResult")
    public void observeMemories() {
        memoriesRepository.getMemories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoriesLiveData::setValue);
    }

    public LiveData<List<TravelMemory>> getMemoriesLiveData() {
        return memoriesLiveData;
    }

    public LiveData<MountainResult> getAllMountains() {
        Flowable<MountainResult> currentMountainFlowable = mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        currentMountainFlowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(currentMountainFlowable);
    }
}
