package com.mkrworld.stories.controller;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.customs.network.ConnectivityInfoUtils;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.utils.Tracer;
import com.mkrworld.stories.utils.libutils.FirebaseUtils;

/**
 * Created by A1ZFKXA3 on 8/23/2017.
 */

public class FetchStoryContentController implements FirebaseUtils.OnFirebaseListener, ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FetchStoryContentController";
    private Context mContext;
    private OnFetchStoryContentControllerListener mOnFetchStoryContentControllerListener;
    private String mStoryId;

    /**
     * Constructor
     *
     * @param context
     * @param onFetchStoryContentControllerListener
     */
    public FetchStoryContentController(Context context, String storyId, OnFetchStoryContentControllerListener onFetchStoryContentControllerListener) {
        Tracer.debug(TAG, "FetchStoryContentController: ");
        mContext = context;
        mStoryId = storyId;
        mOnFetchStoryContentControllerListener = onFetchStoryContentControllerListener;
    }

    /**
     * Method to fetch Story title
     */
    public void fetchStoryContent() {
        Tracer.debug(TAG, "fetchStoryContent: ");
        ConnectivityInfoUtils.isConnected(mContext, this);
    }

    @Override
    public void onFirebaseStoryFetchStoryDataSuccess(String id, DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseStoryFetchStoryDataSuccess: ");
        try {
            String title = (String) dataSnapshot.child(FirebaseUtils.TITLE).getValue();
            String story = (String) dataSnapshot.child(FirebaseUtils.STORY).getValue();
            if (mOnFetchStoryContentControllerListener != null) {
                mOnFetchStoryContentControllerListener.onFetchStoryContentSuccess(new StoryData(mStoryId, title, story));
            }
        } catch (Exception e) {
            Tracer.error(TAG, "onFirebaseStoryFetchStoryDataSuccess()" + e.getMessage());
        }
    }

    @Override
    public void onFirebaseStoryFetchStoryDataFailed(String id, String error) {
        Tracer.debug(TAG, "onFirebaseStoryFetchStoryDataFailed: ");
        if (mOnFetchStoryContentControllerListener != null) {
            mOnFetchStoryContentControllerListener.onFetchStoryContentFailed(id, error);
        }
    }

    @Override
    public void onFirebaseConfigFetchSuccess(DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseConfigFetchSuccess: ");
    }

    @Override
    public void onFirebaseConfigFetchFailed(String error) {
        Tracer.debug(TAG, "onFirebaseConfigFetchFailed: ");
    }

    @Override
    public void onConnectivityInfoUtilsNetworkConnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkConnected: ");
        FirebaseUtils.getInstance().fetchStoryContent(mStoryId, this);
    }

    @Override
    public void onConnectivityInfoUtilsNetworkDisconnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkDisconnected: ");
        onFirebaseStoryFetchStoryDataFailed(mStoryId, mContext.getString(R.string.no_network_connection));
    }

    /**
     * Callback to notify the status of fetching the content of story
     */
    public interface OnFetchStoryContentControllerListener {
        /**
         * Method to notify that request to fetch story content is successfully invoked
         *
         * @param storyData
         */
        public void onFetchStoryContentSuccess(StoryData storyData);

        /**
         * Method to notify that request to fetch story content is failed
         *
         * @param id
         * @param error
         */
        public void onFetchStoryContentFailed(String id, String error);
    }

}
