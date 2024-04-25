package com.sergiu.libihb_java.presentation.fragment.settings;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.EmergencyRepository;
import com.sergiu.libihb_java.data.repository.SettingsRepository;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateName;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidatePhone;
import com.sergiu.libihb_java.presentation.events.SosFromEvent;
import com.sergiu.libihb_java.presentation.fragment.sos.SosFormState;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private final ValidatePhone validatePhone;
    private final ValidateName validateName;
    private final SettingsRepository settingsRepository;
    private final EmergencyRepository emergencyRepository;
    private final MutableLiveData<SosFormState> sosFormState = new MutableLiveData<>(new SosFormState("", "", null, null));
    private final MutableLiveData<Integer> exploreNumberTilesSetting = new MutableLiveData<>();
    private final MutableLiveData<String> unitOfMeasurement = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(
            SettingsRepository settingsRepository,
            EmergencyRepository emergencyRepository,
            ValidatePhone validatePhone,
            ValidateName validateName) {
        this.settingsRepository = settingsRepository;
        this.emergencyRepository = emergencyRepository;
        this.validatePhone = validatePhone;
        this.validateName = validateName;
        getNumberOfTilesSetting();
        getUnitOfMeasurementSetting();
    }

    public LiveData<SosFormState> getSosFormState() {
        return sosFormState;
    }

    public LiveData<Integer> getExploreNumberTilesSetting() {
        return exploreNumberTilesSetting;
    }

    public LiveData<String> getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setExploreNumberTilesSetting(int numberTilesSetting) {
        exploreNumberTilesSetting.setValue(numberTilesSetting);
    }

    public void setUnitOfMeasurement(String unit) {
        unitOfMeasurement.setValue(unit);
    }

    public void onSettingsSave() {
        saveNumberOfTilesSetting();
        saveUnitOfMeasurementSetting();
    }

    public void onEvent(SosFromEvent event) {
        if (event instanceof SosFromEvent.NameChanged) {
            sosFormState.setValue(
                    new SosFormState(((SosFromEvent.NameChanged) event).name,
                            sosFormState.getValue().getPhone(),
                            null,
                            sosFormState.getValue().getPhoneError()
                    ));
        }
        if (event instanceof SosFromEvent.PhoneChanged) {
            sosFormState.setValue(
                    new SosFormState(sosFormState.getValue().getName(),
                            ((SosFromEvent.PhoneChanged) event).phone,
                            sosFormState.getValue().getNameError(),
                            null));
        }
        if (event == SosFromEvent.SubmitClicked) {
            onContactSave();
        }
    }

    private void onContactSave() {
        ValidateResult phoneValid = validatePhone.validate(Objects.requireNonNull(sosFormState.getValue()).getPhone());
        ValidateResult nameValid = validateName.validate(sosFormState.getValue().getName());
        if (!nameValid.isValid() || !phoneValid.isValid()) {
            sosFormState.setValue(new SosFormState(
                    sosFormState.getValue().getName(),
                    sosFormState.getValue().getPhone(),
                    nameValid.getMessageIfNotValid(),
                    phoneValid.getMessageIfNotValid()));

        } else {
            emergencyRepository.writeEmergencyContact(sosFormState.getValue().getName(), sosFormState.getValue().getPhone());
        }
    }

    private void saveNumberOfTilesSetting() {
        Integer numberOfTiles = exploreNumberTilesSetting.getValue();
        if (numberOfTiles != null) {
            settingsRepository.saveExploreNumberTilesSetting(numberOfTiles);
        }
    }

    @SuppressLint("CheckResult")
    private void getNumberOfTilesSetting() {
        settingsRepository.getExploreTitleSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exploreNumberTilesSetting::setValue);
    }

    private void saveUnitOfMeasurementSetting() {
        String unit = unitOfMeasurement.getValue();
        if (unit != null) {
            settingsRepository.saveUnitOfMeasurementSetting(unit);
        }
    }

    @SuppressLint("CheckResult")
    private void getUnitOfMeasurementSetting() {
        settingsRepository.getUnitOfMeasurementSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unitOfMeasurement::setValue);
    }
}
