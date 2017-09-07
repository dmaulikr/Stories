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
        // SET TITLE
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_offline_stories));
        }
        loadStoryList();
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
            FragmentOfflineStory fragmentOfflinStory = new FragmentOfflineStory();
            Bundle bundle = new Bundle();
            bundle.putString(FragmentOfflineStory.EXTRA_STORY_ID, storyTitleData.getId());
            fragmentOfflinStory.setArguments(bundle);
            ((OnFragmentRecyclerViewListener) getActivity()).onFragmentRecyclerViewAddFragmentBackStack(fragmentOfflinStory, FragmentOfflineStory.class.getName());
        }
    }

    @Override
    public void onFragmentReloadFromBackStack() {
        Tracer.debug(TAG, "onFragmentReloadFromBackStack: ");
        getActivity().invalidateOptionsMenu();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_offline_stories));
        }
        loadStoryList();
    }

    /**
     * Method to load offline Story
     */
    private void loadStoryList() {
        Tracer.debug(TAG, "loadStoryList: ");
        // FETCH OFFLINE LIST AND ADD IN ADAPTER
        ArrayList<StoryData> storyDataList = ApplicationDataBase.getInstance(getContext()).getStoryDataList();
        ArrayList<AdapterItem> adapterItemArrayList = new ArrayList<>();
        for (StoryData storyData : storyDataList) {
            adapterItemArrayList.add(new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TITLES, storyData));
        }
        getBaseAdapter().updateAdapterItemList(adapterItemArrayList);
        if (storyDataList.size() == 0) {
            Tracer.showSnack(getRecyclerView(), getString(R.string.no_saved_story_found));
        }
    }
}
