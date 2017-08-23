package com.mkrworld.stories.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryTitleData;
import com.mkrworld.stories.ui.adapter.AdapterItem;
import com.mkrworld.stories.ui.adapter.AdapterItemHandler;
import com.mkrworld.stories.ui.adapter.BaseAdapter;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentHome extends Fragment implements BaseViewHolder.VHClickable, BaseAdapter.OnLoadMoreItemListener {

    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentHome";
    private BaseAdapter mBaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        mBaseAdapter = new BaseAdapter();
        mBaseAdapter.setVHClickCallback(this);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fragment_home_progress).setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mBaseAdapter);
        ArrayList<AdapterItem> adapterItemArrayList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            adapterItemArrayList.add(new AdapterItem(AdapterItemHandler.AdapterItemViewType.STORY_TITLES, new StoryTitleData("MKR", "MANISH KUMAR REWALLIYA : " + i)));
        }
        mBaseAdapter.updateAdapterItemList(adapterItemArrayList);
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        Tracer.debug(TAG, "onViewHolderClicked: ");
    }

    @Override
    public void onLoadMoreItemListener() {
        Tracer.debug(TAG, "onLoadMoreItemListener: ");
    }
}
