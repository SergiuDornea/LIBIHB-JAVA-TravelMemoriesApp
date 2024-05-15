package com.sergiu.libihb_java.domain.usecasevalidate.memoryv;

import android.content.Context;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.usecasevalidate.Validate;
import com.sergiu.libihb_java.domain.usecasevalidate.ValidateResult;

import java.util.List;

import javax.inject.Inject;

public class ValidateMemoryImgList implements Validate<List<String>> {
    private final Context context;

    @Inject
    public ValidateMemoryImgList(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(List<String> inputType) {
        return inputType.size() > 0;
    }

    @Override
    public boolean matchesRequiredType(List<String> inputType) {
        return false;
    }

    @Override
    public ValidateResult validate(List<String> inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_img_list));
        }
        return new ValidateResult(true);
    }
}
