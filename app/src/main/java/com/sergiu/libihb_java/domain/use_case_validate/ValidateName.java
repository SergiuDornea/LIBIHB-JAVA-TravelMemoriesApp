package com.sergiu.libihb_java.domain.use_case_validate;

public class ValidateName implements Validate {

    // Constants to change easily if needed
    private final int minNameLength = 2;
    private final String inputIsBlank = "The name can't be empty";
    private final String doesNotMatchRequiredType = "The name must include at least two characters";

    // Check if name is not blank and returns a boolean value
    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= minNameLength;
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
