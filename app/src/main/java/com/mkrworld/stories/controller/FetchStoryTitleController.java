package com.mkrworld.stories.controller;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.data.StoryTitleData;
import com.mkrworld.stories.utils.Tracer;
import com.mkrworld.stories.utils.libutils.FirebaseUtils;

/**
 * Created by A1ZFKXA3 on 8/23/2017.
 */

public class FetchStoryTitleController implements FirebaseUtils.OnFirebaseListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FetchStoryTitleController";
    private Context mContext;
    private OnFetchStoryTitleControllerListener mOnFetchStoryTitleControllerListener;
    private int mPageIndex;

    /**
     * Constructor
     *
     * @param context
     * @param onOnFetchStoryTitleControllerListener
     */
    public FetchStoryTitleController(Context context, int pageIndex, OnFetchStoryTitleControllerListener onOnFetchStoryTitleControllerListener) {
        Tracer.debug(TAG, "FetchStoryTitleController: ");
        mContext = context;
        mPageIndex = pageIndex;
        mOnFetchStoryTitleControllerListener = onOnFetchStoryTitleControllerListener;
    }

    /**
     * Method to fetch app config
     */
    public void fetchPageTitle() {
        FirebaseUtils.getInstance().fetchStoryTitle(getPageId(), this);
    }

    @Override
    public void onFirebaseStoryFetchTitleSuccess(String id, DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseStoryFetchTitleSuccess: ");
        dataSnapshot.
    }

    @Override
    public void onFirebaseStoryFetchTitleFailed(String id, String error) {
        Tracer.debug(TAG, "onFirebaseStoryFetchTitleFailed: ");
    }

    @Override
    public void onFirebaseConfigFetchSuccess(DataSnapshot dataSnapshot) {
        Tracer.debug(TAG, "onFirebaseConfigFetchSuccess: ");
    }

    @Override
    public void onFirebaseConfigFetchFailed(String error) {
        Tracer.debug(TAG, "onFirebaseConfigFetchFailed: ");
    }

    /**
     * Method to get the page Index
     *
     * @return
     */
    private String getPageId() {
        Tracer.debug(TAG, "getPageId: " + mPageIndex);
        return "" + mPageIndex;
    }

    /**
     * Callback to notify the status of fetching the title of story
     */
    public interface OnFetchStoryTitleControllerListener {
        /**
         * Method to notify that request to fetch story title is successfully invoked
         *
         * @param storyTitleData
         */
        public void onFetchStoryTitleSuccess(StoryTitleData storyTitleData);

        /**
         * Method to notify that request to fetch story title is failed
         *
         * @param error
         */
        public void onFetchStoryTitleFailed(String error);
    }

}
