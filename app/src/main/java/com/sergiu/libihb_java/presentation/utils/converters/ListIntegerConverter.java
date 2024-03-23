package com.sergiu.libihb_java.presentation.utils.converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class ListIntegerConverter {
    @TypeConverter
    public static List<Integer> fromString(String value) {
        if (value == null) {
            return new ArrayList<>();
        }
        String[] parts = value.split(",");
        List<Integer> list = new ArrayList<>();
        for (String part : parts) {
            list.add(Integer.parseInt(part));
        }
        return list;
    }

    @TypeConverter
    public static String fromList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Integer integer : list) {
            builder.append(integer).append(",");
        }
        builder.deleteCharAt(builder.length() - 1); // Remove trailing comma
        return builder.toString();
    }
}
