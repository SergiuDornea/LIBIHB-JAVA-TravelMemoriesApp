package com.sergiu.libihb_java.presentation.fragment.favorites;

import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoritesViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;

    @Inject
    public FavoritesViewModel(MemoriesRepository memoriesRepository) {
        this.memoriesRepository = memoriesRepository;
    }
}