package com.bitplaylabs.restaurent.extra;

/**
 * Created by Vivek on 04-01-2018.
 */

public class TableDetails {


    public String tableid;
    public String tablename;
    public String tablekey;
    public String status;
    public String totalprice;


    public TableDetails(){

    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTablekey() {
        return tablekey;
    }

    public void setTablekey(String tablekey) {
        this.tablekey = tablekey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
