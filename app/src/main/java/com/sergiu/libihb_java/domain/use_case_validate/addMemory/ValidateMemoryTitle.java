package com.sergiu.libihb_java.domain.use_case_validate.addMemory;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.use_case_validate.Validate;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;

import javax.inject.Inject;

public class ValidateMemoryTitle implements Validate<String> {
    private final Context context;

    @Inject
    public ValidateMemoryTitle(Context context) {
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
            return new ValidateResult(false, context.getString(R.string.input_is_blank_title));
        }
        return new ValidateResult(true);
    }
}
