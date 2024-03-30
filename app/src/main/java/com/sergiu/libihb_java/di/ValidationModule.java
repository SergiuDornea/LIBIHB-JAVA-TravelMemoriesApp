package com.sergiu.libihb_java.di;

import android.content.Context;

import com.sergiu.libihb_java.domain.use_case_validate.ValidateEmail;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateName;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePassword;
import com.sergiu.libihb_java.domain.use_case_validate.ValidatePhone;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateRepeatPassword;

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
    public ValidateEmail provideValidateEmail(Context context) {
        return new ValidateEmail(context);
    }

    @Provides
    public ValidatePassword provideValidatePassword(Context context) {
        return new ValidatePassword(context);
    }

    @Provides
    public ValidateRepeatPassword provideValidateRepeatPassword(Context context) {
        return new ValidateRepeatPassword(context);
    }

    @Provides
    public ValidatePhone provideValidatePhone(Context context) {
        return new ValidatePhone(context);
    }

}
