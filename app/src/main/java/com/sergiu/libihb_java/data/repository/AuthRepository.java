package com.sergiu.libihb_java.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore fStore;
    private final Executor executor;

    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore fStore, Executor executor) {
        this.firebaseAuth = firebaseAuth;
        this.fStore = fStore;
        this.executor = executor;
    }
}
