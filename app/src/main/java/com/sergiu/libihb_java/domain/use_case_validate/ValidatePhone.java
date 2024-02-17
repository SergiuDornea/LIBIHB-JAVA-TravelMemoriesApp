package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.presentation.utils.Constants.PHONE_NUMBER_LEN;

import android.util.Patterns;

public class ValidatePhone implements Validate {

    private final String inputIsBlank = "The phone number can't be empty";
    private final String doesNotMatchRequiredType = "The phone number is not valid";
    private final String doesNotHaveReqLength = "The phone number must have 10 digits";

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
            return new ValidateResult(false, inputIsBlank);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, doesNotMatchRequiredType);
        }
        if (!hasCorrectLength(inputType)) {
            return new ValidateResult(false, doesNotHaveReqLength);
        }
        return new ValidateResult(true);
    }
}