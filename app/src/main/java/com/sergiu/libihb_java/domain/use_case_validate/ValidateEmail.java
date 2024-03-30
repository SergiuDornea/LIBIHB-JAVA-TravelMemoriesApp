package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH_REQUIRED_TYPE_EMAIL;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.INPUT_IS_BLANK_EMAIL;

import android.content.Context;
import android.util.Patterns;

import javax.inject.Inject;

public class ValidateEmail implements Validate {
    private final Context context;

    @Inject
    public ValidateEmail(Context context){
        this.context = context;
    }
    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return Patterns.EMAIL_ADDRESS.matcher(inputType).matches();
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, INPUT_IS_BLANK_EMAIL);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, DOES_NOT_MATCH_REQUIRED_TYPE_EMAIL);
        }
        return new ValidateResult(true);
    }
}
