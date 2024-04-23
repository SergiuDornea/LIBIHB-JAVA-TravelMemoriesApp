package com.sergiu.libihb_java.presentation.utils;

import android.util.Pair;

import com.google.gson.Gson;

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
}
