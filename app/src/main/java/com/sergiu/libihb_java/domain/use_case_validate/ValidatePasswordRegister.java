package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.MIN_PASSWORD_LENGTH;
import static com.sergiu.libihb_java.domain.utils.ValidationUtils.PASSWORD_PATTERN;

import android.content.Context;

import com.sergiu.libihb_java.R;

import javax.inject.Inject;


public class ValidatePasswordRegister implements Validate {
    private final Context context;

    @Inject
    public ValidatePasswordRegister(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= MIN_PASSWORD_LENGTH;
    }

    public boolean isPasswordComplex(String inputType) {
        return PASSWORD_PATTERN.matcher(inputType).matches();
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_password));
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, context.getString(R.string.the_password_must) + " "
                    + MIN_PASSWORD_LENGTH + " " + context.getString(R.string.characters_long));
        }
        if (!isPasswordComplex(inputType)) {
            return new ValidateResult(false, context.getString(R.string.not_complex_password));
        }
        return new ValidateResult(true);
    }
}
