package com.sergiu.libihb_java.domain.use_case_validate;

import android.util.Patterns;

public class ValidateEmail implements Validate {
    private final String inputIsBlank = "The email can't be empty";
    private final String doesNotMatchRequiredType = "The email is not valid";

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
            return new ValidateResult(false, inputIsBlank);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, doesNotMatchRequiredType);
        }
        return new ValidateResult(true);
    }
}
