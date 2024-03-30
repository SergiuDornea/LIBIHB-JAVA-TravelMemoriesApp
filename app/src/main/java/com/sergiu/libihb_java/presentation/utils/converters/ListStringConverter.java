package com.sergiu.libihb_java.presentation.utils.converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStringConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(value.split(",")));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String item : list) {
                sb.append(item);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
            return sb.toString();
        }
    }
}
