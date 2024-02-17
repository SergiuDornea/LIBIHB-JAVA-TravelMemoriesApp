package com.sergiu.libihb_java.presentation.fragment.register;

public class RegisterFormState {

    private final String password;
    private final String email;
    private final String name;
    private final String phone;
    private final String passwordError;
    private final String emailError;
    private final String nameError;
    private final String phoneError;

    public RegisterFormState(String password, String email, String name, String phone,
                             String passwordError, String emailError, String nameError, String phoneError) {
        this.password = password != null ? password : "";
        this.email = email != null ? email : "";
        this.name = name != null ? name : "";
        this.phone = phone != null ? phone : "";
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.nameError = nameError;
        this.phoneError = phoneError;
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

    public boolean hasErrors() {
        return passwordError != null || emailError != null || nameError != null || phoneError != null;
    }
}
