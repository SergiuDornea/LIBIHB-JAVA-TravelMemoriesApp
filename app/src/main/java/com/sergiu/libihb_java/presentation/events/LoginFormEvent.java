package com.sergiu.libihb_java.presentation.events;

public abstract class LoginFormEvent {

    public static final LoginFormEvent LoginClicked = new LoginFormEvent() {
    };

    public static class PasswordChanged extends LoginFormEvent {
        public final String password;

        public PasswordChanged(String password) {
            this.password = password;
        }
    }

    public static class EmailChanged extends LoginFormEvent {
        public final String email;

        public EmailChanged(String email) {
            this.email = email;
        }
    }
}