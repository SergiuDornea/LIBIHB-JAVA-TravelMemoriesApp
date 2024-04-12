package com.sergiu.libihb_java.domain.use_case_validate.addMemory;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.use_case_validate.Validate;
import com.sergiu.libihb_java.domain.use_case_validate.ValidateResult;

import javax.inject.Inject;

public class ValidateMemoryLatLng implements Validate<LatLng> {
    private static final int INVALID_LATLNG = -1;
    private final Context context;

    @Inject
    public ValidateMemoryLatLng(Context context) {
        this.context = context;
    }

    @Override
    public boolean inputNotBlank(LatLng inputType) {
        return inputType != null;
    }

    @Override
    public boolean matchesRequiredType(LatLng inputType) {
        return inputType.latitude != INVALID_LATLNG && inputType.longitude != INVALID_LATLNG;
    }

    @Override
    public ValidateResult validate(LatLng inputType) {
        if (!inputNotBlank(inputType)) {
            return new ValidateResult(false, context.getString(R.string.input_is_blank_address));
        }
        return new ValidateResult(true);
    }
}
