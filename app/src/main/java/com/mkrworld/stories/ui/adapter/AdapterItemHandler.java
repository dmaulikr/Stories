package com.mkrworld.stories.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mkrworld.stories.R;
import com.mkrworld.stories.ui.adapter.viewholder.StoryTitleVH;
import com.mkrworld.stories.ui.adapter.viewholder.StoryVH;


/**
 * Created by himanshu on 30/09/15.
 */
public class AdapterItemHandler {
    private static AdapterItemViewType[] ENUM_VALUES;

    public enum AdapterItemViewType {
        NONE, STORY_TITLES, STORY;
    }

    public static BaseViewHolder createHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (ENUM_VALUES == null) {
            ENUM_VALUES = AdapterItemViewType.values();
        }
        switch (ENUM_VALUES[viewType]) {
            case STORY_TITLES:
                return new StoryTitleVH(inflater.inflate(R.layout.adapter_story_title, parent, false));
            case STORY:
                return new StoryVH(inflater.inflate(R.layout.adapter_story, parent, false));
        }
        return null;
    }
}
