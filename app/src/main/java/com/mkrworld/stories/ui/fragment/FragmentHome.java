package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.controller.FetchPageStoryController;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.ui.adapter.AdapterItem;
import com.mkrworld.stories.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.ui.adapter.BaseAdapter;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.PreferenceDataUtils;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentHome extends FragmentRecyclerView implements BaseAdapter.OnLoadMoreItemListener, FetchPageStoryController.OnFetchPageStoryControllerListener {

    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentHome";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        getBaseAdapter().setOnLoadMoreListener(getRecyclerView(), this);
        int currentIndex = PreferenceDataUtils.getStoryMaxCount(getContext());
        int minIndex = PreferenceDataUtils.getStoryMinCount(getContext());
        int pageCount = PreferenceDataUtils.getStoryPageCount(getContext());
        fetchData(currentIndex + 1, minIndex, pageCount);
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
    public void onLoadMoreItemListener() {
        Tracer.debug(TAG, "onLoadMoreItemListener: ");
        if (getBaseAdapter().getItemCount() > 0) {
            AdapterItem item = getBaseAdapter().getItem(getBaseAdapter().getItemCount() - 1);
            if (item.getViewType().equals(AdapterItemHandler.AdapterItemViewType.STORY_TITLES) && item.getBindingData() instanceof StoryData) {
                int currentIndex = PreferenceDataUtils.getStoryMaxCount(getContext());
                int minIndex = PreferenceDataUtils.getStoryMinCount(getContext());
                int pageCount = PreferenceDataUtils.getStoryPageCount(getContext());
                String id = ((StoryData) item.getBindingData()).getId();
                try {
                    currentIndex = Integer.parseInt(id);
                } catch (Exception e) {
                    Tracer.error(TAG, "onLoadMoreItemListener: " + e.getMessage());
                }
                fetchData(currentIndex, minIndex, pageCount);
            }
        }
    }

    @Override
    public void onFetchPageStorySuccess(ArrayList<StoryData> storyDataList) {
        Tracer.debug(TAG, "onFetchPageStorySuccess: ");
        hideProgress();
        ArrayList<AdapterItem> adapterItemArrayList = new ArrayList<>();
        for (StoryData storyData : storyDataList) {
            adapterItemArrayList.add(new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TITLES, storyData));
        }
        getBaseAdapter().appendAdapterItemList(adapterItemArrayList);
    }

    @Override
    public void onFetchPageStoryFailed(String error) {
        Tracer.debug(TAG, "onFetchPageStoryFailed: ");
        hideProgress();
    }

    /**
     * Method to fetch the Page Title
     *
     * @param currentIndex
     * @param minIndex
     * @param pageCount
     */
    private void fetchData(int currentIndex, int minIndex, int pageCount) {
        Tracer.debug(TAG, "fetchData: " + currentIndex);
        if (currentIndex > minIndex) {
            showProgress();
            new FetchPageStoryController(getContext(), currentIndex, minIndex, pageCount, this).fetchPageStoryTitle();
        }
    }
}
