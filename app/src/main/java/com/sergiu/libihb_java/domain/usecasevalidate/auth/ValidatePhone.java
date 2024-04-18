package com.sergiu.libihb_java.domain.usecasevalidate.auth;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.PHONE_NUMBER_LENGTH;

import android.content.Context;
import android.util.Patterns;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

import javax.inject.Inject;

public class ValidatePhone implements Validate<String> {
    private final Context context;

    @Inject
    public ValidatePhone(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(String inputType) {
        return !inputType.trim().isEmpty();
    }

    @Override
    public boolean matchesRequiredType(String inputType) {
        return Patterns.PHONE.matcher(inputType).matches();
    }

    public boolean hasCorrectLength(String inputType) {
        return inputType.length() == PHONE_NUMBER_LENGTH;
    }

    @Override
    public ValidateResult validate(String inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_phone));
        }
        if (!matchesRequiredType(inputType)) {
            return new ValidateResult(false, context.getString(R.string.does_not_match_required_type_phone));
        }
        if (!hasCorrectLength(inputType)) {
            return new ValidateResult(false, context.getString(R.string.does_not_have_req_len_phone));
        }
        return new ValidateResult(true);
    }
}