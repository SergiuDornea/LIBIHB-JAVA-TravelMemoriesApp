package com.sergiu.libihb_java.domain.utils;

import java.util.regex.Pattern;

public final class ValidationUtils {
    public static final int MIN_PASSWORD_LEN = 6;
    public static final int PHONE_NUMBER_LEN = 10;
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");

    public static final String INPUT_IS_BLANK_EMAIL = "The email can't be empty";
    public static final String DOES_NOT_MATCH_REQUIRED_TYPE_EMAIL = "The email is not valid";
    public static final int MIN_NAME_LEN = 2;

    public static final String INPUT_IS_BLANK_PASSWORD = "The password can't be empty";
    public static final String DOES_NOT_MATCH_REQUIRED_TYPE_PASSWORD = "The password is not valid";
    public static final String NOT_COMPLEX = "The password must include: lowercase and uppercase letters, digits, no white spaces";
    public static final String DOES_NOT_MATCH = "The passwords do not match";

    public static final String INPUT_IS_BLANK_PHONE = "The phone can't be empty";
    public static final String DOES_NOT_MATCH_REQUIRED_TYPE_PHONE = "The phone number is not valid";
    public static final String DOES_NOT_HAVE_REQ_LEN = "The phone number must have 10 digits";

    public static final String INPUT_IS_BLANK_NAME = "The name can't be empty";
    public static final String DOES_NOT_MATCH_REQUIRED_TYPE_NAME = "The name is not valid";

}
