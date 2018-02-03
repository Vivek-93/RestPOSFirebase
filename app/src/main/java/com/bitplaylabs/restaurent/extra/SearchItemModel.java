package com.bitplaylabs.restaurent.extra;

import java.io.Serializable;

/**
 * Created by anees on 08-01-2018.
 */

public class SearchItemModel implements Serializable {


    public String searchItem;
    public int itemQuantity;
    public String tableNo;
    public String captainName;
    public Long itemPrice;
    public String time;

    public SearchItemModel() {

    }

    public SearchItemModel(String searchItem, int itemQuantity, String tableNo, String captainName, Long itemPrice) {
        this.searchItem = searchItem;
        this.itemQuantity = itemQuantity;
        this.tableNo = tableNo;
        this.captainName = captainName;
        this.itemPrice=itemPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
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
