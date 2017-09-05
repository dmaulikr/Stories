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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Tracer.debug(TAG, "onCreate()");
        mTableStoryData.createTable(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mTableStoryData.onUpgrade(db);
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
    public void saveLocationData(StoryData storyData) {
        Tracer.debug(TAG, "saveLocationData() " + storyData);
        mTableStoryData.saveStoryData(getWritableDatabase(), storyData);
    }
}
