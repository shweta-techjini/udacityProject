package com.techjini.udacityapp.utility;

import android.util.Log;

/**
 * Created by Shweta on 2/27/16.
 */
public class AppLogger {

    private static String TAG = "Udacity";
    private static final boolean LOG_ENABLE = true;

    private AppLogger() {
    }

    public static void v(final Object object, final String message) {
        if (LOG_ENABLE) {
            Log.v(TAG, object.getClass().getSimpleName() + "::" + message);
        }
    }

    public static void i(final Object object, final String message) {
        if (LOG_ENABLE) {
            Log.i(TAG, object.getClass().getSimpleName() + "::" + message);
        }
    }

    public static void d(final Object object, final String message) {
        if (LOG_ENABLE) {
            Log.d(TAG, object.getClass().getSimpleName() + "::" + message);
        }
    }

    public static void w(final Object object, final String message) {
        if (LOG_ENABLE) {
            Log.w(TAG, object.getClass().getSimpleName() + "::" + message);
        }
    }

    public static void e(final Object object, final String message) {
        if (LOG_ENABLE) {
            Log.e(TAG, object.getClass().getSimpleName() + "::" + message);
        }
    }

    public static void e(Object object, final String message,
                         final Throwable throwable) {
        if (LOG_ENABLE) {
            Log.e(TAG, object.getClass().getSimpleName() + "::" + message,
                    throwable);
        }
    }
}
