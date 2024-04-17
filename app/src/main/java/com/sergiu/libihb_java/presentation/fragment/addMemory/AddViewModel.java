package com.sergiu.libihb_java.presentation.fragment.addMemory;

import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;

    @Inject
    public AddViewModel(MemoriesRepository memoriesRepository) {
        this.memoriesRepository = memoriesRepository;
    }

    public void onEvent(MemoryFormEvent formEvent) {
        memoriesRepository.onEvent(formEvent);
    }
}
