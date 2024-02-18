package com.sergiu.libihb_java.presentation.fragment.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateName;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePhone;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;
import com.sergiu.libihb_java.presentation.events.RegisterFormEvent;


import java.util.Objects;

public class RegisterViewModel extends ViewModel {
    private final ValidateEmail validateEmail = new ValidateEmail();
    private final ValidatePassword validatePassword = new ValidatePassword();
    private final ValidatePhone validatePhone = new ValidatePhone();
    private final ValidateName validateName = new ValidateName();

    private final MutableLiveData<RegisterFormState> formState = new MutableLiveData<>(new RegisterFormState(null, null, null, null, null, null, null, null));

    public LiveData<RegisterFormState> getFormState() {
        return formState;
    }

    public void onEvent(RegisterFormEvent event) {
        if (event instanceof RegisterFormEvent.PasswordChanged) {
            updateFormState(new RegisterFormState(((RegisterFormEvent.PasswordChanged) event).password, Objects.requireNonNull(formState.getValue()).getEmail(), formState.getValue().getName(), formState.getValue().getPhone(),null, formState.getValue().getEmailError(), formState.getValue().getNameError(), formState.getValue().getPhoneError()));
        }
        if (event instanceof RegisterFormEvent.EmailChanged) {
            updateFormState(new RegisterFormState(formState.getValue().getPassword(), ((RegisterFormEvent.EmailChanged) event).email, formState.getValue().getName(), formState.getValue().getPhone(), formState.getValue().getPasswordError(), null, formState.getValue().getNameError(), formState.getValue().getPhoneError()));
        }
        if (event instanceof RegisterFormEvent.NameChanged) {
            updateFormState(new RegisterFormState(formState.getValue().getPassword(), formState.getValue().getEmail(), ((RegisterFormEvent.NameChanged) event).name, formState.getValue().getPhone(), formState.getValue().getPasswordError(), formState.getValue().getEmailError(), null, formState.getValue().getPhoneError()));
        }
        if (event instanceof RegisterFormEvent.PhoneChanged) {
            updateFormState(new RegisterFormState(formState.getValue().getPassword(), formState.getValue().getEmail(), formState.getValue().getName(), ((RegisterFormEvent.PhoneChanged) event).phone, formState.getValue().getPasswordError(), formState.getValue().getEmailError(), formState.getValue().getNameError(), null));
        }
        if (event == RegisterFormEvent.SubmitClicked) {
            onSubmit();
        }
    }

    private void updateFormState(RegisterFormState newState) {
        formState.setValue(newState);
    }

    private void onSubmit() {
        ValidateResult passValid = validatePassword.validate(formState.getValue().getPassword());
        ValidateResult emailValid = validateEmail.validate(formState.getValue().getEmail());
        ValidateResult phoneValid = validatePhone.validate(formState.getValue().getPhone());
        ValidateResult nameValid = validateName.validate(formState.getValue().getName());

        if (!passValid.isValid() || !emailValid.isValid() || !phoneValid.isValid() || !nameValid.isValid()) {
            updateFormState(new RegisterFormState(
                    formState.getValue().getPassword(),
                    formState.getValue().getEmail(),
                    formState.getValue().getPhone(),
                    formState.getValue().getName(),
                    passValid.getMessageIfNotValid(),
                    emailValid.getMessageIfNotValid(),
                    nameValid.getMessageIfNotValid(),
                    phoneValid.getMessageIfNotValid()));
        }
    }
}