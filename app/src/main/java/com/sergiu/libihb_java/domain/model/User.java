package com.sergiu.libihb_java.domain.model;

public class User {
    public static User EMPTY_USER = new User("-1", "no_name", "no_email", null);
    private final String uid;
    private final String name;
    private final String email;
    private final String profilePicture;

    public User(String uid, String name, String email, String profilePicture) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
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

    public String getProfilePicture() {
        return profilePicture;
    }
}
