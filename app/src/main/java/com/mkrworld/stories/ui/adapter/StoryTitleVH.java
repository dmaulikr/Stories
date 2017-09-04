package com.mkrworld.stories.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryTitleVH extends BaseViewHolder<StoryData> {

    private TextView mTextViewStoryTitle;
    private View mParentView;

    public StoryTitleVH(View itemView) {
        super(itemView);
        mTextViewStoryTitle = (TextView) itemView.findViewById(R.id.adapter_story_title_textView_story_title);
        mParentView = itemView.findViewById(R.id.adapter_story_title_parent);
        mParentView.setOnClickListener(this);
    }

    @Override
    protected void bindData(StoryData storyData) {
        mParentView.setTag(storyData);
        mTextViewStoryTitle.setText(storyData.getTitle());
    }
}
