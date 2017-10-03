package com.mkrworld.stories.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by a1zfkxa3 on 9/5/2017.
 */

public class TableViewedStoryTitle {
    private static final String TAG = BuildConfig.BASE_TAG + ".TableViewedStoryTitle";
    private static final String TABLE_NAME = "Table_Viewed_Story_Title";
    private static final String COLLUMN_STORY_ID = "story_id";
    private static TableViewedStoryTitle mTableViewedStoryTitle;
    private static Context mContext;

    /**
     * Method to get the instance.
     *
     * @param context
     * @return Current object of this class.
     */
    public static TableViewedStoryTitle getInstance(Context context) {
        Tracer.debug(TAG, "getInstance() ");
        mContext = context;
        if (mTableViewedStoryTitle == null) {
            mTableViewedStoryTitle = new TableViewedStoryTitle();
        }
        return mTableViewedStoryTitle;
    }

    /**
     * Method to create the table which hold the data of order
     *
     * @param db
     */
    void createTable(SQLiteDatabase db) {
        Tracer.debug(TAG, "createTable() ");
        try {
            String query = "Create table " + TABLE_NAME + "(";
            query += COLLUMN_STORY_ID + " VARCHAR PRIMARY KEY NOT NULL";
            query += ")";
            Tracer.error(TAG, "createTable(QUERY) " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "createTable() " + e.getMessage());
        }
    }

    /**
     * Method to notify that the data base version is upgraded
     *
     * @param db
     */
    void onUpgrade(SQLiteDatabase db) {
        Tracer.debug(TAG, "onUpgrade()");
    }

    /**
     * Method to check weather this table contain this story Id or not
     *
     * @param database
     * @param storyId  unique story_id
     * @return
     */
    boolean isContainStoryId(SQLiteDatabase database, String storyId) {
        Tracer.debug(TAG, "isContainStoryId()");
        try {
            String query = "Select * from " + TABLE_NAME + " WHERE " + COLLUMN_STORY_ID + "=\"" + storyId + "\";";
            Tracer.debug(TAG, "isContainStoryId(QUERY) " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "getStoryData() " + e.getMessage());
        }
        return false;
    }

    /**
     * Method to save Story Id which is read by user
     *
     * @param database
     * @param storyId
     */
    void saveStoryTitleId(SQLiteDatabase database, String storyId) {
        Tracer.debug(TAG, "saveStoryTitleId() " + storyId);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLUMN_STORY_ID, storyId);
        database.replace(TABLE_NAME, null, contentValues);
    }
}
