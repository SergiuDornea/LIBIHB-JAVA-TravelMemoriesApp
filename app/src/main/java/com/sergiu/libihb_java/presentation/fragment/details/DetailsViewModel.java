package com.sergiu.libihb_java.presentation.fragment.details;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRepository;
import com.sergiu.libihb_java.data.repository.SettingsRepository;
import com.sergiu.libihb_java.data.repository.WeatherRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DetailsViewModel extends ViewModel {
    private final MemoriesRepository memoriesRepository;
    private final WeatherRepository weatherRepository;
    private final SettingsRepository settingsRepository;
    private final MutableLiveData<Boolean> isMemoryInFavorites = new MutableLiveData<>(false);
    private final MutableLiveData<String> unitOfMeasurement = new MutableLiveData<>();

    @Inject
    public DetailsViewModel(
            MemoriesRepository memoriesRepository,
            WeatherRepository weatherRepository,
            SettingsRepository settingsRepository) {
        this.memoriesRepository = memoriesRepository;
        this.weatherRepository = weatherRepository;
        this.settingsRepository = settingsRepository;
        getUnitOfMeasurementSetting();
    }

    public LiveData<Boolean> getIsMemoryInFavorites() {
        return isMemoryInFavorites;
    }

    public Flowable<TravelMemory> getMemoryById(String memoryId) {
        return memoriesRepository.getMemoryById(memoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public LiveData<CurrentWeather> getCurrentWeatherByLatAndLong(LatLng latLng) {
        String latitude = Double.toString(latLng.latitude);
        String longitude = Double.toString(latLng.longitude);

        Flowable<CurrentWeather> weatherFlowable = weatherRepository
                .getCurrentWeatherByLatAndLng(latitude, longitude, unitOfMeasurement.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        weatherFlowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(weatherFlowable);
    }

    public void deleteMemory(TravelMemory travelMemory) {
        memoriesRepository.deleteTravelMemory(travelMemory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    public void loadIsCurrentMemoryInFavorites(String id) {
        memoriesRepository.isMemoryInFavorites(id).distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isMemoryInFavorites::setValue);
    }

    public void toggleFavoriteIcon(String id) {
        memoriesRepository.updateIsFavorite(id, Boolean.FALSE.equals(isMemoryInFavorites.getValue()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    private void getUnitOfMeasurementSetting() {
        settingsRepository.getUnitOfMeasurementSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unitOfMeasurement::setValue);
    }
}