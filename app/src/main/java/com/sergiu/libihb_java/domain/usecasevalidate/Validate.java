package com.sergiu.libihb_java.domain.usecasevalidate;

public interface Validate<T> {
    // a method that checks if an inputted string is not blank and returns a boolean value
    boolean inputNotBlank(T input);

    // a method that checks if the inputted string qualifies as a valid type (e.g., email or password)
    boolean matchesRequiredType(T input);

    // the method that validates the input field if the inserted value is okay
    // returns a ValidateResult object
    ValidateResult validate(T input);
}