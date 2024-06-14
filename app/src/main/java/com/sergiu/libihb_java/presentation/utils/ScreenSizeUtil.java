package com.sergiu.libihb_java.presentation.utils;

import android.content.Context;
import android.content.res.Configuration;

public final class ScreenSizeUtil {
    private static final Integer DP_HEIGHT_OF_A_SMALL_SCREEN = 700;

    public static boolean isScreenSmall(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.screenHeightDp < DP_HEIGHT_OF_A_SMALL_SCREEN;
    }
}
