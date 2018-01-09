package com.bitplaylabs.restaurent.extra;

/**
 * Created by anees on 09-01-2018.
 */

public class BookedDetailModel {

    public String bookedItemName;
    public int bookedItemQuantity;

    public BookedDetailModel(){

    }

    public String getBookedItemName() {
        return bookedItemName;
    }

    public void setBookedItemName(String bookedItemName) {
        this.bookedItemName = bookedItemName;
    }

    public int getBookedItemQuantity() {
        return bookedItemQuantity;
    }

    public void setBookedItemQuantity(int bookedItemQuantity) {
        this.bookedItemQuantity = bookedItemQuantity;
    }
}
