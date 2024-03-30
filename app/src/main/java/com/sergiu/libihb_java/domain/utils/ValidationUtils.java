package com.sergiu.libihb_java.domain.utils;

import java.util.regex.Pattern;

public final class ValidationUtils {
    public static final int MIN_NAME_LEN = 2;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int PHONE_NUMBER_LENGTH = 10;
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{6,}$");
}
