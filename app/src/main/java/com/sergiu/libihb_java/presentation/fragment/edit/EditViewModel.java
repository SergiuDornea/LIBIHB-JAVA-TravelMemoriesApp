package com.sergiu.libihb_java.presentation.fragment.edit;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DEFAULT;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryCoordinates;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryDate;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryDescription;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryImgList;
import com.sergiu.libihb_java.domain.use_case_validate.addMemory.ValidateMemoryName;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;
import com.sergiu.libihb_java.presentation.fragment.memoryOverview.MemoryFormState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class EditViewModel extends ViewModel {
    private static final String TAG = EditViewModel.class.getSimpleName();
    private final MemoriesRepository memoriesRepository;
    private final ValidateMemoryImgList validateMemoryImgList;
    private final ValidateMemoryName validateMemoryName;
    private final ValidateMemoryDescription validateMemoryDescription;
    private final ValidateMemoryCoordinates validateMemoryCoordinates;
    private final ValidateMemoryDate validateMemoryDate;
    private final MutableLiveData<MemoryFormState> formState;
    private final MutableLiveData<SaveEditedMemoryClickedEvent> saveMemoryClickedEvent = new MutableLiveData<>();

    @Inject
    public EditViewModel(
            MemoriesRepository memoriesRepository,
            ValidateMemoryImgList validateMemoryImgList,
            ValidateMemoryName validateMemoryName,
            ValidateMemoryDescription validateMemoryDescription,
            ValidateMemoryCoordinates validateMemoryCoordinates,
            ValidateMemoryDate validateMemoryDate) {
        this.memoriesRepository = memoriesRepository;
        this.formState = (MutableLiveData<MemoryFormState>) memoriesRepository.getFormState();
        this.validateMemoryImgList = validateMemoryImgList;
        this.validateMemoryName = validateMemoryName;
        this.validateMemoryDescription = validateMemoryDescription;
        this.validateMemoryCoordinates = validateMemoryCoordinates;
        this.validateMemoryDate = validateMemoryDate;
        this.memoriesRepository.setSubmitCallback(this::onSubmit);
    }

    public MutableLiveData<MemoryFormState> getFormState() {
        return formState;
    }

    public LiveData<SaveEditedMemoryClickedEvent> getSaveEditedMemoryClickedEvent() {
        return saveMemoryClickedEvent;
    }

    public void onEvent(MemoryFormEvent memoryFormEvent) {
        memoriesRepository.onEvent(memoryFormEvent);
    }

    public Flowable<TravelMemory> getMemoryById(long memoryId) {
        return memoriesRepository.getMemoryById(memoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
            memoriesRepository.updateFormState(
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
            if (!memoryCoordinatesValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_COORDINATES, formState.getValue().getCoordinatesError()));
            if (!listValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_IMG_LIST, formState.getValue().getListOfImgUriError()));
            if (!memoryDateValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_DATE, formState.getValue().getDateOfTravelError()));
            if (!memoryDescriptionValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_DESCRIPTION, formState.getValue().getMemoryDescriptionError()));
            if (!memoryNameValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_NAME, formState.getValue().getMemoryNameError()));
        } else {
            saveEditedMemory();
            saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(CAUSE_DEFAULT, null));
        }
    }

    @SuppressLint("CheckResult")
    private void saveEditedMemory() {
        memoriesRepository.updateTravelMemory(
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
                .subscribe(() -> Log.d(TAG, "saveEditedMemory: edited memory saved "),
                        throwable -> Log.e(TAG, "Error saving edited memory", throwable));
    }

    public static class SaveEditedMemoryClickedEvent {
        private final String cause;
        private final String message;

        private SaveEditedMemoryClickedEvent(String cause, String message) {
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