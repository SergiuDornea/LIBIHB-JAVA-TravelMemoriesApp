package com.sergiu.libihb_java.presentation.activity;

import static com.sergiu.libihb_java.presentation.utils.Constants.FAIL;
import static com.sergiu.libihb_java.presentation.utils.Constants.SUCCESS;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.AuthRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private final AuthRepository authRepository;
    private final MutableLiveData<UploadProfileImageEvent> uploadProfileImageEvent = new MutableLiveData<>();

    @Inject
    public MainViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<UploadProfileImageEvent> getUploadProfileImageEvent() {
        return uploadProfileImageEvent;
    }

    public Completable logOut() {
        return authRepository.writeIsLoggedIn(false);
    }

    public void uploadProfileImage(Uri imgUri) {
        authRepository.uploadProfileImage(imgUri, new AuthRepository.UpdateProfilePictureCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
                uploadProfileImageEvent.postValue(new UploadProfileImageEvent(SUCCESS));
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure");
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
