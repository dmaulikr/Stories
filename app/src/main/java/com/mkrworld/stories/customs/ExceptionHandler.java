package com.mkrworld.stories.customs;

import android.content.Context;
import android.widget.Toast;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by A1ZFKXA3 on 1/24/2017.
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = BuildConfig.BASE_TAG + ".ExceptionHandler";
    private Object mObject;

    private ExceptionHandler(Object object) {
        mObject = object;
    }

    /**
     * Method to attach the Exception Handler
     *
     * @param caller
     */
    public static final void attachExceptionHandler(Object caller) {
        Tracer.debug(TAG, "attachExceptionHandler: ");
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(caller));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Tracer.error(TAG, "uncaughtException: " + throwable.getMessage());
        throwable.printStackTrace();
        if (mObject instanceof Context) {
            try {
                Toast.makeText((Context) mObject, "Exception Handler : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Tracer.error(TAG, "uncaughtException: Context: " + e.getMessage());
            }
        }
        System.exit(0);
    }
}
