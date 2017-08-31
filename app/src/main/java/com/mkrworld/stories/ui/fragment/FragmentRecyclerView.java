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
import com.mkrworld.stories.ui.adapter.BaseAdapter;
import com.mkrworld.stories.ui.adapter.BaseViewHolder;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by A1ZFKXA3 on 8/30/2017.
 */

public abstract class FragmentRecyclerView extends Fragment implements BaseViewHolder.VHClickable {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentRecyclerView";
    private BaseAdapter mBaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreateView: ");
        mBaseAdapter = new BaseAdapter();
        mBaseAdapter.setVHClickCallback(this);
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Tracer.debug(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        hideProgress();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mBaseAdapter);
    }

    /**
     * Method to hide progress
     */
    protected void hideProgress() {
        Tracer.debug(TAG, "hideProgress: ");
        if (getView() != null) {
            getView().findViewById(R.id.fragment_recycler_vew_progress).setVisibility(View.GONE);
        }
    }

    /**
     * Method to show progress
     */
    protected void showProgress() {
        Tracer.debug(TAG, "showProgress: ");
        if (getView() != null) {
            getView().findViewById(R.id.fragment_recycler_vew_progress).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to get the base adapter
     *
     * @return
     */
    protected BaseAdapter getBaseAdapter() {
        Tracer.debug(TAG, "getBaseAdapter: ");
        return mBaseAdapter;
    }

    protected RecyclerView getRecyclerView() {
        Tracer.debug(TAG, "getRecyclerView: ");
        if (getView() != null) {
            return (RecyclerView) getView().findViewById(R.id.fragment_recycler_view_recycler_view);
        }
        return null;
    }

    /**
     * Callback to notify the Activity about the event occur inside the activity
     */
    public interface OnFragmentRecyclerViewListener {
        /**
         * Method call to add the fragment inside the Activity
         *
         * @param fragment
         * @param tag
         */
        public void onFragmentRecyclerViewAddFragmentBackStack(Fragment fragment, String tag);
    }
}
