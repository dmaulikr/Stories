package com.mkrworld.stories.controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.utils.ConnectivityInfoUtils;
import com.mkrworld.stories.utils.PreferenceDataUtils;
import com.mkrworld.stories.utils.Tracer;
import com.mkrworld.stories.utils.libutils.FirebaseUtils;

import static com.mkrworld.stories.utils.libutils.FirebaseUtils.APP_VER;
import static com.mkrworld.stories.utils.libutils.FirebaseUtils.STORY_MAX_COUNT;
import static com.mkrworld.stories.utils.libutils.FirebaseUtils.STORY_MIN_COUNT;
import static com.mkrworld.stories.utils.libutils.FirebaseUtils.STORY_PAGE_COUNT;

/**
 * Created by A1ZFKXA3 on 8/23/2017.
 */

public class FetchAppConfigController implements FirebaseUtils.OnFirebaseListener, ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FetchAppConfigController";
    private Context mContext;
    private OnFetchAppConfigControllerListener mOnFetchAppConfigControllerListener;
    private ProgressDialog mProgressDialog;

    /**
     * Constructor
     *
     * @param context
     * @param onFetchAppConfigControllerListener
     */
    public FetchAppConfigController(Context context, OnFetchAppConfigControllerListener onFetchAppConfigControllerListener) {
        Tracer.debug(TAG, "FetchAppConfigController: ");
        mContext = context;
        mOnFetchAppConfigControllerListener = onFetchAppConfigControllerListener;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(mContext.getResources().getString(R.string.dialog_message_please_wait));
    }

    /**
     * Method to fetch app config
     */
    public void fetchAppConfig() {
        mProgressDialog.show();
        ConnectivityInfoUtils.isConnected(mContext, this);
    }

    @Override
    public void onFirebaseStoryFetchStoryDataSuccess(String id, DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseStoryFetchStoryDataSuccess: ");
    }

    @Override
    public void onFirebaseStoryFetchStoryDataFailed(String id, String error) {
        Tracer.debug(TAG, "onFirebaseStoryFetchStoryDataFailed: ");
    }

    @Override
    public void onFirebaseConfigFetchSuccess(DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseConfigFetchSuccess: ");
        mProgressDialog.dismiss();
        try {
            int appVer = Integer.parseInt("" + dataSnapshot.child(APP_VER).getValue());
            PreferenceDataUtils.setLatestVersion(mContext, appVer);
        } catch (Exception e) {
            Tracer.error(TAG, "onFirebaseConfigFetchSuccess(APP_VER)" + e.getMessage());
        }
        try {
            int maxCount = Integer.parseInt("" + dataSnapshot.child(STORY_MAX_COUNT).getValue());
            PreferenceDataUtils.setStoryMaxCount(mContext, maxCount);
        } catch (Exception e) {
            Tracer.error(TAG, "onFirebaseConfigFetchSuccess(STORY_MAX_COUNT)" + e.getMessage());
        }
        try {
            int minCount = Integer.parseInt("" + dataSnapshot.child(STORY_MIN_COUNT).getValue());
            PreferenceDataUtils.setStoryMinCount(mContext, minCount);
        } catch (Exception e) {
            Tracer.error(TAG, "onFirebaseConfigFetchSuccess(STORY_MIN_COUNT)" + e.getMessage());
        }
        try {
            int pageCount = Integer.parseInt("" + dataSnapshot.child(STORY_PAGE_COUNT).getValue());
            PreferenceDataUtils.setStoryPageCount(mContext, pageCount);
        } catch (Exception e) {
            Tracer.error(TAG, "onFirebaseConfigFetchSuccess(STORY_PAGE_COUNT)" + e.getMessage());
        }
        if (mOnFetchAppConfigControllerListener != null) {
            mOnFetchAppConfigControllerListener.onFetchAppConfigSuccess();
        }
    }

    @Override
    public void onFirebaseConfigFetchFailed(String error) {
        Tracer.debug(TAG, "onFirebaseConfigFetchFailed: ");
        mProgressDialog.dismiss();
        if (mOnFetchAppConfigControllerListener != null) {
            mOnFetchAppConfigControllerListener.onFetchAppConfigFailed(error);
        }
    }

    @Override
    public void onConnectivityInfoUtilsNetworkConnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkConnected: ");
        FirebaseUtils.getInstance().getAppConfig(this);
    }

    @Override
    public void onConnectivityInfoUtilsNetworkDisconnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkDisconnected: ");
        onFirebaseConfigFetchFailed(mContext.getString(R.string.no_network_connection));
    }

    /**
     * Callback to notify the status of fetching the AppConfig Data
     */
    public interface OnFetchAppConfigControllerListener {
        /**
         * Method called when Fetching App Config process failed
         */
        public void onFetchAppConfigSuccess();

        /**
         * Method called when Fetching App Config process failed
         * @param errorMessage
         */
        public void onFetchAppConfigFailed(String errorMessage);
    }

}
