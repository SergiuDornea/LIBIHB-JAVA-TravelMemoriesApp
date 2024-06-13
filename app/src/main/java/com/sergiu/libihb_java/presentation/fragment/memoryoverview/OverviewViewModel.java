package com.sergiu.libihb_java.presentation.fragment.memoryoverview;

import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_COORDINATES;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DATE;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DEFAULT;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_DESCRIPTION;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_IMG_LIST;
import static com.sergiu.libihb_java.presentation.utils.Constants.CAUSE_NAME;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryCoordinates;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryDate;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryDescription;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryImgList;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryName;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class OverviewViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;
    private final MutableLiveData<MemoryFormState> formState;
    private final ValidateMemoryImgList validateMemoryImgList;
    private final ValidateMemoryName validateMemoryName;
    private final ValidateMemoryDescription validateMemoryDescription;
    private final ValidateMemoryCoordinates validateMemoryCoordinates;
    private final ValidateMemoryDate validateMemoryDate;
    private final MutableLiveData<SaveMemoryClickedEvent> saveMemoryClickedEvent = new MutableLiveData<>();

    @Inject
    public OverviewViewModel(
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

    public MutableLiveData<SaveMemoryClickedEvent> getSaveMemoryClickedEvent() {
        return saveMemoryClickedEvent;
    }

    public LiveData<MemoryFormState> observeMemoryFormState() {
        return memoriesRepository.getFormState();
    }

    public void onEvent(MemoryFormEvent memoryFormEvent) {
        memoriesRepository.onEvent(memoryFormEvent);
    }

    private void onSubmit() {
        ValidateResult listValid = validateMemoryImgList.validate(formState.getValue().getListOfImgUri());
        ValidateResult memoryNameValid = validateMemoryName.validate(formState.getValue().getMemoryName());
        ValidateResult memoryDescriptionValid = validateMemoryDescription.validate(formState.getValue().getMemoryDescription());
        ValidateResult memoryCoordinatesValid = validateMemoryCoordinates.validate(formState.getValue().getPlaceLocationName());
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
                            formState.getValue().getLatitude(),
                            formState.getValue().getLongitude(),
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
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_COORDINATES, formState.getValue().getCoordinatesError()));
            if (!listValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_IMG_LIST, formState.getValue().getListOfImgUriError()));
            if (!memoryDateValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_DATE, formState.getValue().getDateOfTravelError()));
            if (!memoryDescriptionValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_DESCRIPTION, formState.getValue().getMemoryDescriptionError()));
            if (!memoryNameValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_NAME, formState.getValue().getMemoryNameError()));
        } else {
            saveMemory();
            saveMemoryClickedEvent.postValue(new SaveMemoryClickedEvent(CAUSE_DEFAULT, null));
            memoriesRepository.setFormState(new MemoryFormState(
                    new ArrayList<>(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    0,
                    0,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            ));
        }
    }

    private void saveMemory() {
        memoriesRepository.insertTravelMemory(
                        new TravelMemory(
                                formState.getValue().getListOfImgUri(),
                                formState.getValue().getMemoryName(),
                                formState.getValue().getMemoryDescription(),
                                formState.getValue().getLatitude(),
                                formState.getValue().getLongitude(),
                                formState.getValue().getDateOfTravel(),
                                formState.getValue().getPlaceLocationName(),
                                formState.getValue().getPlaceCountryName(),
                                formState.getValue().getPlaceAdminName()
                        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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