package com.mkrworld.stories.customs.controller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by delhivery on 4/7/16.
 */
public class AppPermissionController {
    private static final String TAG = BuildConfig.BASE_TAG + ".AppPermissionController";
    private static final int REQUEST_PERMISSION = 100;
    private Activity mActivity;
    private String[] PERMISSIONS = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};
    private OnAppPermissionControllerListener mOnAppPermissionControllerListener;

    /**
     * Constructor
     *
     * @param activity
     * @param onAppPermissionControllerListener
     */
    public AppPermissionController(Activity activity, OnAppPermissionControllerListener onAppPermissionControllerListener) {
        Tracer.debug(TAG, "AppPermissionController: ");
        mActivity = activity;
        mOnAppPermissionControllerListener = onAppPermissionControllerListener;
    }

    /**
     * Method to initialized the Application
     */
    public void initializedAppPermission() {
        if (isHaveAllRequiredPermission()) {
            if (mOnAppPermissionControllerListener != null) {
                mOnAppPermissionControllerListener.onAppPermissionControllerListenerHaveAllRequiredPermission();
            }
        } else {
            requestPermission();
        }
    }

    /**
     * Method to called from tis method of Activity
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Tracer.error(TAG, "onRequestPermissionsResult: ");
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(mActivity, "App was unable to store Apk in Sd-Card", Toast.LENGTH_LONG);
                }
                initializedAppPermission();
            }
            break;
        }
    }

    /**
     * Method to know weather the App have All Required Permission
     *
     * @return TRUE if have all permission, else FALSE
     */
    private boolean isHaveAllRequiredPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                Tracer.error(TAG, "isHaveAllRequiredPermission: " + permission + "    " + (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) + "    " + mActivity.checkSelfPermission(permission));
                if (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to know request the permission
     *
     * @return TRUE if have all permission, else FALSE
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                Tracer.error(TAG, "requestPermission: " + permission + "    " + (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) + "    " + mActivity.checkSelfPermission(permission));
                if (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    Tracer.error(TAG, "requestPermission: " + permission);
                    mActivity.requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
                    return;
                }
            }
        }
    }

    /**
     * Controller to notify the App that the Database of this App is initialized
     */
    public interface OnAppPermissionControllerListener {
        /**
         * Controller to notify that App have all the required permission
         */
        public void onAppPermissionControllerListenerHaveAllRequiredPermission();
    }
}
