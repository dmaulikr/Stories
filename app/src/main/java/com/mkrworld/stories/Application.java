package com.mkrworld.stories;

import com.mkrworld.stories.utils.Tracer;


/**
 * Created by delhivery on 5/9/16.
 */
public class Application extends android.app.Application {
    private static final String TAG = BuildConfig.BASE_TAG + ".Application";
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Tracer.debug(TAG, "onCreate()");
        mApplication = this;
    }

    /**
     * Method to get the Instance of the application
     *
     * @return
     */
    public Application getInstance() {
        return mApplication;
    }
}
