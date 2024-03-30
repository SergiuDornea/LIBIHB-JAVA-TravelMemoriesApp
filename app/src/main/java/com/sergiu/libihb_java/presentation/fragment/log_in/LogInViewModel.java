package com.sergiu.libihb_java.presentation.fragment.log_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.data.repository.AuthRepository;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.LogInFormEvent;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LogInViewModel extends ViewModel {
    private final ValidateEmail validateEmail;
    private final ValidatePassword validatePassword;
    private final MutableLiveData<LogInFormState> formState = new MutableLiveData<>(new LogInFormState("", "", "", ""));
    private final MutableLiveData<LogInViewModel.NavigationEvent> navigationEvent = new MutableLiveData<>();
    private final AuthRepository authRepository;

    @Inject
    public LogInViewModel(ValidateEmail validateEmail, ValidatePassword validatePassword, AuthRepository authRepository) {
        this.validateEmail = validateEmail;
        this.validatePassword = validatePassword;
        this.authRepository = authRepository;
    }

    public LiveData<LogInFormState> getFormState() {
        return formState;
    }

    public LiveData<LogInViewModel.NavigationEvent> getNavigationEvent() {
        return navigationEvent;
    }

    public void onEvent(LogInFormEvent event) {
        if (event instanceof LogInFormEvent.PasswordChanged) {
            updateFormState(new LogInFormState(((LogInFormEvent.PasswordChanged) event).password, Objects.requireNonNull(formState.getValue()).getEmail(), null, formState.getValue().getEmailError()));
        }
        if (event instanceof LogInFormEvent.EmailChanged) {
            updateFormState(new LogInFormState(Objects.requireNonNull(formState.getValue()).getPassword(), ((LogInFormEvent.EmailChanged) event).email, formState.getValue().getPasswordError(), null));
        }
        if (event == LogInFormEvent.LoginClicked) {
            onLogin();
        }
    }

    private void updateFormState(LogInFormState newState) {
        formState.setValue(newState);
    }

    private void onLogin() {
        ValidateResult passValid = validatePassword.validate(Objects.requireNonNull(formState.getValue()).getPassword());
        ValidateResult emailValid = validateEmail.validate(formState.getValue().getEmail());

        if (!passValid.isValid() || !emailValid.isValid()) {
            updateFormState(new LogInFormState(formState.getValue().getPassword(), formState.getValue().getEmail(), passValid.getMessageIfNotValid(), emailValid.getMessageIfNotValid()));
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
                            navigationEvent.postValue(new NavigationEvent(R.id.logInFragment));
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
