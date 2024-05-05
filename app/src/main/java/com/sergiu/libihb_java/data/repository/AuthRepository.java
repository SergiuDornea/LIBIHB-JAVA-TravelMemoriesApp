package com.sergiu.libihb_java.data.repository;

import static com.sergiu.libihb_java.domain.model.User.EMPTY_USER;
import static com.sergiu.libihb_java.presentation.utils.Constants.LIBIHB_USER_PATH_KEY;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sergiu.libihb_java.data.datastore.DiskDataStore;
import com.sergiu.libihb_java.domain.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class AuthRepository {
    private final static String TAG = AuthRepository.class.getSimpleName();
    private final static String PHONE_KEY = "phone_key";
    private final static String NAME_KEY = "name_key";
    private final static String PROFILE_IMG_KEY = "profile_img_key";
    private final static String PROFILE_IMAGES_DIR = "libihb_profile_imgs";
    private final static String PROFILE_IMG_REF = "libihb_profile_img";
    private final Executor executor;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore fStore;
    private final FirebaseStorage firebaseStorage;
    private final DiskDataStore diskDataStore;

    @Inject
    public AuthRepository(
            FirebaseAuth firebaseAuth,
            FirebaseFirestore fStore,
            Executor executor,
            DiskDataStore diskDataStore,
            FirebaseStorage firebaseStorage) {
        this.firebaseAuth = firebaseAuth;
        this.fStore = fStore;
        this.executor = executor;
        this.diskDataStore = diskDataStore;
        this.firebaseStorage = firebaseStorage;
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

    public Flowable<User> getCurrentUserData() {
        return Flowable.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                String uid = currentUser.getUid();
                String email = currentUser.getEmail();
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(uid);
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString(NAME_KEY);
                        String profileImg = documentSnapshot.getString(PROFILE_IMG_KEY);
                        emitter.onNext(new User(uid, name, email, profileImg));
                    } else {
                        emitter.onNext(EMPTY_USER);
                    }
                    emitter.onComplete();
                }).addOnFailureListener(emitter::onError);
            } else {
                emitter.onNext(EMPTY_USER);
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<Boolean> getIsLoggedIn() {
        return diskDataStore.getIsLoggedIn();
    }

    public Completable writeIsLoggedIn(boolean isLoggedIn) {
        return diskDataStore.writeIsLoggedIn(isLoggedIn);
    }

    public void uploadProfileImage(Uri imageUri, UpdateProfilePictureCallback callback) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            StorageReference storageReference = firebaseStorage.getReference()
                    .child(PROFILE_IMAGES_DIR)
                    .child(userId)
                    .child(PROFILE_IMG_REF);

            UploadTask uploadTask = storageReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    callback.onFailure();
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    updateUserProfilePicture(userId, downloadUri.toString(), callback);
                } else {
                    callback.onFailure();
                }
            });
        } else {
            callback.onFailure();
        }
    }

    private void updateUserProfilePicture(String userId, String imageUrl, UpdateProfilePictureCallback callback) {
        DocumentReference documentReference = fStore.collection(LIBIHB_USER_PATH_KEY).document(userId);
        documentReference.update(PROFILE_IMG_KEY, imageUrl)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure());
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

    public interface UpdateProfilePictureCallback {
        void onSuccess();

        void onFailure();
    }
}