package com.sergiu.libihb_java.presentation.events;

public abstract class LogInFormEvent {

    // Singleton object representing the SubmitClicked event
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