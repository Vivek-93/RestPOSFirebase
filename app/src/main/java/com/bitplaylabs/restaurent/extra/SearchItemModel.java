package com.bitplaylabs.restaurent.extra;

/**
 * Created by anees on 08-01-2018.
 */

public class SearchItemModel {


    public String searchItem;
    public int itemQuantity;

    public SearchItemModel(){

    }

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
