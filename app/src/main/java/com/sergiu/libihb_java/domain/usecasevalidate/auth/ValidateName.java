package com.sergiu.libihb_java.domain.usecasevalidate.auth;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.MIN_NAME_LEN;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

import javax.inject.Inject;

public class ValidateName implements Validate<String> {
    private final Context context;

    @Inject
    public ValidateName(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return inputType.length() >= MIN_NAME_LEN;
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_name));
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, context.getString(R.string.does_not_match_required_type_name));
        }
        return new ValidateResult(true);
    }
}
