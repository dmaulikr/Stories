package com.mkrworld.stories.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.mkrworld.stories.BuildConfig;


public class PreferenceDataUtils {

    private static final String TAG = BuildConfig.BASE_TAG + ".PreferenceDataUtils";
    private static final String STORE = "PREFERENCE_STORE";
    private static String LATEST_VER_CODE = "LATEST_VER_CODE";

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
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return getShearedPreference(context).getInt(LATEST_VER_CODE, packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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
