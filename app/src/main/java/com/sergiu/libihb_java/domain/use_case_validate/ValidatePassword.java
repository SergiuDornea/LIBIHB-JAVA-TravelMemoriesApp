package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.presentation.utils.Constants.MIN_PASSWORD_LEN;
import static com.sergiu.libihb_java.presentation.utils.Constants.PASSWORD_PATTERN;

public class ValidatePassword implements Validate {
    private final String inputIsBlank = "The password can't be empty";
    private final String notComplex = "The password must include: lowercase and uppercase letters, digits, no white spaces";
    private final String doesNotMatchRequiredType = "The password must be over " + MIN_PASSWORD_LEN + " characters long ";

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
}
