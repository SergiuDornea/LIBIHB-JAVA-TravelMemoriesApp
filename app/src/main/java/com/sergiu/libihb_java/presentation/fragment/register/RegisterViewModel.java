package com.sergiu.libihb_java.presentation.fragment.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateName;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePhone;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateRepeatPassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.RegisterFormEvent;


import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final ValidateEmail validateEmail;
    private final ValidatePassword validatePassword;
    private final ValidatePhone validatePhone;
    private final ValidateName validateName;
    private final ValidateRepeatPassword validateRepeatPassword;
    private final MutableLiveData<RegisterFormState> formState = new MutableLiveData<>(new RegisterFormState("", "", "", "", "", "", "", "", "", ""));
    private final MutableLiveData<NavigationEvent> navigationEvent = new MutableLiveData<>();
    private final AuthRepository authRepository;

    @Inject
    public RegisterViewModel(ValidateEmail validateEmail,
                             ValidatePassword validatePassword,
                             ValidatePhone validatePhone,
                             ValidateName validateName,
                             ValidateRepeatPassword validateRepeatPassword,
                             AuthRepository authRepository) {
        this.validateEmail = validateEmail;
        this.validatePassword = validatePassword;
        this.validatePhone = validatePhone;
        this.validateName = validateName;
        this.validateRepeatPassword = validateRepeatPassword;
        this.authRepository = authRepository;

    }

    public LiveData<RegisterFormState> getFormState() {
        return formState;
    }

    public LiveData<NavigationEvent> getNavigationEvent() {
        return navigationEvent;
    }

    public void onEvent(RegisterFormEvent event) {
        if (event instanceof RegisterFormEvent.PasswordChanged) {
            updateFormState(new RegisterFormState(((RegisterFormEvent.PasswordChanged) event).password, Objects.requireNonNull(formState.getValue()).getEmail(), formState.getValue().getName(), formState.getValue().getPhone(), formState.getValue().getRepeatPassword(), null, formState.getValue().getEmailError(), formState.getValue().getNameError(), formState.getValue().getPhoneError(), formState.getValue().getRepeatPasswordError()));
        }
        if (event instanceof RegisterFormEvent.EmailChanged) {
            updateFormState(new RegisterFormState(Objects.requireNonNull(formState.getValue()).getPassword(), ((RegisterFormEvent.EmailChanged) event).email, formState.getValue().getName(),
                    formState.getValue().getPhone(), formState.getValue().getRepeatPassword(), formState.getValue().getPasswordError(), null, formState.getValue().getNameError(), formState.getValue().getPhoneError(), formState.getValue().getRepeatPasswordError()));
        }
        if (event instanceof RegisterFormEvent.NameChanged) {
            updateFormState(new RegisterFormState(Objects.requireNonNull(formState.getValue()).getPassword(), formState.getValue().getEmail(), ((RegisterFormEvent.NameChanged) event).name,
                    formState.getValue().getPhone(), formState.getValue().getRepeatPassword(), formState.getValue().getPasswordError(), formState.getValue().getEmailError(), null, formState.getValue().getPhoneError(), formState.getValue().getRepeatPasswordError()));
        }
        if (event instanceof RegisterFormEvent.PhoneChanged) {
            updateFormState(new RegisterFormState(Objects.requireNonNull(formState.getValue()).getPassword(), formState.getValue().getEmail(), formState.getValue().getName(),
                    ((RegisterFormEvent.PhoneChanged) event).phone, formState.getValue().getRepeatPassword(), formState.getValue().getPasswordError(), formState.getValue().getEmailError(), formState.getValue().getNameError(), null, formState.getValue().getRepeatPasswordError()));
        }
        if (event instanceof RegisterFormEvent.RepeatedPasswordChanged) {
            updateFormState(new RegisterFormState(Objects.requireNonNull(formState.getValue()).getPassword(), formState.getValue().getEmail(), formState.getValue().getName(),
                    formState.getValue().getPhone(), ((RegisterFormEvent.RepeatedPasswordChanged) event).repeteadPassword, formState.getValue().getPasswordError(), formState.getValue().getEmailError(), formState.getValue().getNameError(), formState.getValue().getPhoneError(), null));
        }
        if (event == RegisterFormEvent.RegisterClicked) {
            onRegister();
        }
    }

    private void updateFormState(RegisterFormState newState) {
        formState.setValue(newState);
    }

    private void onRegister() {
        ValidateResult passValid = validatePassword.validate(Objects.requireNonNull(formState.getValue()).getPassword());
        ValidateResult emailValid = validateEmail.validate(formState.getValue().getEmail());
        ValidateResult phoneValid = validatePhone.validate(formState.getValue().getPhone());
        ValidateResult nameValid = validateName.validate(formState.getValue().getName());
        ValidateResult repeatPasswordValid = validateRepeatPassword.validateEqual(formState.getValue().getRepeatPassword(), formState.getValue().getPassword());

        if (!passValid.isValid() || !emailValid.isValid() || !phoneValid.isValid() || !nameValid.isValid()) {
            updateFormState(new RegisterFormState(
                    formState.getValue().getPassword(),
                    formState.getValue().getEmail(),
                    formState.getValue().getPhone(),
                    formState.getValue().getName(),
                    formState.getValue().getRepeatPassword(),
                    passValid.getMessageIfNotValid(),
                    emailValid.getMessageIfNotValid(),
                    nameValid.getMessageIfNotValid(),
                    phoneValid.getMessageIfNotValid(),
                    repeatPasswordValid.getMessageIfNotValid()));
        } else {
            authRepository.registerUser(
                    formState.getValue().getName(),
                    formState.getValue().getEmail(),
                    formState.getValue().getPhone(),
                    formState.getValue().getPassword(),
                    new AuthRepository.CreateUserCallback() {
                        @Override
                        public void onSuccess() {
                            authRepository.loginUser(
                                    formState.getValue().getEmail(),
                                    formState.getValue().getPassword(),
                                    new AuthRepository.LoginCallback() {
                                        @Override
                                        public void onSuccess() {
                                            navigationEvent.postValue(new NavigationEvent(R.id.mainFragment));
                                        }

                                        @Override
                                        public void onFailure() {
                                            navigationEvent.postValue(new NavigationEvent(R.id.logInFragment));
                                        }
                                    });
                        }

                        @Override
                        public void onFailure() {
                            navigationEvent.postValue(new NavigationEvent(R.id.authFragment));
                        }
                    });
        }
    }

    public static class NavigationEvent {
        private final int destinationId;

        private NavigationEvent(int destinationId) {
            this.destinationId = destinationId;
        }

        public int getDestinationId() {
            return destinationId;
        }
    }
}