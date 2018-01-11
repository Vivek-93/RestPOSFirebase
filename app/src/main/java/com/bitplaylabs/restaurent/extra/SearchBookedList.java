package com.bitplaylabs.restaurent.extra;

/**
 * Created by anees on 11-01-2018.
 */

public class SearchBookedList {

    private String itemName;
    private Long itemPrice;

    public SearchBookedList(){

    }
    public SearchBookedList(String itemName,Long itemPrice){
        this.itemName=itemName;
        this.itemPrice=itemPrice;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }
}
