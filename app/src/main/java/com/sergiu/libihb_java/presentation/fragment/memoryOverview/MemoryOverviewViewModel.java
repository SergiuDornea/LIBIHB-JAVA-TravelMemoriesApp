package com.sergiu.libihb_java.presentation.fragment.memoryOverview;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryCoordinates;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryDate;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryDescription;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryImgList;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryName;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MemoryOverviewViewModel extends ViewModel {
    private static final String TAG = MemoryOverviewViewModel.class.getSimpleName();
    private final MemoriesRoomRepository memoriesRoomRepository;
    private final MutableLiveData<MemoryFormState> formState;
    private final ValidateMemoryImgList validateMemoryImgList;
    private final ValidateMemoryName validateMemoryName;
    private final ValidateMemoryDescription validateMemoryDescription;
    private final ValidateMemoryCoordinates validateMemoryCoordinates;
    private final ValidateMemoryDate validateMemoryDate;
    private final MutableLiveData<SaveMemoryClickedEvent> saveMemoryClickedEvent = new MutableLiveData<>();

    @Inject
    public MemoryOverviewViewModel(
            MemoriesRoomRepository memoriesRoomRepository,
            ValidateMemoryImgList validateMemoryImgList,
            ValidateMemoryName validateMemoryName,
            ValidateMemoryDescription validateMemoryDescription,
            ValidateMemoryCoordinates validateMemoryCoordinates,
            ValidateMemoryDate validateMemoryDate) {
        this.memoriesRoomRepository = memoriesRoomRepository;
        this.formState = (MutableLiveData<MemoryFormState>) memoriesRoomRepository.getFormState();
        this.validateMemoryImgList = validateMemoryImgList;
        this.validateMemoryName = validateMemoryName;
        this.validateMemoryDescription = validateMemoryDescription;
        this.validateMemoryCoordinates = validateMemoryCoordinates;
        this.validateMemoryDate = validateMemoryDate;
        this.memoriesRoomRepository.setSubmitCallback(this::onSubmit);
    }

    public void onEvent(MemoryFormEvent memoryFormEvent) {
        memoriesRoomRepository.onEvent(memoryFormEvent);
    }

    public MutableLiveData<SaveMemoryClickedEvent> getSaveMemoryClickedEvent() {
        return saveMemoryClickedEvent;
    }

    public LiveData<MemoryFormState> observeMemoryFormState() {
        return memoriesRoomRepository.getFormState();
    }

    private void onSubmit() {
        Log.d(TAG, "onSubmit: Submit button clicked");
        ValidateResult listValid = validateMemoryImgList.validate(formState.getValue().getListOfImgUri());
        ValidateResult memoryNameValid = validateMemoryName.validate(formState.getValue().getMemoryName());
        ValidateResult memoryDescriptionValid = validateMemoryDescription.validate(formState.getValue().getMemoryDescription());
        ValidateResult memoryCoordinatesValid = validateMemoryCoordinates.validate(formState.getValue().getCoordinates());
        ValidateResult memoryDateValid = validateMemoryDate.validate(formState.getValue().getDateOfTravel());

        if (!listValid.isValid()
                || !memoryNameValid.isValid()
                || !memoryDescriptionValid.isValid()
                || !memoryCoordinatesValid.isValid()
                || !memoryDateValid.isValid()) {
            memoriesRoomRepository.updateFormState(
                    new MemoryFormState(
                            formState.getValue().getListOfImgUri(),
                            formState.getValue().getMemoryName(),
                            formState.getValue().getMemoryDescription(),
                            formState.getValue().getPlaceLocationName(),
                            formState.getValue().getPlaceCountryName(),
                            formState.getValue().getPlaceAdminName(),
                            formState.getValue().getCoordinates(),
                            formState.getValue().getDateOfTravel(),
                            listValid.getMessageIfNotValid(),
                            memoryNameValid.getMessageIfNotValid(),
                            memoryDescriptionValid.getMessageIfNotValid(),
                            null,
                            null,
                            null,
                            memoryCoordinatesValid.getMessageIfNotValid(),
                            memoryDateValid.getMessageIfNotValid())
            );
            if (!listValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_IMG_LIST, formState.getValue().getListOfImgUriError()));
            if (!memoryNameValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_NAME, formState.getValue().getMemoryNameError()));
            if (!memoryDescriptionValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_DESCRIPTION, formState.getValue().getMemoryDescriptionError()));
            if (!memoryDateValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_DATE, formState.getValue().getDateOfTravelError()));
            if (!memoryCoordinatesValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_COORDINATES, formState.getValue().getCoordinatesError()
                ));
        } else {
            saveMemory();
            saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(null, null));
        }
    }

    @SuppressLint("CheckResult")
    private void saveMemory() {
        memoriesRoomRepository.insertTravelMemory(
                        new TravelMemory(
                                formState.getValue().getListOfImgUri(),
                                formState.getValue().getMemoryName(),
                                formState.getValue().getMemoryDescription(),
                                formState.getValue().getCoordinates(),
                                formState.getValue().getDateOfTravel(),
                                formState.getValue().getPlaceLocationName(),
                                formState.getValue().getPlaceCountryName(),
                                formState.getValue().getPlaceAdminName()
                        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "saveMemory: memory saved "),
                        throwable -> Log.e(TAG, "Error saving memory", throwable));
    }

    public static class SaveMemoryClickedEvent {
        private final String cause;
        private final String message;

        private SaveMemoryClickedEvent(String cause, String message) {
            this.cause = cause;
            this.message = message;
        }

        public String getCause() {
            return cause;
        }

        public String getMessage() {
            return message;
        }
    }
}