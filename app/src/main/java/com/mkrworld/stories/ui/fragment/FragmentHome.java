package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.controller.FetchPageStoryController;
import com.mkrworld.stories.customs.ui.adapter.AdapterItem;
import com.mkrworld.stories.customs.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.customs.ui.adapter.BaseAdapter;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.customs.utils.Promotion;
import com.mkrworld.stories.data.StoryData;
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
        setHasOptionsMenu(true);
        getBaseAdapter().setOnLoadMoreListener(getRecyclerView(), this);
        int currentIndex = PreferenceDataUtils.getStoryMaxCount(getContext());
        int minIndex = PreferenceDataUtils.getStoryMinCount(getContext());
        int pageCount = PreferenceDataUtils.getStoryPageCount(getContext());
        fetchData(currentIndex + 1, minIndex, pageCount);
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_stories));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_offline_stories:
                if (getActivity() instanceof OnFragmentRecyclerViewListener) {
                    FragmentOfflineHome fragmentOfflineHome = new FragmentOfflineHome();
                    ((OnFragmentRecyclerViewListener) getActivity()).onFragmentRecyclerViewAddFragmentBackStack(fragmentOfflineHome, FragmentOfflineHome.class.getName());
                }
                break;
            case R.id.menu_home_share_app:
                Promotion.shareApp(getContext());
                break;
            case R.id.menu_home_rate_us:
                Promotion.sendReview(getContext());
                break;
            case R.id.menu_home_more_apps:
                Promotion.getMoreApps(getContext());
                break;
            case R.id.menu_home_send_feedback:
                Promotion.sendFeedback(getContext());
                break;
        }
        return super.onOptionsItemSelected(item);
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
                String id = ((StoryData) item.getBindingData()).getId().replace(BuildConfig.STORY_KEY_PRE_TAG, "").trim();
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
        Tracer.showSnack(getRecyclerView(), error);
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

    @Override
    public void onFragmentReloadFromBackStack() {
        Tracer.debug(TAG, "onFragmentReloadFromBackStack: ");
        getActivity().invalidateOptionsMenu();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_stories));
        }
        getBaseAdapter().notifyDataSetChanged();
    }
}
