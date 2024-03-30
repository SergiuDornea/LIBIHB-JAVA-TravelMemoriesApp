package com.sergiu.libihb_java.di;

import android.content.Context;

import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmailLogin;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmailRegister;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateName;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePasswordLogin;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePasswordRegister;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePhone;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateRepeatPasswordRegister;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ValidationModule {
    @Provides
    public ValidateName provideValidateName(Context context) {
        return new ValidateName(context);
    }

    @Provides
    public ValidateEmailRegister provideValidateEmail(Context context) {
        return new ValidateEmailRegister(context);
    }

    @Provides
    public ValidatePasswordRegister provideValidatePassword(Context context) {
        return new ValidatePasswordRegister(context);
    }

    @Provides
    public ValidateRepeatPasswordRegister provideValidateRepeatPassword(Context context) {
        return new ValidateRepeatPasswordRegister(context);
    }

    @Provides
    public ValidatePhone provideValidatePhone(Context context) {
        return new ValidatePhone(context);
    }

    @Provides
    public ValidateEmailLogin provideValidateEmailLogin(Context context) {
        return new ValidateEmailLogin(context);
    }

    @Provides
    public ValidatePasswordLogin provideValidatePasswordLogin(Context context) {
        return new ValidatePasswordLogin(context);
    }
}
