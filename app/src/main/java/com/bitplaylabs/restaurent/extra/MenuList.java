package com.bitplaylabs.restaurent.extra;

/**
 * Created by vivek on 05-01-2018.
 */

public class MenuList {

    public String itemname;
    public String shortcode;
    public String onlinedisplayname;
    public String category;
    public String subcategory;
    public String mealtype;
    public Long price;
    public String minimumpreparationtime;
    public String hsncode;
    public String description;
    public String available;


    public MenuList(){

    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getOnlinedisplayname() {
        return onlinedisplayname;
    }

    public void setOnlinedisplayname(String onlinedisplayname) {
        this.onlinedisplayname = onlinedisplayname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getMinimumpreparationtime() {
        return minimumpreparationtime;
    }

    public void setMinimumpreparationtime(String minimumpreparationtime) {
        this.minimumpreparationtime = minimumpreparationtime;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getMealtype() {
        return mealtype;
    }

    public void setMealtype(String mealtype) {
        this.mealtype = mealtype;
    }
}
