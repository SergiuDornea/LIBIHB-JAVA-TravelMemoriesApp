package com.sergiu.libihb_java.network;

import com.sergiu.libihb_java.domain.model.mountain.Mountain;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MountainApi {
    @GET("/api/mountains/")
    Flowable<List<Mountain>> getAllMountains();

    @GET("/api/mountains/")
    Flowable<Mountain> getMountainById(@Query("id") String id);
}
