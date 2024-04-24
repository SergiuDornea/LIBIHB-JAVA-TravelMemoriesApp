package com.sergiu.libihb_java.presentation.fragment.sos;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.data.repository.EmergencyRepository;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateName;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidatePhone;
import com.sergiu.libihb_java.presentation.events.SosFromEvent;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SosViewModel extends ViewModel {
    private final EmergencyRepository emergencyRepository;
    private final ValidatePhone validatePhone;
    private final ValidateName validateName;
    private final MutableLiveData<SosFormState> formState = new MutableLiveData<>(new SosFormState("", "", null, null));
    private final MutableLiveData<Callback> callback = new MutableLiveData<>();

    @Inject
    public SosViewModel(EmergencyRepository emergencyRepository,
                        ValidatePhone validatePhone,
                        ValidateName validateName) {
        this.emergencyRepository = emergencyRepository;
        this.validatePhone = validatePhone;
        this.validateName = validateName;
    }

    public LiveData<Pair<String, String>> observeEmergencyContact() {
        Flowable<Pair<String, String>> flowable = emergencyRepository.getEmergencyContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe();
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

    public LiveData<SosFormState> getFormState() {
        return formState;
    }

    public LiveData<Callback> getCallback() {
        return callback;
    }

    public void onEvent(SosFromEvent event) {
        if (event instanceof SosFromEvent.NameChanged) {
            formState.setValue(
                    new SosFormState(((SosFromEvent.NameChanged) event).name,
                            formState.getValue().getPhone(),
                            null,
                            formState.getValue().getPhoneError()
                    ));
        }
        if (event instanceof SosFromEvent.PhoneChanged) {
            formState.setValue(
                    new SosFormState(formState.getValue().getName(),
                            ((SosFromEvent.PhoneChanged) event).phone,
                            formState.getValue().getNameError(),
                            null));
        }
        if (event == SosFromEvent.SubmitClicked) {
            onSave();
        }
    }

    private void onSave() {
        ValidateResult phoneValid = validatePhone.validate(Objects.requireNonNull(formState.getValue()).getPhone());
        ValidateResult nameValid = validateName.validate(formState.getValue().getName());
        if (!nameValid.isValid() || !phoneValid.isValid()) {
            formState.setValue(new SosFormState(
                    formState.getValue().getName(),
                    formState.getValue().getPhone(),
                    nameValid.getMessageIfNotValid(),
                    phoneValid.getMessageIfNotValid()));

        } else {
            emergencyRepository.writeEmergencyContact(formState.getValue().getName(), formState.getValue().getPhone());
            callback.postValue(new Callback(true));
        }
    }

    public static class Callback {
        private final boolean isSuccess;

        private Callback(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}