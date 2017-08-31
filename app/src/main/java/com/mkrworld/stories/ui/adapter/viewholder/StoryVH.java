package com.mkrworld.stories.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryVH extends BaseViewHolder<StoryData> {

    private TextView mTextViewStoryTitle;
    private TextView mTextViewStory;

    public StoryVH(View itemView) {
        super(itemView);
        mTextViewStoryTitle = (TextView) itemView.findViewById(R.id.adapter_story_textView_story_title);
        mTextViewStory = (TextView) itemView.findViewById(R.id.adapter_story_textView_story);
    }

    @Override
    protected void bindData(StoryData storyData) {
        mTextViewStoryTitle.setText(storyData.getTitle());
        mTextViewStory.setText(storyData.getDescription());
    }
}
