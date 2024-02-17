package com.sergiu.libihb_java.presentation.fragment.log_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.LogInFormEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


public class LogInViewModel extends ViewModel {
    private final ValidateEmail validateEmail = new ValidateEmail();
    private final ValidatePassword validatePassword = new ValidatePassword();

    private final MutableLiveData<LogInFormState> formState = new MutableLiveData<>(new LogInFormState(null, null, null, null));
    public LiveData<LogInFormState> getFormState() {
        return formState;
    }

//    @Inject
//    public LogInViewModel(ValidateEmail validateEmail, ValidatePassword validatePassword) {
//        this.validateEmail = validateEmail;
//        this.validatePassword = validatePassword;
//    }

    public void onEvent(LogInFormEvent event) {
        if (event instanceof LogInFormEvent.PasswordChanged) {
            updateFormState(new LogInFormState(
                    ((LogInFormEvent.PasswordChanged) event).password,
                    formState.getValue().getEmail(),
                    null,
                    formState.getValue().getEmailError()
            ));
        } else if (event instanceof LogInFormEvent.EmailChanged) {
            updateFormState(new LogInFormState(
                    formState.getValue().getPassword(),
                    ((LogInFormEvent.EmailChanged) event).email,
                    formState.getValue().getPasswordError(),
                    null
            ));
        } else if (event == LogInFormEvent.SubmitClicked) {
            onSubmit();
        }
    }

    private void updateFormState(LogInFormState newState) {
        formState.setValue(newState);
    }

    private void onSubmit() {
        ValidateResult passValid = validatePassword.validate(formState.getValue().getPassword());
        ValidateResult emailValid = validateEmail.validate(formState.getValue().getEmail());

        if (!passValid.isValid() || !emailValid.isValid()) {
            updateFormState(new LogInFormState(
                    formState.getValue().getPassword(),
                    formState.getValue().getEmail(),
                    passValid.getMessageIfNotValid(),
                    emailValid.getMessageIfNotValid()
            ));
        }
    }
}
