package com.mkrworld.stories.ui.adapter.viewholder;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryTitleData;

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

public class StoryTitleVH extends BaseViewHolder<StoryTitleData> {

    private TextView mTextViewStoryTitle;
    private View mParentView;

    public StoryTitleVH(View itemView) {
        super(itemView);
        mTextViewStoryTitle = (TextView) itemView.findViewById(R.id.adapter_story_title_textView_story_title);
        mParentView = itemView.findViewById(R.id.adapter_story_title_parent);
        mParentView.setOnClickListener(this);
    }

    @Override
    protected void bindData(StoryTitleData storyTitleData) {
        mParentView.setTag(storyTitleData);
        Spanned htmlText;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            htmlText = Html.fromHtml("<u>" + storyTitleData.getTitle() + "</u>", Html.FROM_HTML_MODE_LEGACY);
        } else {
            htmlText = Html.fromHtml("<u>" + storyTitleData.getTitle() + "</u>");
        }
        mTextViewStoryTitle.setText(htmlText);
    }
}
