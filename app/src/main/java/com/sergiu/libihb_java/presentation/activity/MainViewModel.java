package com.sergiu.libihb_java.presentation.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.AuthRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final AuthRepository authRepository;

    @Inject
    public MainViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Completable logOut() {
        return authRepository.writeIsLoggedIn(false);
    }
}
