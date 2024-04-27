package com.sergiu.libihb_java.presentation.fragment.search;

import static com.sergiu.libihb_java.presentation.utils.Constants.INVALID_POSITION;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
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
public class SearchViewModel extends ViewModel {
    private final MountainRepository mountainRepository;
    private final MutableLiveData<List<CurrentMountain>> allMountains = new MutableLiveData<>();

    @Inject
    public SearchViewModel(MountainRepository mountainRepository) {
        this.mountainRepository = mountainRepository;
        getAllMountains();
    }

    public LiveData<List<CurrentMountain>> searchCurrentMountainByName(String name) {
        Flowable<List<CurrentMountain>> flowable = mountainRepository.searchCurrentMountainByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }


    @SuppressLint("CheckResult")
    public void getAllMountains() {
        mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allMountains::setValue);
    }

    public int getPositionById(String id) {
        for (CurrentMountain mountain : allMountains.getValue()) {
            if (mountain.getId().equals(id)) {
                return allMountains.getValue().indexOf(mountain);
            }
        }
        return INVALID_POSITION;
    }
}