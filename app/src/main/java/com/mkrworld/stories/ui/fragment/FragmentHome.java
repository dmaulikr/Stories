package com.mkrworld.stories.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.stories.R;
import com.mkrworld.stories.data.StoryTitleData;
import com.mkrworld.stories.ui.adapter.AdapterItem;
import com.mkrworld.stories.ui.adapter.viewholder.AdapterItemHandler;
import com.mkrworld.stories.ui.adapter.viewholder.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

import java.util.ArrayList;

/**
 * Created by A1ZFKXA3 on 8/18/2017.
 */

public class FragmentHome extends Fragment implements BaseViewHolder.VHClickable {

    private BaseAdapter mBaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseAdapter = new BaseAdapter();
        mBaseAdapter.setVHClickCallback(this);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        Tracer.showToastProduction(getActivity(), view.getTag().toString(), true);
    }
}
