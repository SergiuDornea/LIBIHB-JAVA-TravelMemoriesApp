package com.sergiu.libihb_java.domain.use_case_validate;

import android.util.Patterns;

// Implementing Validate interface for phone number validation
public class ValidatePhone implements Validate {

    // A val that holds the required length of the phone number
    private final int reqLength = 10;

    private final String inputIsBlank = "The phone number can't be empty";
    private final String doesNotMatchRequiredType = "The phone number is not valid";
    private final String doesNotHaveReqLength = "The phone number must have 10 digits";

    // Check if phone number is not blank and returns a boolean value
    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    // Check if the inputted string qualifies as a valid phone number
    // using Patterns utility
    // and returns a boolean value
    @Override
    public boolean matchesRequiredType(String inputType) {
        return Patterns.PHONE.matcher(inputType).matches();
    }

    // Check if the phone number has a length of 10 digits
    public boolean hasCorrectLength(String inputType) {
        return reqLength == inputType.length();
    }

    // Validate the phone number if everything is okay
    // Returns a ValidateResult object
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