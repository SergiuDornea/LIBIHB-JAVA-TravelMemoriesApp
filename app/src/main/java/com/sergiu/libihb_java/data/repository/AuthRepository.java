package com.sergiu.libihb_java.data.repository;

import static com.sergiu.libihb_java.presentation.utils.Constants.LIBIHB_USER_PATH_KEY;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class AuthRepository {
    private final static String TAG = AuthRepository.class.getName();
    private final static String PHONE_KEY = "phone_key";
    private final static String NAME_KEY = "name_key";
    private final Executor executor;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore fStore;
    private final DiskDataStore diskDataStore;

    @Inject
    public AuthRepository(
            FirebaseAuth firebaseAuth,
            FirebaseFirestore fStore,
            Executor executor,
            DiskDataStore diskDataStore) {
        this.firebaseAuth = firebaseAuth;
        this.fStore = fStore;
        this.executor = executor;
        this.diskDataStore = diskDataStore;
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

    public void registerUser(String name, String email, String phone, String password, RegisterCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userID = firebaseUser.getUid();
                            checkPhoneExists(phone, phoneExists -> {
                                if (phoneExists) {
                                    Log.d(TAG, "registerUser: PHONE ALREADY EXISTS");
                                    callback.onEmailOrPhoneAlreadyUsed();
                                } else {
                                    saveUserToFirestore(userID, name, phone, callback);
                                }
                            });
                        } else {
                            Log.w(TAG, "registerUser: USER IS NULL");
                        }
                    } else {
                        Log.e(TAG, "registerUser: FAIL ", task.getException());
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Log.d(TAG, "registerUser: INSTANCE OF FirebaseAuthUserCollisionException");
                            callback.onEmailOrPhoneAlreadyUsed();
                        }
                    }
                });
    }

    public Flowable<Boolean> getIsLoggedIn() {
        return diskDataStore.getIsLoggedIn();
    }

    public Completable writeIsLoggedIn(boolean isLoggedIn) {
        return diskDataStore.writeIsLoggedIn(isLoggedIn);
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

    private void checkPhoneExists(String phone, PhoneCheckCallback callback) {
        fStore.collection(LIBIHB_USER_PATH_KEY)
                .whereEqualTo(PHONE_KEY, phone)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> callback.onPhoneChecked(!queryDocumentSnapshots.isEmpty()))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "checkPhoneExists: FAIL ", e);
                    callback.onPhoneChecked(false);
                });
    }

    public interface LoginCallback {
        void onSuccess();

        void onFailure();
    }

    public interface RegisterCallback {
        void onSuccess();

        void onFailure();

        void onEmailOrPhoneAlreadyUsed();
    }

    public interface PhoneCheckCallback {
        void onPhoneChecked(boolean phoneExists);
    }
}