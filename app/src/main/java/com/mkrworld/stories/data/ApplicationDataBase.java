package com.mkrworld.stories.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by a1zfkxa3 on 9/5/2017.
 */

public class ApplicationDataBase extends SQLiteOpenHelper {
    private static final String TAG = BuildConfig.BASE_TAG + ".ApplicationDataBase";
    private static final String APP_DB = "APPLICATION_DB_1";
    private static ApplicationDataBase mApplicationDataBase;
    private static Context mContext;
    private TableStoryData mTableStoryData;
    private TableViewedStoryTitle mTableViewedStoryTitle;

    /**
     * Get the instance of DataHelper
     *
     * @param context
     * @return
     */
    public static ApplicationDataBase getInstance(Context context) {
        mContext = context;
        if (mApplicationDataBase == null) {
            mApplicationDataBase = new ApplicationDataBase(context);
        }
        return mApplicationDataBase;
    }

    private ApplicationDataBase(Context context) {
        super(context, APP_DB, null, 1);
        Tracer.debug(TAG, "ApplicationDataBase()");
        mTableStoryData = TableStoryData.getInstance(context);
        mTableViewedStoryTitle = TableViewedStoryTitle.getInstance(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Tracer.debug(TAG, "onCreate()");
        mTableStoryData.createTable(db);
        mTableViewedStoryTitle.createTable(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mTableStoryData.onUpgrade(db);
        mTableViewedStoryTitle.onUpgrade(db);
    }

    //=============================================================================================
    //==============================SERVER ORDER DATA==============================================
    //=============================================================================================

    /**
     * Method to get the List of StoryData
     *
     * @return
     */
    public ArrayList<StoryData> getStoryDataList() {
        Tracer.error(TAG, "getStoryDataList() ");
        ArrayList<StoryData> locationDataList = mTableStoryData.getStoryDataList(getReadableDatabase());
        return locationDataList != null ? locationDataList : new ArrayList<StoryData>();
    }

    /**
     * Method to get the StoryData
     *
     * @param storyId Unique id of StoryData
     * @return
     */
    public StoryData getStoryData(String storyId) {
        Tracer.debug(TAG, "getStoryData()");
        return mTableStoryData.getStoryData(getReadableDatabase(), storyId);
    }


    /**
     * Method to know weather the StoryData is saved locally or not
     *
     * @param storyId Unique id of StoryData
     * @return
     */
    public boolean isContainStoryData(String storyId) {
        Tracer.debug(TAG, "isContainStoryData()");
        return mTableStoryData.isContainStoryData(getReadableDatabase(), storyId);
    }

    /**
     * Method to save the StoryData
     *
     * @param storyData data of a new-story
     */
    public void saveStoryData(StoryData storyData) {
        Tracer.debug(TAG, "saveStoryData() " + storyData);
        mTableStoryData.saveStoryData(getWritableDatabase(), storyData);
    }

    /**
     * Method to delete the StoryData
     *
     * @param storyId
     */
    public void deleteStoryData(String storyId) {
        Tracer.debug(TAG, "deleteStoryData() " + storyId);
        mTableStoryData.deleteStoryData(getWritableDatabase(), storyId);
    }

    /**
     * Method to check weather this story is aleady read by the user or not
     *
     * @param storyId  unique story_id
     * @return
     */
    public boolean isContainStoryId(String storyId) {
        Tracer.debug(TAG, "isContainStoryId() " + storyId);
        return mTableViewedStoryTitle.isContainStoryId(getReadableDatabase(), storyId);
    }

    /**
     * Method to save the Story Id which is read by the user
     *
     * @param storyId
     */
    public void saveStoryTitleId(String storyId) {
        Tracer.debug(TAG, "saveStoryTitleId: " + storyId);
        mTableViewedStoryTitle.saveStoryTitleId(getWritableDatabase(), storyId);
    }
}
