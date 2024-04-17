package com.sergiu.libihb_java.presentation.utils;


import static com.sergiu.libihb_java.presentation.utils.Constants.DATE_FORMAT_PATTERN;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {
    public static String formDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
        return dateFormat.format(date);
    }
}
