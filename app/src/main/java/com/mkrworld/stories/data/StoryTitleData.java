package com.mkrworld.stories.data;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryTitleData {
    private String mId;
    private String mTitle;

    public StoryTitleData(String id, String title) {
        mTitle = title;
        mId = id;
    }

    /**
     * Method to get the Id
     *
     * @return
     */
    public String getId() {
        return mId;
    }

    /**
     * Method to ge the Title
     *
     * @return
     */
    public String getTitle() {
        return mTitle;
    }
}
