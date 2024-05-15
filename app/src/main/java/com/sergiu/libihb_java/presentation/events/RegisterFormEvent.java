package com.sergiu.libihb_java.presentation.events;

public abstract class RegisterFormEvent {

    public static final RegisterFormEvent RegisterClicked = new RegisterFormEvent() {
    };

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

    public static class RepeatedPasswordChanged extends RegisterFormEvent {
        public final String repeatedPassword;

        public RepeatedPasswordChanged(String repeatedPassword) {
            this.repeatedPassword = repeatedPassword;
        }
    }
}
