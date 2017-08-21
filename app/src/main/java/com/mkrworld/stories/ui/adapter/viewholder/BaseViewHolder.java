package com.mkrworld.stories.ui.adapter.viewholder;

import android.view.View;

import com.mkrworld.stories.ui.adapter.AdapterItem;

/**
 * Created by himanshu on 08/09/15.
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected View parent;
    private VHClickable clickCallback;
    private VHLongClickable longClickCallback;
    private AdapterItem mAdapterItem;

    public BaseViewHolder(View itemView) {
        super(itemView);
        parent = itemView;
    }

    public View getParent() {
        return parent;
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
        if (clickCallback != null) clickCallback.onViewHolderClicked(this, v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickCallback != null) {
            longClickCallback.onViewHolderLongClicked(this, v);
            return true;
        }
        return false;
    }

    /**
     * Method to set the VHClickCallback
     * @param callback
     */
    public void setVHClickCallback(VHClickable callback) {
        clickCallback = callback;
    }

    /**
     * Method to set the VHLongClickCallback
     * @param callback
     */
    public void setVHLongClickCallback(VHLongClickable callback) {
        longClickCallback = callback;
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
