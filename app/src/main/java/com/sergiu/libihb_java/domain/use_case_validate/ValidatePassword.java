package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH_REQUIRED_TYPE_PASSWORD;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.INPUT_IS_BLANK_PASSWORD;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.MIN_PASSWORD_LEN;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.NOT_COMPLEX;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.PASSWORD_PATTERN;


public class ValidatePassword implements Validate {

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= MIN_PASSWORD_LEN;
    }

    public boolean isPasswordComplex(String inputType) {
        return PASSWORD_PATTERN.matcher(inputType).matches();
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, INPUT_IS_BLANK_PASSWORD);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, DOES_NOT_MATCH_REQUIRED_TYPE_PASSWORD);
        }
        if (!isPasswordComplex(inputType)) {
            return new ValidateResult(false, NOT_COMPLEX);
        }
        return new ValidateResult(true);
    }
}
