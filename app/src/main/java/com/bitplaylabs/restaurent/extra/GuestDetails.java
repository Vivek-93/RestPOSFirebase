package com.bitplaylabs.restaurent.extra;

/**
 * Created by anees on 05-01-2018.
 */

public class GuestDetails {

    public String guestname;
    public String guestnumber;
    public String headcount;
    public String kot;

    public GuestDetails(){

    }

    public GuestDetails(String guestname, String phoneno, String headcount , String kot) {
        this.guestname=guestname;
        this.guestnumber=phoneno;
        this.headcount=headcount;
        this.kot=kot;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getGuestnumber() {
        return guestnumber;
    }

    public void setGuestnumber(String guestnumber) {
        this.guestnumber = guestnumber;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getKot() {
        return kot;
    }

    public void setKot(String kot) {
        this.kot = kot;
    }
}
