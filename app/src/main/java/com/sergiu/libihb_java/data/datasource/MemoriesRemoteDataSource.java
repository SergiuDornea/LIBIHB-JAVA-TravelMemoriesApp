package com.sergiu.libihb_java.data.datasource;

import static com.sergiu.libihb_java.presentation.utils.Constants.LIBIHB_USER_PATH_KEY;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MemoriesRemoteDataSource {
    private static final String TAG = MemoriesRemoteDataSource.class.getSimpleName();
    private static final String TRAVEL_MEMORIES_KEY = "travel_memories";
    private static final String TRAVEL_MEMORY_IMG_KEY = "travel_memory_img";
    private static final String ID_KEY = "id";
    private final FirebaseFirestore fStore;
    private final FirebaseStorage firebaseStorage;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public MemoriesRemoteDataSource(
            FirebaseFirestore fStore,
            FirebaseStorage firebaseStorage,
            FirebaseAuth firebaseAuth) {
        this.fStore = fStore;
        this.firebaseStorage = firebaseStorage;
        this.firebaseAuth = firebaseAuth;
    }

    @SuppressLint("CheckResult")
    public Completable insertTravelMemory(TravelMemory travelMemory) {
        return Completable.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(currentUser.getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CollectionReference memoriesRef = userRef.collection(TRAVEL_MEMORIES_KEY);
                        memoriesRef.add(travelMemory)
                                .addOnSuccessListener(documentReference -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError);
                    } else {
                        Log.e(TAG, "saveTravelMemory: User document not found");
                    }
                }).addOnFailureListener(emitter::onError);
            }
        });
    }

    public Completable deleteTravelMemory(TravelMemory travelMemory) {
        return Completable.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(currentUser.getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                CollectionReference memoriesRef = userRef.collection(TRAVEL_MEMORIES_KEY);
                                memoriesRef.whereEqualTo(ID_KEY, travelMemory.getId())
                                        .get()
                                        .addOnSuccessListener(queryDocumentSnapshots -> {
                                            DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                                            snapshot.getReference().delete()
                                                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                                                    .addOnFailureListener(emitter::onError);
                                        })
                                        .addOnFailureListener(emitter::onError);
                            } else {
                                Log.e(TAG, "saveTravelMemory: User document not found");
                            }
                        })
                        .addOnFailureListener(emitter::onError);
            }
        });
    }

    public Completable updateTravelMemory(TravelMemory travelMemory) {
        return Completable.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(currentUser.getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CollectionReference memoriesRef = userRef.collection(TRAVEL_MEMORIES_KEY);
                        memoriesRef.whereEqualTo(ID_KEY, travelMemory.getId())
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    snapshot.getReference().set(travelMemory)
                                            .addOnSuccessListener(aVoid -> emitter.onComplete())
                                            .addOnFailureListener(emitter::onError);
                                })
                                .addOnFailureListener(emitter::onError);
                    } else {
                        Log.e(TAG, "updateTravelMemory: User document not found");
                    }
                }).addOnFailureListener(emitter::onError);
            }
        });
    }

    public Single<List<TravelMemory>> getMemories() {
        return Single.create(emitter -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                DocumentReference userRef = fStore.collection(LIBIHB_USER_PATH_KEY).document(currentUser.getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CollectionReference memoriesRef = userRef.collection(TRAVEL_MEMORIES_KEY);
                        memoriesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
                            List<TravelMemory> travelMemories = new ArrayList<>();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                TravelMemory memory = snapshot.toObject(TravelMemory.class);
                                if (memory != null) {
                                    travelMemories.add(memory);
                                }
                            }
                            emitter.onSuccess(travelMemories);
                        }).addOnFailureListener(e -> emitter.onSuccess(new ArrayList<>()));
                    } else {
                        Log.e(TAG, "getMemories: ERROR");
                    }
                }).addOnFailureListener(emitter::onError);
            }
        });
    }

    public Single<String> uploadImg(Uri imageUri) {
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
