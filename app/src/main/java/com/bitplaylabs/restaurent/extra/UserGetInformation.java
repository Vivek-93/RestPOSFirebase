package com.bitplaylabs.restaurent.extra;

/**
 * Created by anees on 31-12-2017.
 */

public class UserGetInformation {

    public String name;
    public String number;
    public String email;
    public String password;
    public String selectrole;
    public String profilepic;


    public UserGetInformation(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelectrole() {
        return selectrole;
    }

    public void setSelectrole(String selectrole) {
        this.selectrole = selectrole;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
