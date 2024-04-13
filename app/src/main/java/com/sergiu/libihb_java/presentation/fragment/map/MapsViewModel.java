package com.sergiu.libihb_java.presentation.fragment.map;

import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapsViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;

    @Inject
    public MapsViewModel(MemoriesRoomRepository memoriesRoomRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
    }

    public void onEvent(MemoryFormEvent formEvent) {
        memoriesRoomRepository.onEvent(formEvent);
    }
}
