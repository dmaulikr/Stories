package com.mkrworld.stories.customs.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by himanshu on 08/09/15.
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected View mParent;
    private VHClickable mClickCallback;
    private VHLongClickable mLongClickCallback;
    private AdapterItem mAdapterItem;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mParent = itemView;
    }

    public View getmParent() {
        return mParent;
    }

    public void bindFeedItem(AdapterItem<V> adapterItem) {
        mAdapterItem = adapterItem;
        bindData(adapterItem.getBindingData());
    }

    protected abstract void bindData(V v);

    public AdapterItem getAdapterItem() {
        return mAdapterItem;
    }

    @Override
    public void onClick(View v) {
        if (mClickCallback != null) mClickCallback.onViewHolderClicked(this, v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongClickCallback != null) {
            mLongClickCallback.onViewHolderLongClicked(this, v);
            return true;
        }
        return false;
    }

    /**
     * Method to set the VHClickCallback
     * @param callback
     */
    public void setVHClickCallback(VHClickable callback) {
        mClickCallback = callback;
    }

    /**
     * Method to set the VHLongClickCallback
     * @param callback
     */
    public void setVHLongClickCallback(VHLongClickable callback) {
        mLongClickCallback = callback;
    }

    /**
     * Callback to notify that user clicked the viewItem
     */
    public interface VHClickable {
        /**
         * Method called when user click a view in adapter Item
         *
         * @param holder
         * @param view
         */
        void onViewHolderClicked(BaseViewHolder holder, View view);
    }

    /**
     * Callback to notify that user long-clicked the viewItem
     */
    public interface VHLongClickable {
        /**
         * Method called when user long-click a view in adapter Item
         *
         * @param holder
         * @param view
         */
        void onViewHolderLongClicked(BaseViewHolder holder, View view);
    }
}
