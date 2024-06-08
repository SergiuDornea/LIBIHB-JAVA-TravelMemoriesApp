package com.sergiu.libihb_java.presentation.fragment.home;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.data.repository.SettingsRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.User;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

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
    private final SettingsRepository settingsRepository;
    private final AuthRepository authRepository;
    private final MutableLiveData<Integer> numberOfTilesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TravelMemory>> memoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<CurrentMountain>> mountainsLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel(
            MemoriesRepository memoriesRepository,
            MountainRepository mountainRepository,
            SettingsRepository settingsRepository,
            AuthRepository authRepository) {
        this.memoriesRepository = memoriesRepository;
        this.mountainRepository = mountainRepository;
        this.settingsRepository = settingsRepository;
        this.authRepository = authRepository;
        observeMemories();
        observeDiscoverableMountains();
        getDiscoverNumberOfTileSetting();
    }

    public void resetForm() {
        memoriesRepository.resetFormState();
    }

    public LiveData<List<TravelMemory>> getMemoriesLiveData() {
        return memoriesLiveData;
    }

    public LiveData<List<CurrentMountain>> getMountainLiveData() {
        return mountainsLiveData;
    }

    public LiveData<Integer> getNumberOfTilesLiveData() {
        return numberOfTilesLiveData;
    }

    public LiveData<User> getUserDetails() {
        Flowable<User> flowable = authRepository.getCurrentUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

    @SuppressLint("CheckResult")
    public void observeDiscoverableMountains() {
        mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mountainsLiveData::setValue);
    }

    @SuppressLint("CheckResult")
    private void observeMemories() {
        memoriesRepository.getMemories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memoriesLiveData::setValue);
    }

    @SuppressLint("CheckResult")
    private void getDiscoverNumberOfTileSetting() {
        settingsRepository.getDiscoverTitleSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(numberOfTilesLiveData::setValue);
    }
}
