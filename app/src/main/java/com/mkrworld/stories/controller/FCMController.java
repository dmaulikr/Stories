package com.mkrworld.stories.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.customs.network.ConnectivityInfoUtils;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by A1ZFKXA3 on 8/23/2017.
 */

public class FCMController implements ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FCMController";
    private Context mContext;

    /**
     * Constructor
     *
     * @param context
     */
    public FCMController(Context context) {
        Tracer.debug(TAG, "FCMController: ");
        mContext = context;
    }

    /**
     * Method to register FCM
     */
    public void registerFCM() {
        ConnectivityInfoUtils.isConnected(mContext, this);
    }

    @Override
    public void onConnectivityInfoUtilsNetworkConnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkConnected: ");
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Tracer.debug(TAG, "doInBackground: " + refreshedToken);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onConnectivityInfoUtilsNetworkDisconnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkDisconnected: ");
    }
}
