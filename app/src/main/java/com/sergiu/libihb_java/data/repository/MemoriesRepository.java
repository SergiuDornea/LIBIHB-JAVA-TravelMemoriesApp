package com.sergiu.libihb_java.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.MemoryFormState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MemoriesRepository {
    private final TravelMemoryDao dao;
    private SubmitCallback submitCallback;
    private final MutableLiveData<MemoryFormState> formState = new MutableLiveData<>(
            new MemoryFormState(
                    new ArrayList<>(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    null,
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

    @Inject
    public MemoriesRepository(TravelMemoryDao travelMemoryDao) {
        this.dao = travelMemoryDao;
    }

    public void setFormState(MemoryFormState formState) {
        this.formState.setValue(formState);
    }

    public LiveData<MemoryFormState> getFormState() {
        return formState;
    }

    public void setSubmitCallback(SubmitCallback submitCallback) {
        this.submitCallback = submitCallback;
    }

    public void onEvent(MemoryFormEvent event) {
        if (event instanceof MemoryFormEvent.ImgListChanged) {
            updateFormState(new MemoryFormState(
                    ((MemoryFormEvent.ImgListChanged) event).imgList,
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    null,
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryNameChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    ((MemoryFormEvent.MemoryNameChanged) event).memoryName,
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    null,
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryDescriptionChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    ((MemoryFormEvent.MemoryDescriptionChanged) event).memoryDescription,
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    null,
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryPlaceLocationNameChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    ((MemoryFormEvent.MemoryPlaceLocationNameChanged) event).placeLocationName,
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    null,
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryPlaceCountryNameChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    ((MemoryFormEvent.MemoryPlaceCountryNameChanged) event).memoryPlaceCountryName,
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    null,
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryPlaceAdminNameChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    ((MemoryFormEvent.MemoryPlaceAdminNameChanged) event).placeLocationAdminName,
                    formState.getValue().getCoordinates(),
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    null,
                    formState.getValue().getCoordinatesError(),
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryCoordinatesChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    ((MemoryFormEvent.MemoryCoordinatesChanged) event).coordinates,
                    formState.getValue().getDateOfTravel(),
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    null,
                    formState.getValue().getDateOfTravelError()
            ));
        }
        if (event instanceof MemoryFormEvent.MemoryDateOfTravelChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getCoordinates(),
                    ((MemoryFormEvent.MemoryDateOfTravelChanged) event).dateOfTravel,
                    formState.getValue().getListOfImgUriError(),
                    formState.getValue().getMemoryNameError(),
                    formState.getValue().getMemoryDescriptionError(),
                    formState.getValue().getPlaceLocationNameError(),
                    formState.getValue().getPlaceCountryNameError(),
                    formState.getValue().getPlaceAdminNameError(),
                    formState.getValue().getCoordinatesError(),
                    null
            ));
        }
        if (event == MemoryFormEvent.SubmitClicked) {
            if (submitCallback != null) {
                submitCallback.onSubmitClicked();
            }
        }
    }

    public void updateFormState(MemoryFormState newState) {
        formState.setValue(newState);
    }

    public Completable insertTravelMemory(TravelMemory travelMemory) {
        return dao.insertTravelMemory(travelMemory);
    }

    public Flowable<List<TravelMemory>> getMemories() {
        return dao.getMemories();
    }

    public Flowable<TravelMemory> getMemoryById(Long memoryId) {
        return dao.getMemoryById(memoryId);
    }

    public Completable deleteTravelMemory(TravelMemory travelMemory) {
        return dao.deleteTravelMemory(travelMemory);
    }

    public Completable updateTravelMemory(TravelMemory travelMemory) {
        return dao.updateTravelMemory(travelMemory);
    }

    public Flowable<Boolean> isMemoryInFavorites(long id) {
        return dao.isMemoryInFavorites(id);
    }

    public Completable updateIsFavorite(long id, boolean isFavorite) {
        return dao.updateIsFavorite(id, isFavorite);
    }

    public interface SubmitCallback {
        void onSubmitClicked();
    }
}
