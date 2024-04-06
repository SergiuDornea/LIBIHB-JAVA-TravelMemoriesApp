package com.sergiu.libihb_java.presentation.fragment.details;

import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MemoryDetailsViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public MemoryDetailsViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }
}