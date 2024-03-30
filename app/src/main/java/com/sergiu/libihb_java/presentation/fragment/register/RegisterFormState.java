package com.sergiu.libihb_java.presentation.fragment.register;

import javax.annotation.Nonnull;

public class RegisterFormState {
    private final String password;
    private final String repeatPassword;
    private final String email;
    private final String name;
    private final String phone;
    private final String passwordError;
    private final String repeatPasswordError;
    private final String emailError;
    private final String nameError;
    private final String phoneError;

    public RegisterFormState(@Nonnull String password, @Nonnull String email, @Nonnull String name, @Nonnull String phone, @Nonnull String repeatPassword,
                             String passwordError, String emailError, String nameError, String phoneError, String repeatPasswordError) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.repeatPassword = repeatPassword;
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.nameError = nameError;
        this.phoneError = phoneError;
        this.repeatPasswordError = repeatPasswordError;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getNameError() {
        return nameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }
}
