package com.sergiu.libihb_java.presentation.fragment.log_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.LogInFormEvent;

public class LogInViewModel extends ViewModel {
    private final ValidateResult validateCredentialsUseCase;

    private final MutableLiveData<LogInFormState> formState = new MutableLiveData<>(new LogInFormState(null, null, null, null));
    public LiveData<LogInFormState> getFormState() {
        return formState;
    }

    public LogInViewModel(ValidateResult validateCredentialsUseCase) {
        this.validateCredentialsUseCase = validateCredentialsUseCase;
    }

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
        ValidateResult passValid = validateCredentialsUseCase.validatePassword(formState.getValue().getPassword());
        ValidateResult emailValid = validateCredentialsUseCase.validateEmail(formState.getValue().getEmail());

        if (!passValid.isValid || !emailValid.isValid) {
            updateFormState(new LogInFormState(
                    formState.getValue().getPassword(),
                    formState.getValue().getEmail(),
                    passValid.getMessageIfNotValid(),
                    emailValid.getMessageIfNotValid()
            ));
            return;
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success);
        }
    }
}
}
