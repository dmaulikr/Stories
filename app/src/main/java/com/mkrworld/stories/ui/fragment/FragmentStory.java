package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.controller.FetchStoryContentController;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.customs.ui.adapter.AdapterItem;
import com.mkrworld.stories.customs.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentStory extends FragmentRecyclerView implements FetchStoryContentController.OnFetchStoryContentControllerListener {
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";
    public static final String EXTRA_STORY_URL = "EXTRA_STORY_URL";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentStory";
    private static final String DEFAULT_STORY_ID = "DEFAULT_STORY_ID";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        if (getArguments() == null) {
            getActivity().onBackPressed();
            return;
        }
        if (!getArguments().getString(EXTRA_STORY_ID, "").trim().isEmpty()) {
            String pageId = getArguments().getString(EXTRA_STORY_ID, "").trim();
            new FetchStoryContentController(getContext(), pageId, this).fetchStoryContent();
        } else if (!getArguments().getString(EXTRA_STORY_URL, "").trim().isEmpty()) {
            String url = getArguments().getString(EXTRA_STORY_URL, "").trim();
        } else {
            getActivity().onBackPressed();
        }
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
        loadTextStory(storyData);
    }

    @Override
    public void onFetchStoryContentFailed(String id, String error) {
        Tracer.debug(TAG, "onFetchStoryContentFailed: ");
        hideProgress();
        Tracer.showSnack(getRecyclerView(), error);
    }

    /**
     * Method to load the Text Story
     */
    private void loadTextStory(StoryData storyData) {
        AdapterItem storyDataAdapterItem = new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TEXT, storyData);
        ArrayList<AdapterItem> storyDataArrayList = new ArrayList<>();
        storyDataArrayList.add(storyDataAdapterItem);
        getBaseAdapter().updateAdapterItemList(storyDataArrayList);
    }
}
