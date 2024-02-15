package com.sergiu.libihb_java.domain.use_case_validate;

// Implementing Validate interface for password validation
public class ValidatePassword implements Validate {

    // Constants to change easily if needed
    private final int minPasswordLength = 6;

    // Error messages
    private final String inputIsBlank = "The password can't be empty";
    private final String notComplex = "The password must include: lowercase and uppercase letters, and digits";
    private final String doesNotMatchRequiredType = "The password must be over " + minPasswordLength + " characters long ";

    // Check if password is not blank and returns a boolean value
    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    // Check if the inputted string qualifies as a valid password
    // Must be over 6 characters long
    // and returns a boolean value
    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= minPasswordLength;
    }

    // Check if at least one character in the password
    // is a digit and at least one character is a letter
    public boolean isPasswordComplex(String inputType) {
        boolean hasLetter = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;

        for (char c : inputType.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                if (Character.isUpperCase(c)) {
                    hasUpperCase = true;
                }
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasUpperCase && hasDigit;
    }

    // Validate the password if everything is okay
    // Returns a ValidateResult object
    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, inputIsBlank);
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, doesNotMatchRequiredType);
        }
        if (!isPasswordComplex(inputType)) {
            return new ValidateResult(false, notComplex);
        }
        return new ValidateResult(true);
    }

    // If the inputted password is valid, check if it exists in FireBase
    public ValidateResult inDataBase(String inputType) {
        // TODO: Implement Firebase check
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
