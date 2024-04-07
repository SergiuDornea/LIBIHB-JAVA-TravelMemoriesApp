package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MemoryOverviewViewModel extends ViewModel {
    private static final String TAG = MemoryOverviewViewModel.class.getSimpleName();
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public MemoryOverviewViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public LiveData<String> observePlaceLocationName() {
        return memoriesRoomRepository.observePlaceLocationName();
    }

    public LiveData<List<String>> observeListOfImgUri() {
        return memoriesRoomRepository.observeListOfImgUri();
    }

    public LiveData<String> observeMemoryName() {
        return memoriesRoomRepository.observeMemoryName();
    }

    public LiveData<String> observeMemoryDescription() {
        return memoriesRoomRepository.observeMemoryDescription();
    }

    public LiveData<LatLng> observeCoordinates() {
        return memoriesRoomRepository.observeCoordinates();
    }

    public LiveData<Date> observeDateOfTravel() {
        return memoriesRoomRepository.observeDateOfTravel();
    }

    @SuppressLint("CheckResult")
    public void saveMemory() {
        memoriesRoomRepository.insertTravelMemory(
                        new TravelMemory(
                                memoriesRoomRepository.getImageList(),
                                memoriesRoomRepository.getMemoryName(),
                                memoriesRoomRepository.getMemoryDescription(),
                                memoriesRoomRepository.getCoordinates(),
                                memoriesRoomRepository.getDateOfTravel(),
                                memoriesRoomRepository.getPlaceLocationName(),
                                memoriesRoomRepository.getPlaceCountryName(),
                                memoriesRoomRepository.getPlaceAdminName()
                        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "saveMemory: memory saveeeed "),
                        throwable -> Log.e(TAG, "Error saving memory", throwable));
    }
}