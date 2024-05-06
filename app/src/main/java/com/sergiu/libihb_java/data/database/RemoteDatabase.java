package com.sergiu.libihb_java.data.database;

import static com.sergiu.libihb_java.presentation.utils.Constants.LIBIHB_USER_PATH_KEY;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class RemoteDatabase {
    private static final String TRAVEL_MEMORIES_KEY = "travel_memories";
    private static final String TRAVEL_MEMORY_IMG_KEY = "travel_memory_img";
    private final FirebaseFirestore fStore;
    private final FirebaseStorage firebaseStorage;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public RemoteDatabase(
            FirebaseFirestore fStore,
            FirebaseStorage firebaseStorage,
            FirebaseAuth firebaseAuth) {
        this.fStore = fStore;
        this.firebaseStorage = firebaseStorage;
        this.firebaseAuth = firebaseAuth;
    }

    @SuppressLint("CheckResult")
    public Completable saveTravelMemory(TravelMemory travelMemory) {
        return Completable.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(currentUser.getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CollectionReference memoriesRef = userRef.collection(TRAVEL_MEMORIES_KEY);
                        List<String> imageUrls = new ArrayList<>();

                        Observable.fromIterable(travelMemory.getImageList())
                                .flatMapSingle(imageUri -> uploadImg(Uri.parse(imageUri)))
                                .toList()
                                .subscribe(urls -> {
                                    imageUrls.addAll(urls);
                                    travelMemory.setImageList(imageUrls);

                                    memoriesRef.add(travelMemory)
                                            .addOnSuccessListener(documentReference -> emitter.onComplete())
                                            .addOnFailureListener(emitter::onError);
                                }, emitter::onError);
                    } else {
                        emitter.onError(new IllegalStateException("User document does not exist"));
                    }
                }).addOnFailureListener(emitter::onError);
            }
        });
    }

    private Single<String> uploadImg(Uri imageUri) {
        return Single.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                StorageReference storageReference = firebaseStorage.getReference()
                        .child(TRAVEL_MEMORY_IMG_KEY)
                        .child(currentUser.getUid())
                        .child(imageUri.getLastPathSegment());

                UploadTask uploadTask = storageReference.putFile(imageUri);
                uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        emitter.onError(task.getException());
                    }
                    return storageReference.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        emitter.onSuccess(downloadUri.toString());
                    } else {
                        emitter.onError(task.getException());
                    }
                });
            }
        });
    }
}
