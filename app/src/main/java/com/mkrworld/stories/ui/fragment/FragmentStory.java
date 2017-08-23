package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.ui.adapter.BaseAdapter;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentStory extends Fragment implements BaseViewHolder.VHClickable {
    public static final String EXTRA_STORY_ID = "EXTRA_STORY_ID";
    public static final String EXTRA_STORY_TITLE = "EXTRA_STORY_TITLE";
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentStory";
    private BaseAdapter mBaseAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        mBaseAdapter = new BaseAdapter();
        mBaseAdapter.setVHClickCallback(this);
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof AppCompatActivity && getArguments() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString(EXTRA_STORY_TITLE, ""));
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_story_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mBaseAdapter);
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        Tracer.debug(TAG, "onViewHolderClicked: ");
        Tracer.showSnack(getActivity(), view.getTag().toString());
    }
}
