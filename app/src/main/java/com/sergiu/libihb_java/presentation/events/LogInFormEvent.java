package com.sergiu.libihb_java.presentation.events;

public abstract class LogInFormEvent {

    public static final LogInFormEvent SubmitClicked = new LogInFormEvent() {};

    public static class PasswordChanged extends LogInFormEvent {
        public final String password;

        public PasswordChanged(String password) {
            this.password = password;
        }
    }

    public static class EmailChanged extends LogInFormEvent {
        public final String email;

        public EmailChanged(String email) {
            this.email = email;
        }
    }
}