package com.sergiu.libihb_java.presentation.fragment.login;

import javax.annotation.Nonnull;

public class LoginFormState {

    private final String password;
    private final String email;
    private final String passwordError;
    private final String emailError;

    public LoginFormState(@Nonnull String password, @Nonnull String email, String passwordError, String emailError) {
        this.password = password;
        this.email = email;
        this.passwordError = passwordError;
        this.emailError = emailError;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getEmailError() {
        return emailError;
    }
}