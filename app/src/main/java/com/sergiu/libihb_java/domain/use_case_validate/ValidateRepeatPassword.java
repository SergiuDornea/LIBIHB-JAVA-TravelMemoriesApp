package com.sergiu.libihb_java.domain.use_case_validate;

import static com.sergiu.libihb_java.domain.utils.ValidationUtils.DOES_NOT_MATCH;

public class ValidateRepeatPassword extends ValidatePassword{
    public boolean doPasswordValuesMatch(String password, String repeatPassword){
        return password.equals(repeatPassword);
    }

    public ValidateResult validateEqual(String inputType, String password) {
        ValidateResult superResult = super.validate(inputType);

        if (!doPasswordValuesMatch(password, inputType)) {
            return new ValidateResult(false, DOES_NOT_MATCH);
        }

        return new ValidateResult(superResult.isValid(), superResult.getMessageIfNotValid());
    }
}
