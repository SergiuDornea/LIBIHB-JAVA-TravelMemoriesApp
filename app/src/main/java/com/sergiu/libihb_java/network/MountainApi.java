package com.sergiu.libihb_java.network;

import com.sergiu.libihb_java.domain.model.mountain.Mountain;
import com.sergiu.libihb_java.domain.model.mountain.MountainResult;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

public interface MountainApi {
    @GET("mountains/")
    Flowable<MountainResult> getAllMountains();
}
