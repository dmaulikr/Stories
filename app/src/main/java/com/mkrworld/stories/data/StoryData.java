package com.mkrworld.stories.data;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryData {
    private String mId;
    private String mTitle;
    private String mDescrpition;
    private String mInputType;

    public StoryData(String id, String title, String description, String inputType) {
        mTitle = title;
        mId = id;
        mDescrpition = description;
        mInputType = inputType;
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

    /**
     * Method to get the Story Description
     *
     * @return
     */
    public String getDescription() {
        return mDescrpition;
    }

    /**
     * Method to get the Input type
     *
     * @return
     */
    public String getInputType() {
        return mInputType;
    }
}
