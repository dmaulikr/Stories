package com.mkrworld.stories.ui.adapter;

public class AdapterItem<MKR> {
    private AdapterItemHandler.AdapterItemViewType viewType;
    private MKR mkr;

    /**
     * Constructor
     * @param viewType
     * @param d Object Data pass to the BaseViewHolder
     */
    public AdapterItem(AdapterItemHandler.AdapterItemViewType viewType, MKR d) {
        this.viewType = viewType;
        this.mkr = d;
    }

    public MKR getBindingData() {
        return mkr;
    }

    public AdapterItemHandler.AdapterItemViewType getViewType() {
        return viewType;
    }
}
