package com.sergiu.libihb_java.presentation.fragment.edit;

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
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.MemoryFormState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class EditViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;
    private final ValidateMemoryImgList validateMemoryImgList;
    private final ValidateMemoryName validateMemoryName;
    private final ValidateMemoryDescription validateMemoryDescription;
    private final ValidateMemoryCoordinates validateMemoryCoordinates;
    private final ValidateMemoryDate validateMemoryDate;
    private final MutableLiveData<MemoryFormState> formState;
    private final MutableLiveData<SaveEditedMemoryClickedEvent> saveMemoryClickedEvent = new MutableLiveData<>();
    private String id;

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

    public void setId(String id) {
        this.id = id;
    }

    public MutableLiveData<MemoryFormState> observeFormState() {
        return formState;
    }

    public MemoryFormState getFormState() {
        return formState.getValue();
    }

    public void setMemoryFormState(MemoryFormState memoryFormState) {
        memoriesRepository.setFormState(memoryFormState);
    }

    public LiveData<SaveEditedMemoryClickedEvent> getSaveEditedMemoryClickedEvent() {
        return saveMemoryClickedEvent;
    }

    public void onEvent(MemoryFormEvent memoryFormEvent) {
        memoriesRepository.onEvent(memoryFormEvent);
    }

    public Flowable<TravelMemory> getMemoryById(String memoryId) {
        return memoriesRepository.getMemoryById(memoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(formState.getValue().getCoordinatesError()));
            if (!listValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(formState.getValue().getListOfImgUriError()));
            if (!memoryDateValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(formState.getValue().getDateOfTravelError()));
            if (!memoryDescriptionValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(formState.getValue().getMemoryDescriptionError()));
            if (!memoryNameValid.isValid())
                saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(formState.getValue().getMemoryNameError()));
        } else {
            saveEditedMemory();
            saveMemoryClickedEvent.postValue(new SaveEditedMemoryClickedEvent(null));
        }
    }

    private void saveEditedMemory() {
        TravelMemory memory = new TravelMemory(
                formState.getValue().getListOfImgUri(),
                formState.getValue().getMemoryName(),
                formState.getValue().getMemoryDescription(),
                formState.getValue().getLatitude(),
                formState.getValue().getLongitude(),
                formState.getValue().getDateOfTravel(),
                formState.getValue().getPlaceLocationName(),
                formState.getValue().getPlaceCountryName(),
                formState.getValue().getPlaceAdminName());
        memory.setId(id);

        memoriesRepository.updateTravelMemory(memory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static class SaveEditedMemoryClickedEvent {
        private final String message;

        private SaveEditedMemoryClickedEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}