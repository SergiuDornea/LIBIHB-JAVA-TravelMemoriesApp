package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH_REQUIRED_TYPE_NAME;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.INPUT_IS_BLANK_NAME;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.MIN_NAME_LEN;

public class ValidateName implements Validate {

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
