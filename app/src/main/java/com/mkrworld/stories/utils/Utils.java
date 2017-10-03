package com.mkrworld.stories.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mkrworld.stories.BuildConfig;

/**
 * Created by a1zfkxa3 on 9/29/2017.
 */
public class Utils {
    private static final String TAG = BuildConfig.BASE_TAG + ".Utils";

    /**
     * Method to get the current app version code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 100;
    }

    /**
     * Method to get the story text size in per
     *
     * @param context
     */
    public static float getStoryTextSize(Context context) {
        int storyTextSizePer = PreferenceDataUtils.getStoryTextSizePer(context);
        return (Constants.STORY_TEXT_SIZE_MAX_EMS * (float) storyTextSizePer) / Constants.STORY_TEXT_SIZE_MAX_PER;
    }
}
