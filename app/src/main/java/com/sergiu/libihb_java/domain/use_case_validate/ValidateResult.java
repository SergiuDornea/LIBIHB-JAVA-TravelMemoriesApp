package com.sergiu.libihb_java.domain.use_case_validate;

public final class ValidateResult {
    private final boolean isValid;
    private final String messageIfNotValid;

    public ValidateResult(boolean isValid, String messageIfNotValid) {
        this.isValid = isValid;
        this.messageIfNotValid = messageIfNotValid;
    }

    public ValidateResult(boolean isValid) {
        this.isValid = isValid;
        this.messageIfNotValid = null;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getMessageIfNotValid() {
        return messageIfNotValid;
    }
}