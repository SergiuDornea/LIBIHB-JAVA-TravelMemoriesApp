package com.sergiu.libihb_java.presentation.fragment.sos;

import org.jetbrains.annotations.NotNull;

public class SosFormState {
    private final String name;
    private final String phone;
    private final String nameError;
    private final String phoneError;

    public SosFormState(@NotNull String name, @NotNull String phone, String nameError, String phoneError) {
        this.name = name;
        this.phone = phone;
        this.nameError = nameError;
        this.phoneError = phoneError;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public String getName() {
        return name;
    }

    public String getNameError() {
        return nameError;
    }
}
