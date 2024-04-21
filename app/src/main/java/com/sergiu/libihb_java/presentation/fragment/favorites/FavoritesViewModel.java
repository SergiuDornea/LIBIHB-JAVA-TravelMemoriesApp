package com.sergiu.libihb_java.presentation.fragment.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FavoritesViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;

    @Inject
    public FavoritesViewModel(MemoriesRepository memoriesRepository) {
        this.memoriesRepository = memoriesRepository;
    }

    public LiveData<List<TravelMemory>> getFavoriteMemories() {
        Flowable<List<TravelMemory>> flowable = memoriesRepository.getAllFavoriteMemories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}