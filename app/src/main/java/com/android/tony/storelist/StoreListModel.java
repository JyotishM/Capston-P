package com.android.tony.storelist;

import android.net.Uri;

public class StoreListModel {
    private String storeName,storeAddress,storeRating;
    private Uri storeThumbnail;

    StoreListModel(Uri storeThumbnail, String storeName, String storeAddress, String storeRating)
    {
        this.storeThumbnail = storeThumbnail;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeRating = storeRating;
    }

    public Uri getStoreThumbnail() {
        return storeThumbnail;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getStoreRating() {
        return storeRating;
    }
}
