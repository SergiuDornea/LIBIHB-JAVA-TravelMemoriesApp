package com.sergiu.libihb_java.presentation.fragment.home;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;
    private final MountainRepository mountainRepository;
    private final MutableLiveData<List<TravelMemory>> memoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<CurrentMountain>> mountainsLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel(MemoriesRepository memoriesRepository, MountainRepository mountainRepository) {
        this.memoriesRepository = memoriesRepository;
        this.mountainRepository = mountainRepository;
        observeMemories();
        observeDiscoverableMountains();
    }

    @SuppressLint("CheckResult")
    public void observeMemories() {
        memoriesRepository.getMemories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoriesLiveData::setValue);
    }

    @SuppressLint("CheckResult")
    public void observeDiscoverableMountains() {
        mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mountainsLiveData::setValue);
    }

    public LiveData<List<TravelMemory>> getMemoriesLiveData() {
        return memoriesLiveData;
    }

    public LiveData<List<CurrentMountain>> getMountainLiveData() {
        return mountainsLiveData;
    }
}
