package com.sergiu.libihb_java.data.datasource;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sergiu.libihb_java.domain.model.Education;
import com.sergiu.libihb_java.presentation.utils.JsonConversionUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class EducationRemoteDataSource {
    private static final String EDUCATION_REF = "education.json";
    private final FirebaseStorage firebaseStorage;
    private final JsonConversionUtil jsonConversionUtil;

    @Inject
    public EducationRemoteDataSource(FirebaseStorage firebaseStorage, JsonConversionUtil jsonConversionUtil) {
        this.firebaseStorage = firebaseStorage;
        this.jsonConversionUtil = jsonConversionUtil;
    }

    public Single<List<Education>> getEducationData() {
        StorageReference storageReference = firebaseStorage.getReference().child(EDUCATION_REF);

        return Single.create(emitter -> storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            String jsonData = new String(bytes);
            List<Education> list = jsonConversionUtil.fromStringToEducationList(jsonData);
            emitter.onSuccess(list);
        }).addOnFailureListener(emitter::onError));
    }
}

