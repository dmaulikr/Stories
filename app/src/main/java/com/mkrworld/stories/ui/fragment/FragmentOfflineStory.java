package com.mkrworld.stories.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.customs.ui.adapter.AdapterItem;
import com.mkrworld.stories.customs.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.customs.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.customs.utils.Promotion;
import com.mkrworld.stories.data.ApplicationDataBase;
import com.mkrworld.stories.data.StoryData;
import com.mkrworld.stories.utils.Constants;
import com.mkrworld.stories.utils.DialogUtils;
import com.mkrworld.stories.utils.PreferenceDataUtils;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentOfflineStory extends FragmentRecyclerView {
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentOfflineStory";
    private String mStoryId;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() == null) {
            getActivity().onBackPressed();
            return;
        }
        if (!getArguments().getString(EXTRA_STORY_ID, "").trim().isEmpty()) {
            mStoryId = getArguments().getString(EXTRA_STORY_ID, "").trim();
            loadTextStory(ApplicationDataBase.getInstance(getContext()).getStoryData(mStoryId));
        } else {
            getActivity().onBackPressed();
        }
        // SET TITLE
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_offline_stories));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_offline_story, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_offline_story_remove_story:
                if (mStoryId != null && !mStoryId.trim().isEmpty()) {
                    ApplicationDataBase.getInstance(getContext()).deleteStoryData(mStoryId);
                    getActivity().onBackPressed();
                } else {
                    Tracer.showSnack(getRecyclerView(), getString(R.string.operation_failed));
                }
                break;
            case R.id.menu_offline_story_share_story:
                if (mStoryId != null && !mStoryId.trim().isEmpty()) {
                    StoryData storyData = ApplicationDataBase.getInstance(getContext()).getStoryData(mStoryId);
                    String text = storyData.getTitle() + "\n\n" + storyData.getDescription();
                    Promotion.shareText(getContext(), text);
                } else {
                    Tracer.showSnack(getRecyclerView(), getString(R.string.operation_failed));
                }
                break;
            case R.id.menu_offline_story_font_size:
                DialogUtils.dismissCurrentDialog();
                String title = getString(R.string.dialog_font_size_title);
                String message = getString(R.string.dialog_font_size_message);
                String okText = getString(R.string.dialog_upgrade_dialog_positive);
                String cancelText = getString(R.string.dialog_upgrade_dialog_negative);
                DialogUtils.showFontSize(getActivity(), Constants.DIALOG_FONT_MAX_INDEX, PreferenceDataUtils.getStoryTextSizePer(getActivity()) - Constants.DIALOG_FONT_TRASH_HOLD, Constants.DIALOG_FONT_TRASH_HOLD, title, message, okText, cancelText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        PreferenceDataUtils.setStoryTextSizePer(getContext(), i);
                        getBaseAdapter().notifyDataSetChanged();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        // Do-Nothing
    }

    /**
     * Method to load the Text Story
     */
    private void loadTextStory(StoryData storyData) {
        Tracer.debug(TAG, "loadTextStory: " + storyData.getId());
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
