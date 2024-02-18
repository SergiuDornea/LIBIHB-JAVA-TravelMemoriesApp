package com.sergiu.libihb_java.domain.use_case_validate;

public interface Validate {
    // a method that checks if an inputted string is not blank and returns a boolean value
    boolean inputNotBlank(String inputType);

    // a method that checks if the inputted string qualifies as a valid type (e.g., email or password)
    boolean matchesRequiredType(String inputType);

    // the method that validates the input field if the inserted value is okay
    // returns a ValidateResult object
    ValidateResult validate(String inputType);
}