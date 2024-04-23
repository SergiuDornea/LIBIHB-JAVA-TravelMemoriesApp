package com.sergiu.libihb_java.presentation.events;

public class SosFromEvent {
    public static final SosFromEvent SubmitClicked = new SosFromEvent() {
    };

    public static class NameChanged extends SosFromEvent {
        public final String name;

        public NameChanged(String name) {
            this.name = name;
        }
    }

    public static class PhoneChanged extends SosFromEvent {
        public final String phone;

        public PhoneChanged(String phone) {
            this.phone = phone;
        }
    }
}
