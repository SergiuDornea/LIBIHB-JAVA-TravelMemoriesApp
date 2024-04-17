package com.sergiu.libihb_java.presentation.fragment.map;

import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapsViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;

    @Inject
    public MapsViewModel(MemoriesRepository memoriesRepository) {
        this.memoriesRepository = memoriesRepository;
    }

    public void onEvent(MemoryFormEvent formEvent) {
        memoriesRepository.onEvent(formEvent);
    }
}
