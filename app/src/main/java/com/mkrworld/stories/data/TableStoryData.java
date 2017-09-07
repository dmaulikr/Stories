package com.mkrworld.stories.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by a1zfkxa3 on 9/5/2017.
 */

public class TableStoryData {
    private static final String TAG = BuildConfig.BASE_TAG + ".TableStoryData";
    private static final String TABLE_NAME = "Table_Story_Data";
    private static final String COLLUMN_STORY_ID = "story_id";
    private static final String COLLUMN_STORY_TITLE = "story_title";
    private static final String COLLUMN_STORY_CONTENT = "story_content";
    private static final String COLLUMN_TIME = "update_time";
    private static TableStoryData mTableStoryData;
    private static Context mContext;

    /**
     * Method to get the instance.
     *
     * @param context
     * @return Current object of this class.
     */
    public static TableStoryData getInstance(Context context) {
        Tracer.debug(TAG, "getInstance() ");
        mContext = context;
        if (mTableStoryData == null) {
            mTableStoryData = new TableStoryData();
        }
        return mTableStoryData;
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
            query += COLLUMN_STORY_ID + " VARCHAR PRIMARY KEY NOT NULL, ";
            query += COLLUMN_STORY_TITLE + " VARCHAR NOT NULL, ";
            query += COLLUMN_STORY_CONTENT + " VARCHAR NOT NULL, ";
            query += COLLUMN_TIME + " INTEGER NOT NULL";
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
     * Method to get the list of all the saved StoryData
     *
     * @param database
     * @return
     */
    ArrayList<StoryData> getStoryDataList(SQLiteDatabase database) {
        Tracer.debug(TAG, "getStoryDataList()");
        ArrayList<StoryData> list = new ArrayList<>();
        try {
            String query = "Select * from " + TABLE_NAME + ";";
            Tracer.debug(TAG, "getStoryDataList(QUERY) " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null) {
                Tracer.debug(TAG, "getStoryDataList(LENGTH) " + cursor.getCount());
                while (cursor.moveToNext()) {
                    list.add(getStoryDataFromCursor(cursor, false));
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "getStoryDataList() " + e.getMessage());
        }
        return list;
    }

    /**
     * Method to get the StoryData
     *
     * @param database
     * @param storyId  unique story_id
     * @return
     */
    StoryData getStoryData(SQLiteDatabase database, String storyId) {
        Tracer.debug(TAG, "getStoryData()");
        try {
            String query = "Select * from " + TABLE_NAME + " WHERE " + COLLUMN_STORY_ID + "=\"" + storyId + "\";";
            Tracer.debug(TAG, "getStoryData(QUERY) " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null) {
                Tracer.debug(TAG, "getStoryData(LENGTH) " + cursor.getCount());
                while (cursor.moveToNext()) {
                    return getStoryDataFromCursor(cursor, true);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "getStoryData() " + e.getMessage());
        }
        return null;
    }

    /**
     * Method to check weather the StoryData is saved ocally or not
     *
     * @param database
     * @param storyId  unique story_id
     * @return TRUE if contain locally, else FALSE
     */
    boolean isContainStoryData(SQLiteDatabase database, String storyId) {
        Tracer.debug(TAG, "isContainStoryData()");
        try {
            String query = "Select * from " + TABLE_NAME + " WHERE " + COLLUMN_STORY_ID + "=\"" + storyId + "\";";
            Tracer.debug(TAG, "isContainStoryData(QUERY) " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor != null) {
                Tracer.debug(TAG, "isContainStoryData(LENGTH) " + cursor.getCount());
                while (cursor.moveToNext()) {
                    return (getStoryDataFromCursor(cursor, true) != null) ? true : false;
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "isContainStoryData() " + e.getMessage());
        }
        return false;
    }

    /**
     * Method to delete StoryData
     *
     * @param database
     * @param storyId
     */
    void deleteStoryData(SQLiteDatabase database, String storyId) {
        Tracer.debug(TAG, "saveStoryData: " + storyId);
        database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLLUMN_STORY_ID + "= '" + storyId + "'");
    }

    /**
     * Method to save StoryData
     *
     * @param database
     * @param storyData
     */
    void saveStoryData(SQLiteDatabase database, StoryData storyData) {
        Tracer.debug(TAG, "deleteStoryData() " + storyData);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLUMN_STORY_ID, storyData.getId());
        contentValues.put(COLLUMN_STORY_TITLE, storyData.getTitle());
        contentValues.put(COLLUMN_STORY_CONTENT, storyData.getDescription());
        contentValues.put(COLLUMN_TIME, System.currentTimeMillis());
        database.replace(TABLE_NAME, null, contentValues);
    }

    /**
     * Method to get the StoryData from Cursor
     *
     * @param cursor
     * @param isClosedTheCursor TRUE if need to closed the cursor else not
     * @return
     */
    private StoryData getStoryDataFromCursor(Cursor cursor, boolean isClosedTheCursor) {
        Tracer.debug(TAG, "getStoryDataFromCursor() ");
        String storyId = cursor.getString(cursor.getColumnIndex(COLLUMN_STORY_ID));
        String title = cursor.getString(cursor.getColumnIndex(COLLUMN_STORY_TITLE));
        String story = cursor.getString(cursor.getColumnIndex(COLLUMN_STORY_CONTENT));
        if (isClosedTheCursor) {
            cursor.close();
        }
        return new StoryData(storyId, title, story);
    }
}
