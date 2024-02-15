package com.sergiu.libihb_java.presentation.fragment.log_in;

public class LogInFormState {

    private final String password;
    private final String email;
    private final String passwordError;
    private final String emailError;

    public LogInFormState(String password, String email, String passwordError, String emailError) {
        this.password = password != null ? password : "";
        this.email = email != null ? email : "";
        this.passwordError = passwordError;
        this.emailError = emailError;
    }

    // Implement getters for all fields:
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

    public boolean hasErrors() {
        return passwordError != null || emailError != null;
    }
}