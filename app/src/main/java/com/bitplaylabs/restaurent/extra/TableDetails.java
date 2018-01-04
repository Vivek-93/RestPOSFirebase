package com.bitplaylabs.restaurent.extra;

/**
 * Created by Vivek on 04-01-2018.
 */

public class TableDetails {


    public String tableId;
    public String tableName;


    public TableDetails (String tableId ,String tableName){
        this.tableId=tableId;
        this.tableName=tableName;

    }

    public TableDetails(){

    }
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
