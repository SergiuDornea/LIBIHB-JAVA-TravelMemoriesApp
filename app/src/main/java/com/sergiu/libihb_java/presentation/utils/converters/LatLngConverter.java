package com.sergiu.libihb_java.presentation.utils.converters;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

public class LatLngConverter {
    @TypeConverter
    public static LatLng fromString(String value) {
        if (value == null || value.isEmpty())
            return null;

        String[] parts = value.split(",");
        double latitude = Double.parseDouble(parts[0]);
        double longitude = Double.parseDouble(parts[1]);
        return new LatLng(latitude, longitude);
    }

    @TypeConverter
    public static String fromLatLng(LatLng latLng) {
        if (latLng == null)
            return null;

        return latLng.latitude + "," + latLng.longitude;
    }
}
