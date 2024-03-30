package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH_REQUIRED_TYPE_NAME;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.INPUT_IS_BLANK_NAME;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.MIN_NAME_LEN;

import android.content.Context;

import javax.inject.Inject;

public class ValidateName implements Validate {
    private final Context context;

    @Inject
    public ValidateName(Context context){
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= MIN_NAME_LEN;
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, INPUT_IS_BLANK_NAME);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, DOES_NOT_MATCH_REQUIRED_TYPE_NAME);
        }
        return new ValidateResult(true);
    }
}
