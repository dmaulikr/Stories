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

public class FragmentOfflineHome extends FragmentRecyclerView {

    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentOfflineHome";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        ArrayList<StoryData> storyDataList = ApplicationDataBase.getInstance(getContext()).getStoryDataList();
        ArrayList<AdapterItem> adapterItemArrayList = new ArrayList<>();
        for (StoryData storyData : storyDataList) {
            adapterItemArrayList.add(new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TITLES, storyData));
        }
        getBaseAdapter().appendAdapterItemList(adapterItemArrayList);
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        Tracer.debug(TAG, "onViewHolderClicked: ");
        StoryData storyTitleData = (StoryData) view.getTag();
        if (getActivity() instanceof OnFragmentRecyclerViewListener) {
            FragmentStory fragmentStory = new FragmentStory();
            Bundle bundle = new Bundle();
            bundle.putString(FragmentStory.EXTRA_STORY_ID, storyTitleData.getId());
            fragmentStory.setArguments(bundle);
            ((OnFragmentRecyclerViewListener) getActivity()).onFragmentRecyclerViewAddFragmentBackStack(fragmentStory, FragmentStory.class.getName());
        }
    }
}
