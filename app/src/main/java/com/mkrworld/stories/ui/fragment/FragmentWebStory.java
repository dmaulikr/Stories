package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.controller.FetchStoryContentController;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.ui.adapter.AdapterItem;
import com.mkrworld.stories.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentWebStory extends FragmentRecyclerView implements FetchStoryContentController.OnFetchStoryContentControllerListener {
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";
    public static final String EXTRA_STORY_TITLE = "EXTRA_STORY_TITLE";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentStory";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        String pageId = "";
        if (getArguments() != null) {
            pageId = getArguments().getString(EXTRA_STORY_ID, "").trim();
        }
        if (pageId.isEmpty()) {
            getActivity().onBackPressed();
            return;
        }
        new FetchStoryContentController(getContext(), pageId, this).fetchStoryContent();
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        Tracer.debug(TAG, "onViewHolderClicked: ");
        Tracer.showSnack(getRecyclerView(), view.getTag().toString());
    }

    @Override
    public void onFetchStoryContentSuccess(StoryData storyData) {
        Tracer.debug(TAG, "onFetchStoryContentSuccess: ");
        hideProgress();
        AdapterItem storyDataAdapterItem = new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY, storyData);
        ArrayList<AdapterItem> storyDataArrayList = new ArrayList<>();
        storyDataArrayList.add(storyDataAdapterItem);
        getBaseAdapter().updateAdapterItemList(storyDataArrayList);
    }

    @Override
    public void onFetchStoryContentFailed(String id, String error) {
        Tracer.debug(TAG, "onFetchStoryContentFailed: ");
        Tracer.showSnack(getRecyclerView(), error);
    }
}
