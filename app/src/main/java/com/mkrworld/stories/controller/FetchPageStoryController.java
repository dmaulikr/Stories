package com.mkrworld.stories.controller;

import android.content.Context;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.customs.network.ConnectivityInfoUtils;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by A1ZFKXA3 on 8/23/2017.
 */

public class FetchPageStoryController implements FetchStoryContentController.OnFetchStoryContentControllerListener, ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".FetchPageStoryController";
    private Context mContext;
    private OnFetchPageStoryControllerListener mOnFetchPageStoryControllerListener;
    private int mStartIndex;
    private int mEndIndex;
    private int mPageCount;
    private ArrayList<StoryData> mStoryDataArrayList;
    private int mFailureCount;

    /**
     * Constructor
     *
     * @param context
     * @param currentIndex                       Current visible Title Index
     * @param minIndex
     * @param pageCount
     * @param onFetchPageStoryControllerListener
     */
    public FetchPageStoryController(Context context, int currentIndex, int minIndex, int pageCount, OnFetchPageStoryControllerListener onFetchPageStoryControllerListener) {
        Tracer.debug(TAG, "FetchPageStoryController: ");
        mContext = context;
        mStartIndex = currentIndex - 1;
        mPageCount = pageCount;
        mEndIndex = mStartIndex - mPageCount;
        if (mEndIndex < minIndex) {
            mEndIndex = minIndex;
        }
        mOnFetchPageStoryControllerListener = onFetchPageStoryControllerListener;
        mStoryDataArrayList = new ArrayList<>();
        mFailureCount = 0;
    }

    /**
     * Method to fetch Story title
     */
    public void fetchPageStoryTitle() {
        Tracer.debug(TAG, "fetchPageStoryTitle: " + this);
        ConnectivityInfoUtils.isConnected(mContext, this);
    }

    @Override
    public void onFetchStoryContentSuccess(StoryData storyData) {
        Tracer.debug(TAG, "onFetchStoryContentSuccess: ");
        mStoryDataArrayList.add(storyData);
        validateResponse();
    }

    @Override
    public void onFetchStoryContentFailed(String id, String error) {
        Tracer.debug(TAG, "onFetchStoryContentFailed: ");
        mFailureCount++;
        validateResponse();
    }

    /**
     * Method to validate response
     */
    private void validateResponse() {
        Tracer.debug(TAG, "validateResponse: ");
        if (isGetAllStoryResponse() && mOnFetchPageStoryControllerListener != null) {
            Collections.sort(mStoryDataArrayList, new Comparator<StoryData>() {
                @Override
                public int compare(StoryData storyData1, StoryData storyData2) {
                    try {
                        int id1 = Integer.parseInt(storyData1.getId().replace(BuildConfig.STORY_KEY_PRE_TAG, "").trim());
                        int id2 = Integer.parseInt(storyData2.getId().replace(BuildConfig.STORY_KEY_PRE_TAG, "").trim());
                        if (id1 < id2) {
                            return 1;
                        } else if (id1 > id2) {
                            return -1;
                        }
                        return 0;
                    } catch (Exception e) {
                        Tracer.error(TAG, "compare: " + e.getMessage());
                    }
                    return 0;
                }
            });
            mOnFetchPageStoryControllerListener.onFetchPageStorySuccess(mStoryDataArrayList);
        }
    }

    /**
     * Method to weather the response of all requested Items are fetched
     *
     * @return TRUE
     */
    private boolean isGetAllStoryResponse() {
        Tracer.debug(TAG, "isGetAllStoryResponse: " + mStartIndex + "    " + mEndIndex + "    " + mFailureCount + "     " + mStoryDataArrayList.size() + "     " + (!((mStartIndex - mEndIndex) >= (mFailureCount + mStoryDataArrayList.size()))));
        return !((mStartIndex - mEndIndex) >= (mFailureCount + mStoryDataArrayList.size()));
    }

    @Override
    public void onConnectivityInfoUtilsNetworkConnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkConnected: ");
        for (int storyIndex = mStartIndex; storyIndex >= mEndIndex; storyIndex--) {
            Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkConnected().fetchPageStoryTitle(): " + storyIndex);
            new FetchStoryContentController(mContext, BuildConfig.STORY_KEY_PRE_TAG + storyIndex, this).fetchStoryContent();
        }
    }

    @Override
    public void onConnectivityInfoUtilsNetworkDisconnected() {
        Tracer.debug(TAG, "onConnectivityInfoUtilsNetworkDisconnected: ");
        if (mOnFetchPageStoryControllerListener != null) {
            mOnFetchPageStoryControllerListener.onFetchPageStoryFailed(mContext.getString(R.string.no_network_connection));
        }
    }

    /**
     * Callback to notify the status of fetching the stories of a specific page
     */
    public interface OnFetchPageStoryControllerListener {
        /**
         * Method to notify that request to fetch page story is successfully invoked
         *
         * @param storyDataList
         */
        public void onFetchPageStorySuccess(ArrayList<StoryData> storyDataList);

        /**
         * Method to notify that request to fetch page story is failed
         *
         * @param error
         */
        public void onFetchPageStoryFailed(String error);
    }

}
