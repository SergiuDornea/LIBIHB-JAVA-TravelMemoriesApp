package com.sergiu.libihb_java.domain.usecasevalidate;

public interface Validate<T> {
    boolean inputNotBlank(T input);

    boolean matchesRequiredType(T input);

    ValidateResult validate(T input);
}