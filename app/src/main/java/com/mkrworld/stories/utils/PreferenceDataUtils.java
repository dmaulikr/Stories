package com.mkrworld.stories.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mkrworld.stories.BuildConfig;


public class PreferenceDataUtils {

    private static final String TAG = BuildConfig.BASE_TAG + ".PreferenceDataUtils";
    private static final String STORE = "PREFERENCE_STORE";
    private static String LATEST_VER_CODE = "LATEST_VER_CODE";
    private static String STORY_MAX_COUNT = "STORY_MAX_COUNT";
    private static String STORY_MIN_COUNT = "STORY_MIN_COUNT";
    private static String STORY_PAGE_COUNT = "STORY_PAGE_COUNT";

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to know weather there is any need of upgrade or not
     *
     * @param context
     * @return
     */
    public static int getLatestVersionCode(Context context) {
        Tracer.debug(TAG, "getLatestVersionCode()");
        return getShearedPreference(context).getInt(LATEST_VER_CODE, Utils.getAppVersionCode(context));
    }

    /**
     * Method to set that there is need of upgrade
     *
     * @param context
     * @param versionOnFirebase Version code on play store
     */
    public static void setLatestVersion(Context context, int versionOnFirebase) {
        Tracer.debug(TAG, "setLatestVersion()" + versionOnFirebase);
        if (versionOnFirebase > getLatestVersionCode(context)) {
            getShearedPreferenceEditor(context).putInt(LATEST_VER_CODE, versionOnFirebase).commit();
        }
    }

    /**
     * Method to get the story max count
     *
     * @param context
     */
    public static int getStoryMaxCount(Context context) {
        Tracer.debug(TAG, "getStoryMaxCount()");
        return getShearedPreference(context).getInt(STORY_MAX_COUNT, 0);
    }

    /**
     * Method to set the story max count
     *
     * @param context
     * @param storyMaxCount Story max count
     */
    public static void setStoryMaxCount(Context context, int storyMaxCount) {
        Tracer.debug(TAG, "setStoryMaxCount()" + storyMaxCount);
        getShearedPreferenceEditor(context).putInt(STORY_MAX_COUNT, storyMaxCount).commit();
    }

    /**
     * Method to get the story min count
     *
     * @param context
     */
    public static int getStoryMinCount(Context context) {
        Tracer.debug(TAG, "getStoryMinCount()");
        return getShearedPreference(context).getInt(STORY_MIN_COUNT, 0);
    }

    /**
     * Method to set the story min count
     *
     * @param context
     * @param storyMinCount Story min count
     */
    public static void setStoryMinCount(Context context, int storyMinCount) {
        Tracer.debug(TAG, "setStoryMinCount()" + storyMinCount);
        getShearedPreferenceEditor(context).putInt(STORY_MIN_COUNT, storyMinCount).commit();
    }

    /**
     * Method to get the story page count
     *
     * @param context
     */
    public static int getStoryPageCount(Context context) {
        Tracer.debug(TAG, "getStoryPageCount()");
        return getShearedPreference(context).getInt(STORY_PAGE_COUNT, 10);
    }

    /**
     * Method to set the story page count
     *
     * @param context
     * @param storyPageCount Story page count
     */
    public static void setStoryPageCount(Context context, int storyPageCount) {
        Tracer.debug(TAG, "setStoryPageCount()" + storyPageCount);
        getShearedPreferenceEditor(context).putInt(STORY_PAGE_COUNT, storyPageCount).commit();
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to clear the Data Store
     *
     * @param context
     */
    public static void clearStore(Context context) {
        Tracer.debug(TAG, "clearStore()");
        getShearedPreferenceEditor(context).clear().commit();
    }

    /**
     * Method to return the Data Store Prefference
     *
     * @param context
     * @return
     */
    private static SharedPreferences getShearedPreference(Context context) {
        return context.getSharedPreferences(STORE, Context.MODE_PRIVATE);
    }

    /**
     * caller to commit this editor
     *
     * @param context
     * @return Editor
     */
    private static Editor getShearedPreferenceEditor(Context context) {
        return getShearedPreference(context).edit();
    }
}
