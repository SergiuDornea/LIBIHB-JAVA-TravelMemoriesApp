package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_HAVE_REQ_LEN;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH_REQUIRED_TYPE_PHONE;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.INPUT_IS_BLANK_PHONE;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.PHONE_NUMBER_LEN;

import android.content.Context;
import android.util.Patterns;

import javax.inject.Inject;

public class ValidatePhone implements Validate {
    private final Context context;

    @Inject
    public ValidatePhone(Context context){
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return Patterns.PHONE.matcher(inputType).matches();
    }

    public boolean hasCorrectLength(String inputType) {
        return inputType.length() == PHONE_NUMBER_LEN;
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, INPUT_IS_BLANK_PHONE);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, DOES_NOT_MATCH_REQUIRED_TYPE_PHONE);
        }
        if (!hasCorrectLength(inputType)) {
            return new ValidateResult(false, DOES_NOT_HAVE_REQ_LEN);
        }
        return new ValidateResult(true);
    }
}