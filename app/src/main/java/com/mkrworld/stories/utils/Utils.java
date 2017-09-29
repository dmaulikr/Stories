package com.mkrworld.stories.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by a1zfkxa3 on 9/29/2017.
 */
public class Utils {

    /**
     * Method to get the current app version code
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 100;
    }
}
