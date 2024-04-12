package com.sergiu.libihb_java.domain.use_case_validate.addMemory;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.use_case_validate.Validate;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;

import java.util.Date;

import javax.inject.Inject;

public class ValidateMemoryDate implements Validate<Date> {
    private final Context context;

    @Inject
    public ValidateMemoryDate(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(Date inputType) {
        return false;
    }

    @Override
    public boolean matchesRequiredType(Date inputType) {
        return false;
    }

    @Override
    public ValidateResult validate(Date inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_date));
        }
        return new ValidateResult(true);
    }
}
