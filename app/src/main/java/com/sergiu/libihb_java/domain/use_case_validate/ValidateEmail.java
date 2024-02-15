package com.sergiu.libihb_java.domain.use_case_validate;

import android.util.Patterns;

// Implementing Validate interface for email validation
public class ValidateEmail implements Validate {

    // Error messages
    private final String inputIsBlank = "The email can't be empty";
    private final String doesNotMatchRequiredType = "The email is not valid";

    // Check if email is not blank and returns a boolean value
    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    // Check if the inputted string qualifies as a valid email
    // using Patterns utility
    // and returns a boolean value
    @Override
    public boolean matchesRequiredType(String inputType) {
        return Patterns.EMAIL_ADDRESS.matcher(inputType).matches();
    }

    // Validate the email if everything is okay
    // returns a ValidateResult object
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

    // Check if the inputted email is valid and exists in the DB
    public ValidateResult inDataBase(String inputType) {
        // TODO: Implement Firebase check
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
