package com.mkrworld.stories.customs.ui.adapter;

public class AdapterItem<MKR> {
    private AdapterItemHandler.AdapterItemViewType viewType;
    private MKR mkr;

    /**
     * Constructor
     *
     * @param viewType
     * @param d        Object Data pass to the BaseViewHolder
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

    @Override
    public boolean equals(Object obj) {
        try {
            if (obj instanceof AdapterItem) {
                AdapterItem adapterItem = (AdapterItem) obj;
                if (adapterItem.viewType.equals(viewType) && adapterItem.mkr.equals(mkr)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Any exception occur at the time of equals operation
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mkr.hashCode();
    }
}
