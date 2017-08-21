package com.mkrworld.stories.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkrworld.stories.ui.adapter.viewholder.AdapterItemHandler;
import com.mkrworld.stories.ui.adapter.viewholder.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by himanshu on 05/09/15.
 */
public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BaseViewHolder.VHClickable, BaseViewHolder.VHLongClickable {
    private ArrayList<AdapterItem> mAdapterItemList;
    private BaseViewHolder.VHLongClickable longClickCallback;
    private BaseViewHolder.VHClickable clickCallback;

    /**
     * Constructor
     */
    public BaseAdapter() {
        mAdapterItemList = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param recyclerView           Pass to listen the Last Item Visible Count
     * @param onLoadMoreItemListener Callback to listen the load even
     */
    public BaseAdapter(RecyclerView recyclerView, OnLoadMoreItemListener onLoadMoreItemListener) {
        this();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = AdapterItemHandler.createHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
        viewHolder.setVHClickCallback(this);
        viewHolder.setVHLongClickCallback(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof BaseViewHolder)) return;
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        AdapterItem adapterItem = getItem(position);
        baseViewHolder.bindFeedItem(adapterItem);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return mAdapterItemList.size();
    }

    @Override
    public void onViewHolderClicked(BaseViewHolder holder, View view) {
        if (clickCallback != null) clickCallback.onViewHolderClicked(holder, view);
    }

    @Override
    public void onViewHolderLongClicked(BaseViewHolder holder, View view) {
        if (longClickCallback != null) {
            longClickCallback.onViewHolderLongClicked(holder, view);
        }
    }

    /**
     * Method to get the AdapterItem
     *
     * @param position
     * @return
     */
    private AdapterItem getItem(int position) {
        return mAdapterItemList.get(position);
    }

    /**
     * Method to update Adapter Item
     *
     * @param list
     */
    public void updateAdapterItemList(ArrayList<AdapterItem> list) {
        mAdapterItemList.clear();
        if (list != null) {
            mAdapterItemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * Method to append Adapter Item
     *
     * @param list
     */
    public void appendAdapterItemList(ArrayList<AdapterItem> list) {
        if (list != null) {
            mAdapterItemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * Method to set the VHLongClickCallback
     *
     * @param longClickCallback
     */
    public void setVHLongClickCallback(BaseViewHolder.VHLongClickable longClickCallback) {
        this.longClickCallback = longClickCallback;
    }

    /**
     * Method to set the VHClickCallback
     *
     * @param clickCallback
     */
    public void setVHClickCallback(BaseViewHolder.VHClickable clickCallback) {
        this.clickCallback = clickCallback;
    }

    /**
     * Callback to notify weather there is a need to load more item or not
     */
    public interface OnLoadMoreItemListener {
        /**
         * Method to notify that there is a need to load more items
         */
        public void onLoadMoreItemListener();
    }
}
