package com.sergiu.libihb_java.presentation.fragment.explore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.EducationRepository;
import com.sergiu.libihb_java.data.repository.MountainRepository;
import com.sergiu.libihb_java.domain.model.Education;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ExploreViewModel extends ViewModel {
    private final MountainRepository mountainRepository;
    private final EducationRepository educationRepository;

    @Inject
    public ExploreViewModel(MountainRepository mountainRepository, EducationRepository educationRepository) {
        this.mountainRepository = mountainRepository;
        this.educationRepository = educationRepository;
    }

    public LiveData<List<CurrentMountain>> getDiscoverableMountains() {
        Flowable<List<CurrentMountain>> flowable = mountainRepository.getAllMountains()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

    public LiveData<List<Education>> getAllEducationData() {
        Flowable<List<Education>> flowable = educationRepository.getAllEducationData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
