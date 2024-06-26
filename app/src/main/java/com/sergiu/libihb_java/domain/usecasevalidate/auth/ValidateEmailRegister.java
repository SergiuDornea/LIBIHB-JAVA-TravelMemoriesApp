package com.sergiu.libihb_java.domain.usecasevalidate.auth;

import android.content.Context;
import android.util.Patterns;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

import javax.inject.Inject;

public class ValidateEmailRegister implements Validate<String> {
    private final Context context;

    @Inject
    public ValidateEmailRegister(Context context) {
        this.context = context;
    }

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
            return new ValidateResult(false, context.getString(R.string.input_is_blank_email));
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, context.getString(R.string.does_not_match_required_type_email));
        }
        return new ValidateResult(true);
    }
}
