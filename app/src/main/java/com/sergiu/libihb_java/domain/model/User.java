package com.sergiu.libihb_java.domain.model;

public class User {
    public static User EMPTY_USER = new User("-1", "no_name", "no_email");
    private final String uid;
    private final String name;
    private final String email;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
