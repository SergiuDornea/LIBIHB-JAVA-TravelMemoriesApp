package com.sergiu.libihb_java.presentation.utils.converters;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @TypeConverter
    public static Date fromString(String value) {
        try {
            return value == null ? null : sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToString(Date date) {
        return date == null ? null : sdf.format(date);
    }
}
