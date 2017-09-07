package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
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
        setHasOptionsMenu(true);
        ArrayList<StoryData> storyDataList = ApplicationDataBase.getInstance(getContext()).getStoryDataList();
        ArrayList<AdapterItem> adapterItemArrayList = new ArrayList<>();
        for (StoryData storyData : storyDataList) {
            adapterItemArrayList.add(new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TITLES, storyData));
        }
        getBaseAdapter().appendAdapterItemList(adapterItemArrayList);
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_offline_stories));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_offline_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public void onFragmentReloadFromBackStack() {
        Tracer.debug(TAG, "onFragmentReloadFromBackStack: ");
        getActivity().invalidateOptionsMenu();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_offline_stories));
        }
    }
}
