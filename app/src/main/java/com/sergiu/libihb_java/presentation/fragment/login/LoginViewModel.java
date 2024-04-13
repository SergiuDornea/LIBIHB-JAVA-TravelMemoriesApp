package com.sergiu.libihb_java.presentation.fragment.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.domain.use_case_validate.auth.ValidateEmailLogin;
import com.sergiu.libihb_java.domain.use_case_validate.auth.ValidatePasswordLogin;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.LoginFormEvent;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final ValidateEmailLogin validateEmailLogin;
    private final ValidatePasswordLogin validatePasswordLogin;
    private final MutableLiveData<LoginFormState> formState = new MutableLiveData<>(new LoginFormState("", "", null, null));
    private final MutableLiveData<NavigationEvent> navigationEvent = new MutableLiveData<>();
    private final AuthRepository authRepository;

    @Inject
    public LoginViewModel(ValidateEmailLogin validateEmailLogin, ValidatePasswordLogin validatePasswordLogin, AuthRepository authRepository) {
        this.validateEmailLogin = validateEmailLogin;
        this.validatePasswordLogin = validatePasswordLogin;
        this.authRepository = authRepository;
    }

    public LiveData<LoginFormState> observeFormState() {
        return formState;
    }

    public LiveData<LoginViewModel.NavigationEvent> getNavigationEvent() {
        return navigationEvent;
    }

    public void onEvent(LoginFormEvent event) {
        if (event instanceof LoginFormEvent.PasswordChanged) {
            updateFormState(new LoginFormState(
                    ((LoginFormEvent.PasswordChanged) event).password,
                    Objects.requireNonNull(formState.getValue()).getEmail(),
                    null,
                    formState.getValue().getEmailError()));
        }
        if (event instanceof LoginFormEvent.EmailChanged) {
            updateFormState(new LoginFormState(
                    Objects.requireNonNull(formState.getValue()).getPassword(),
                    ((LoginFormEvent.EmailChanged) event).email,
                    formState.getValue().getPasswordError(),
                    null));
        }
        if (event == LoginFormEvent.LoginClicked) {
            onLogin();
        }
    }

    private void updateFormState(LoginFormState newState) {
        formState.setValue(newState);
    }

    private void onLogin() {
        ValidateResult passValid = validatePasswordLogin.validate(Objects.requireNonNull(formState.getValue()).getPassword());
        ValidateResult emailValid = validateEmailLogin.validate(formState.getValue().getEmail());

        if (!passValid.isValid() || !emailValid.isValid()) {
            updateFormState(new LoginFormState(formState.getValue().getPassword(), formState.getValue().getEmail(), passValid.getMessageIfNotValid(), emailValid.getMessageIfNotValid()));
        } else {
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
                            navigationEvent.postValue(new NavigationEvent(R.id.loginFragment));
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
