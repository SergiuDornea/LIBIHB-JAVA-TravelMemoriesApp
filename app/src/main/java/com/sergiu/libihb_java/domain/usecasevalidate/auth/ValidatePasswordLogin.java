package com.sergiu.libihb_java.domain.usecasevalidate.auth;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

import javax.inject.Inject;

public class ValidatePasswordLogin implements Validate<String> {
    private final Context context;

    @Inject
    public ValidatePasswordLogin(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return false;
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_password));
        }
        return new ValidateResult(true);
    }
}
