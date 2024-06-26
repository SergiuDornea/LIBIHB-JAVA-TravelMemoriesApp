package com.sergiu.libihb_java.domain.usecasevalidate.memoryv;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

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
        return inputType != null;
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
