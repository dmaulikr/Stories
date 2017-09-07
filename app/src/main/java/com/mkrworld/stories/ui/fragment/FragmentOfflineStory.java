package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.customs.ui.adapter.AdapterItem;
import com.mkrworld.stories.customs.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.data.ApplicationDataBase;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentOfflineStory extends FragmentRecyclerView {
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentOfflineStory";

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
            String storyId = getArguments().getString(EXTRA_STORY_ID, "").trim();
            loadTextStory(ApplicationDataBase.getInstance(getContext()).getStoryData(storyId));
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        // Do-Nothing
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

    @Override
    public void onFragmentReloadFromBackStack() {
        Tracer.debug(TAG, "onFragmentReloadFromBackStack: ");
    }
}
