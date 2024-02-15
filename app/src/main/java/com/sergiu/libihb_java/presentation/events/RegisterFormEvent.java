package com.sergiu.libihb_java.presentation.events;

public abstract class RegisterFormEvent {

    // Singleton object representing the SubmitClicked event
    public static final RegisterFormEvent SubmitClicked = new RegisterFormEvent() {};

    public static class PasswordChanged extends RegisterFormEvent {
        public final String password;

        public PasswordChanged(String password) {
            this.password = password;
        }
    }

    public static class EmailChanged extends RegisterFormEvent {
        public final String email;

        public EmailChanged(String email) {
            this.email = email;
        }
    }

    public static class NameChanged extends RegisterFormEvent {
        public final String name;

        public NameChanged(String name) {
            this.name = name;
        }
    }

    public static class PhoneChanged extends RegisterFormEvent {
        public final String phone;

        public PhoneChanged(String phone) {
            this.phone = phone;
        }
    }
}
