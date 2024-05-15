package com.sergiu.libihb_java.di;

import android.content.Context;

import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryCoordinates;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryDate;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryDescription;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryImgList;
import com.sergiu.libihb_java.domain.usecasevalidate.memoryv.ValidateMemoryName;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateEmailLogin;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateEmailRegister;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateName;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidatePasswordLogin;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidatePasswordRegister;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidatePhone;
import com.sergiu.libihb_java.domain.usecasevalidate.auth.ValidateRepeatPasswordRegister;

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

    @Provides
    public ValidateMemoryName provideValidateMemoryTitle(Context context) {
        return new ValidateMemoryName(context);
    }

    @Provides
    public ValidateMemoryDescription provideValidateMemoryDescription(Context context) {
        return new ValidateMemoryDescription(context);
    }

    @Provides
    public ValidateMemoryCoordinates provideValidateMemoryAddress(Context context) {
        return new ValidateMemoryCoordinates(context);
    }

    @Provides
    public ValidateMemoryDate provideValidateMemoryDate(Context context) {
        return new ValidateMemoryDate(context);
    }

    @Provides
    public ValidateMemoryImgList provideValidateMemoryImgList(Context context) {
        return new ValidateMemoryImgList(context);
    }
}
