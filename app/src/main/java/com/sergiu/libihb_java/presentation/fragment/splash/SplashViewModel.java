package com.sergiu.libihb_java.presentation.fragment.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.AuthRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SplashViewModel extends ViewModel {
    private final AuthRepository authRepository;

    @Inject
    public SplashViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<Boolean> getIsLoggedIn() {
        Flowable<Boolean> flowable = authRepository.getIsLoggedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
