package com.sergiu.libihb_java.presentation.activity;

import static com.sergiu.libihb_java.presentation.utils.Constants.FAIL;
import static com.sergiu.libihb_java.presentation.utils.Constants.SUCCESS;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.data.repository.MemoriesRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private boolean isFirstLaunch = true;
    private final AuthRepository authRepository;
    private final MemoriesRepository memoriesRepository;
    private final MutableLiveData<UploadProfileImageEvent> uploadProfileImageEvent = new MutableLiveData<>();

    @Inject
    public MainViewModel(AuthRepository authRepository, MemoriesRepository memoriesRepository) {
        this.authRepository = authRepository;
        this.memoriesRepository = memoriesRepository;
    }

    public LiveData<UploadProfileImageEvent> getUploadProfileImageEvent() {
        return uploadProfileImageEvent;
    }

    public boolean isFirstLaunch() {
        return isFirstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        isFirstLaunch = firstLaunch;
    }

    public void logOut() {
        Completable completableResetUserData = memoriesRepository.resetUserData();
        Completable completableLogout = authRepository.writeIsLoggedIn(false);
        Completable.mergeArray(completableResetUserData, completableLogout).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void uploadProfileImage(Uri imgUri) {
        authRepository.uploadProfileImage(imgUri, new AuthRepository.UpdateProfilePictureCallback() {
            @Override
            public void onSuccess() {
                uploadProfileImageEvent.postValue(new UploadProfileImageEvent(SUCCESS));
            }

            @Override
            public void onFailure() {
                uploadProfileImageEvent.postValue(new UploadProfileImageEvent(FAIL));
            }
        });
    }

    public static class UploadProfileImageEvent {
        private final String response;

        private UploadProfileImageEvent(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }
    }
}
