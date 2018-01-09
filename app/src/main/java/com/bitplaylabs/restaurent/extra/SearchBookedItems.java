package com.bitplaylabs.restaurent.extra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivek on 09-01-2018.
 */

public class SearchBookedItems {

    public List<SearchItemModel> searchList = new ArrayList<>();

    public SearchBookedItems() {

    }

    public SearchBookedItems(List<SearchItemModel> searchList) {
        this.searchList = searchList;

    }

    public List<SearchItemModel> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchItemModel> searchList) {
        this.searchList = searchList;
    }
}
