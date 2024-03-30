package com.sergiu.libihb_java.domain.use_case_validate;

import android.content.Context;

import com.sergiu.libihb_java.R;

import javax.inject.Inject;

public class ValidateRepeatPassword extends ValidatePassword {
    private final Context context;

    @Inject
    public ValidateRepeatPassword(Context context) {
        super(context);
        this.context = context;
    }

    public boolean doPasswordValuesMatch(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public ValidateResult validateEqual(String inputType, String password) {
        ValidateResult superResult = super.validate(inputType);

        if (!doPasswordValuesMatch(password, inputType)) {
            return new ValidateResult(false, context.getString(R.string.does_not_match));
        }

        return new ValidateResult(superResult.isValid(), superResult.getMessageIfNotValid());
    }
}
