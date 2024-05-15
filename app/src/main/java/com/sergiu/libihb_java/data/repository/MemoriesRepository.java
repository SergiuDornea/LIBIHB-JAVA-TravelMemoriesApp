package com.sergiu.libihb_java.data.repository;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sergiu.libihb_java.data.dao.TravelMemoryDao;
import com.sergiu.libihb_java.data.datasource.MemoriesRemoteDataSource;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.presentation.events.MemoryFormEvent;
import com.sergiu.libihb_java.presentation.fragment.memoryoverview.MemoryFormState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MemoriesRepository {
    private static final String TAG = MemoriesRepository.class.getSimpleName();
    private final TravelMemoryDao dao;
    private final MemoriesRemoteDataSource memoriesRemoteDataSource;
    private final DiskDataStore diskDataStore;
    private SubmitCallback submitCallback;
    private final MutableLiveData<MemoryFormState> formState = new MutableLiveData<>(
            new MemoryFormState(
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

    @Inject
    public MemoriesRepository(
            TravelMemoryDao travelMemoryDao,
            MemoriesRemoteDataSource memoriesRemoteDataSource,
            DiskDataStore diskDataStore) {
        this.dao = travelMemoryDao;
        this.memoriesRemoteDataSource = memoriesRemoteDataSource;
        this.diskDataStore = diskDataStore;
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
        if (event instanceof MemoryFormEvent.MemoryLatChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    ((MemoryFormEvent.MemoryLatChanged) event).latitude,
                    formState.getValue().getLongitude(),
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
        } else if (event instanceof MemoryFormEvent.MemoryLngChanged) {
            updateFormState(new MemoryFormState(
                    formState.getValue().getListOfImgUri(),
                    formState.getValue().getMemoryName(),
                    formState.getValue().getMemoryDescription(),
                    formState.getValue().getPlaceLocationName(),
                    formState.getValue().getPlaceCountryName(),
                    formState.getValue().getPlaceAdminName(),
                    formState.getValue().getLatitude(),
                    ((MemoryFormEvent.MemoryLngChanged) event).longitude,
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
                    formState.getValue().getLatitude(),
                    formState.getValue().getLongitude(),
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
        String id = UUID.randomUUID().toString();
        travelMemory.setId(id);
        return uploadImages(travelMemory.getImageList())
                .flatMapCompletable(imageUrls -> {
                    travelMemory.setImageList(imageUrls);
                    return Completable.defer(() -> {
                        Completable roomCompletable = dao.insertTravelMemory(travelMemory)
                                .onErrorResumeNext(error -> {
                                    Log.e(TAG, "insertTravelMemory: room ERROR ", error);
                                    return Completable.complete();
                                });
                        Completable firestoreCompletable = memoriesRemoteDataSource.insertTravelMemory(travelMemory)
                                .onErrorResumeNext(error -> {
                                    Log.e(TAG, "insertTravelMemory: firestore ERROR ", error);
                                    return Completable.complete();
                                });
                        return Completable.mergeArray(firestoreCompletable, roomCompletable)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    });
                });
    }

    public Flowable<List<TravelMemory>> getMemories() {
        if (dataIsExpired(diskDataStore.getMemoriesExpireDate())) {
            return memoriesRemoteDataSource.getMemories()
                    .toFlowable()
                    .doOnNext(this::updateLocalData)
                    .doOnError(error -> Log.e(TAG, "getMemories: ERROR", error));

        } else {
            return dao.getMemories();
        }
    }

    public Flowable<TravelMemory> getMemoryById(String memoryId) {
        return dao.getMemoryById(memoryId);
    }

    public Completable resetUserData() {
        Completable completableDeleteAll = dao.deleteAll();
        Completable completableResetUserData = diskDataStore.resetUserDataValues();
        return Completable.mergeArray(completableDeleteAll, completableResetUserData);
    }

    public Completable updateTravelMemory(TravelMemory travelMemory) {
        Log.d(TAG, "updateTravelMemory: list size " + travelMemory.getImageList().size());
        return uploadImages(travelMemory.getImageList())
                .flatMapCompletable(imageUrls -> {
                    travelMemory.setImageList(imageUrls);
                    return Completable.defer(() -> {
                        Completable roomCompletable = dao.updateTravelMemory(travelMemory)
                                .onErrorResumeNext(error -> {
                                    Log.e(TAG, "updateTravelMemory: room ERROR ", error);
                                    return Completable.complete();
                                });
                        Completable firestoreCompletable = memoriesRemoteDataSource.updateTravelMemory(travelMemory)
                                .onErrorResumeNext(error -> {
                                    Log.e(TAG, "updateTravelMemory: firestore ERROR ", error);
                                    return Completable.complete();
                                });
                        return Completable.mergeArray(firestoreCompletable, roomCompletable)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    });
                });
    }

    public Completable deleteTravelMemory(TravelMemory travelMemory) {
        return Completable.defer(() -> {
            Completable roomCompletable = dao.deleteTravelMemory(travelMemory)
                    .onErrorResumeNext(error -> {
                        Log.e(TAG, "deleteTravelMemory: room ERROR ", error);
                        return Completable.complete();
                    });
            Completable firestoreCompletable = memoriesRemoteDataSource.deleteTravelMemory(travelMemory)
                    .onErrorResumeNext(error -> {
                        Log.e(TAG, "deleteTravelMemory: firestore ERROR ", error);
                        return Completable.complete();
                    });
            return Completable.mergeArray(firestoreCompletable, roomCompletable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        });
    }

    public Flowable<Boolean> isMemoryInFavorites(String id) {
        return dao.isMemoryInFavorites(id);
    }

    public Completable updateIsFavorite(String id, boolean isFavorite) {
        Completable completableLocal = dao.updateIsFavorite(id, isFavorite);
        Completable completableFb = memoriesRemoteDataSource.updateMemoryFavorite(id, isFavorite);
        return Completable.mergeArray(completableFb, completableLocal);
    }

    public Flowable<List<TravelMemory>> getAllFavoriteMemories() {
        return dao.getAllFavoriteMemories();
    }

    private Single<List<String>> uploadImages(List<String> imageUris) {
        List<String> localUris = new ArrayList<>();
        List<String> firestoreUrls = new ArrayList<>();

        for (String uriString : imageUris) {
            if (isLocalUri(uriString)) {
                localUris.add(uriString);
            } else {
                firestoreUrls.add(uriString);
            }
        }

        Single<List<String>> uploadLocalImagesSingle = localUris.isEmpty() ?
                Single.just(new ArrayList<>()) :
                Observable.fromIterable(localUris)
                        .flatMap(uriString -> memoriesRemoteDataSource.uploadImg(Uri.parse(uriString)).toObservable())
                        .toList();

        return uploadLocalImagesSingle
                .flatMap(localUrls -> Single.just(listConcat(localUrls, firestoreUrls)));
    }

    private boolean isLocalUri(String uriString) {
        return !uriString.startsWith("https://firebasestorage") || !uriString.startsWith("http");
    }

    private List<String> listConcat(List<String> list1, List<String> list2) {
        List<String> listConcat = new ArrayList<>(list1);
        listConcat.addAll(list2);
        return listConcat;
    }

    private void updateLocalData(List<TravelMemory> memoryList) {
        diskDataStore.writeMemoriesExpireDate();
        for (TravelMemory memory : memoryList) {
            dao.insertTravelMemory(memory)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    private boolean dataIsExpired(Date date) {
        return new Date().after(date);
    }

    public interface SubmitCallback {
        void onSubmitClicked();
    }
}
