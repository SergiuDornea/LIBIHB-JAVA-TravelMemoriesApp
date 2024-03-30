package com.sergiu.libihb_java.data.repository;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.EMAIL_IS_USED_CHECK;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class AuthRepository {
    private final static String TAG = AuthRepository.class.getName();
    private final static String LIBIHB_USER_PATH_KEY = "user_profile";
    private final static String PHONE_KEY = "phone_key";
    private final static String NAME_KEY = "name_key";
    private final Executor executor;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore fStore;

    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore fStore, Executor executor) {
        this.firebaseAuth = firebaseAuth;
        this.fStore = fStore;
        this.executor = executor;
    }

    public void registerUser(String name, String email, String phone, String password, RegisterCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userID = firebaseUser.getUid();
                            saveUserToFirestore(userID, name, phone, callback);
                        } else {
                            Log.w(TAG, "registerUser: USER IS NULL");
                        }
                    } else {
                        Log.e(TAG, "registerUser: FAIL ", task.getException());
                    }
                });
    }

    public void loginUser(String email, String password, LoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser() != null) {
                            callback.onSuccess();
                        }
                    } else {
                        Log.e(TAG, "loginUser: FAIL", task.getException());
                        callback.onFailure();
                    }
                });
    }

    private void saveUserToFirestore(String userID, String name, String phone, RegisterCallback callback) {
        DocumentReference documentReference = fStore.collection(LIBIHB_USER_PATH_KEY).document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put(NAME_KEY, name);
        user.put(PHONE_KEY, phone);
        documentReference.set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure());
    }

    public interface LoginCallback {
        void onSuccess();

        void onFailure();
    }

    public interface RegisterCallback {
        void onSuccess();

        void onFailure();
    }
}