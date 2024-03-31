package com.sergiu.libihb_java.domain.use_case_validate;

import android.content.Context;

import com.sergiu.libihb_java.R;

import javax.inject.Inject;

public class ValidatePasswordLogin implements Validate {
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
