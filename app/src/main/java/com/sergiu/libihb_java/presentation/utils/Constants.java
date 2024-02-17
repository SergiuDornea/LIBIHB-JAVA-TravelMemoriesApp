package com.sergiu.libihb_java.presentation.utils;

import java.util.regex.Pattern;

public final class Constants {
    public static final int MIN_PASSWORD_LEN = 6;
    public static final int PHONE_NUMBER_LEN = 10;
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");

}
