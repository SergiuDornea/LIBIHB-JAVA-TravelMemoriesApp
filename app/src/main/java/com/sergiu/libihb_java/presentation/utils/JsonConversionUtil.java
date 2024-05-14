package com.sergiu.libihb_java.presentation.utils;

import android.util.Pair;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sergiu.libihb_java.domain.model.Education;

import java.lang.reflect.Type;
import java.util.List;

public class JsonConversionUtil {
    private final Gson gson;

    public JsonConversionUtil(Gson gson) {
        this.gson = gson;
    }

    public Pair<String, String> fromStringToPairOfStrings(String contactJson) {
        return gson.fromJson(contactJson, Pair.class);
    }

    public String fromPairToString(Pair<String, String> pair) {
        return gson.toJson(pair);
    }

    public String fromEducationListToString(List<Education> educationList) {
        return gson.toJson(educationList);
    }

    public List<Education> fromStringToEducationList(String educationListJson) {
        Type listType = new TypeToken<List<Education>>() {
        }.getType();
        return gson.fromJson(educationListJson, listType);
    }
}
