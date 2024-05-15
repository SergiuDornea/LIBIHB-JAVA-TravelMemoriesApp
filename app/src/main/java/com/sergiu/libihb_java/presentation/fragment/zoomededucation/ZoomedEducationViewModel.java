package com.sergiu.libihb_java.presentation.fragment.zoomededucation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.EducationRepository;
import com.sergiu.libihb_java.domain.model.Education;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ZoomedEducationViewModel extends ViewModel {
    private final EducationRepository educationRepository;

    @Inject
    public ZoomedEducationViewModel(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public LiveData<Education> getEducationById(String id) {
        Flowable<Education> flowable = educationRepository.getEducationById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
