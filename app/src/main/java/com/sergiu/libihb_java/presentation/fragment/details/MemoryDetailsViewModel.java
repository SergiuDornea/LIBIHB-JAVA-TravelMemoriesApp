package com.sergiu.libihb_java.presentation.fragment.details;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.data.repository.MemoriesRoomRepository;
import com.sergiu.libihb_java.data.repository.WeatherRepository;
import com.sergiu.libihb_java.domain.model.TravelMemory;
import com.sergiu.libihb_java.domain.model.weather.CurrentWeather;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MemoryDetailsViewModel extends ViewModel {
    private final MemoriesRoomRepository memoriesRoomRepository;
    private final WeatherRepository weatherRepository;

    @Inject
    public MemoryDetailsViewModel(MemoriesRoomRepository memoriesRoomRepository, WeatherRepository weatherRepository) {
        this.memoriesRoomRepository = memoriesRoomRepository;
        this.weatherRepository = weatherRepository;
    }

    public Flowable<TravelMemory> getMemoryById(long memoryId) {
        return memoriesRoomRepository.getMemoryById(memoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    public LiveData<CurrentWeather> getCurrentWeatherByLatAndLong(LatLng latLng) {
        String latitude = Double.toString(latLng.latitude);
        String longitude = Double.toString(latLng.longitude);

        Flowable<CurrentWeather> weatherFlowable = weatherRepository
                .getCurrentWeatherByLatAndLng(latitude, longitude, "metric") //todo create units settings
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        weatherFlowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(weatherFlowable);
    }
}