package com.bitplaylabs.restaurent.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;


public class Sharedpreferences {

    Context context;
    private SharedPreferences pref; //added private
    public static Editor editor;
    private int PRIVATE_MODE = 0;
    private static Sharedpreferences userData = null;

    // Shared Preferences file name

    private static final String PREF_NAME = "com.bitplaylabs.restpos";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_EMAIL = "email";

    public static final String TAG_IEMI_NUMBER = "imeiNumber";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static final String TAG_USER_ID = "userid";

    public static final String TAG_USER_MOBILE_NUM = "usermobilenum";

    public static final String TAG_USER_ROLE_TYPE = "roleType";

    public static final String TAG_USER_LOGGED_IN = "userloggedinstatus";

    public static final String TAG_USER_LOGGED_IN_TYPE = "userloggedintype";

    public static final String TAG_AUTH_TOKEN = "userauthtoken";

    public static final String TAG_GET_TABLE_FEY= "tablekey";
    public static final String TAG_USER_ROLE= "userrole";
    public static final String TAG_TABLE_ID= "tableid";
    public static final String TAG_TABLE_NUMBER= "tablenumber";

    public static final String TAG_USER_COMING_FROM = "comingfrom";

    public static final String LOGINSTATUS = "login_status";
    public static final String ACTIVATION_STATUS = "activation_status";
    public static final String TAG_LOGGEDIN_USERNAME = "username";
    public static final String TAG_LOGGEDIN_USEREMAIL = "userEmailid";
    public static final String TAG_LOGGEDIN_USERIMAGE = "userImage";



    public Sharedpreferences(Context c) {
        try {
            this.context = c;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sharedpreferences getUserDataObj(Context c) {
        if (userData == null) {
            userData = new Sharedpreferences(c);
        }
        return userData;
    }

    public void clearAll(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref.edit().clear().commit();
    }


    /*
        *  Loggedin username
        **/
    public Boolean getIsUserLoggedIn() {
        return pref.getBoolean(TAG_USER_LOGGED_IN, false);
    }

    public void setIsUserLoggedIn(Boolean status) {
        try {
            editor.putBoolean(TAG_USER_LOGGED_IN, status);
            editor.commit();
        } catch (Exception e) {
        }
    }


    /*
        *  Loggedin type
        **/
    public String getLoggedInType() {
        return pref.getString(TAG_USER_LOGGED_IN_TYPE, "");
    }

    public void setLoggedInType(String imeiNumber) {
        try {
            editor.putString(TAG_USER_LOGGED_IN_TYPE, imeiNumber);
            editor.commit();
        } catch (Exception e) {
        }
    }

    /*
          *  Auth Token
          **/
    public String getAuthToken() {
        return pref.getString(TAG_AUTH_TOKEN, "");
    }

    public void setAuthToken(String userauthtoken) {
        try {
            editor.putString(TAG_AUTH_TOKEN, userauthtoken);
            editor.commit();
        } catch (Exception e) {
        }
    }

    /*
            *  User Name
            **/
    public String getTableKey() {
        return pref.getString(TAG_GET_TABLE_FEY, "");
    }

    public void setTableKey(String username) {
        try {
            editor.putString(TAG_GET_TABLE_FEY, username);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getTableNumber() {
        return pref.getString(TAG_TABLE_NUMBER, "");
    }

    public void setTableNumber(String tablenumber) {
        try {
            editor.putString(TAG_TABLE_NUMBER, tablenumber);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getUserRole() {
        return pref.getString(TAG_USER_ROLE, "");
    }
    public void setUserRole(String userrole) {
        try {
            editor.putString(TAG_USER_ROLE, userrole);
            editor.commit();
        } catch (Exception e) {
        }
    }


    public String getUserId() {
        return pref.getString(TAG_USER_ID, "");
    }

    public void setUserId(String userid) {
        try {
            editor.putString(TAG_USER_ID, userid);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getTableId() {
        return pref.getString(TAG_TABLE_ID, "");
    }

    public void setTableId(String tableid) {
        try {
            editor.putString(TAG_TABLE_ID, tableid);
            editor.commit();
        } catch (Exception e) {
        }
    }




    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    //IEMI Number
    public String getIMEINumber() {
        return pref.getString(TAG_IEMI_NUMBER, "");
    }

    public void setIMEINumber(String imeiNumber) {
        try {
            editor.putString(TAG_IEMI_NUMBER, imeiNumber);
            editor.commit();
        } catch (Exception e) {
        }
    }


    public String getUserMobileNum() {
        return pref.getString(TAG_USER_MOBILE_NUM, "");
    }

    public void setUserMobileNum(String usermobilenum) {
        try {
            editor.putString(TAG_USER_MOBILE_NUM, usermobilenum);
            editor.commit();
        } catch (Exception e) {
        }
    }


    public int getRoleType() {
        return pref.getInt(TAG_USER_ROLE_TYPE,0);
    }

    public void setRoleType(int roleType) {
        try {
            editor.putInt(TAG_USER_ROLE_TYPE, roleType);
            editor.commit();
        } catch (Exception e) {
        }
    }


    /*
    *  coming from
    **/


    public String getTagUserComingFrom() {
        return pref.getString(TAG_USER_COMING_FROM, "");
    }

    public void setTagUserComingFrom(String userid) {
        try {
            editor.putString(TAG_USER_COMING_FROM, userid);
            editor.commit();
        } catch (Exception e) {
        }
    }

    //LoginStatus
    public Boolean getLoginStatus() {
        return pref.getBoolean(LOGINSTATUS, false);
    }

    public void setLoginStatus(Boolean status) {
        try {
            editor.putBoolean(LOGINSTATUS, status);
            editor.commit();
        } catch (Exception e) {
        }
    }    //Is User Activated Status

    public String getActivationStatus() {
        return pref.getString(ACTIVATION_STATUS, "");
    }

    public void setActivationStatus(String status) {
        try {
            editor.putString(ACTIVATION_STATUS, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    //Loggedin username
    public String getLoggedInUsername() {
        return pref.getString(TAG_LOGGEDIN_USERNAME, "");
    }

    public void setLoggedInUsername(String username) {
        try {
            editor.putString(TAG_LOGGEDIN_USERNAME, username);
            editor.commit();
        } catch (Exception e) {
        }
    }


    //Loggedin useremailid
    public String getLoggedInUserEmailid() {
        return pref.getString(TAG_LOGGEDIN_USEREMAIL, "");
    }

    public void setLoggedInUserEmailid(String userEmailid) {
        try {
            editor.putString(TAG_LOGGEDIN_USEREMAIL, userEmailid);
            editor.commit();
        } catch (Exception e) {
        }
    }

    //Loggedin useremailid
    public String getLoggedInUserImage() {
        return pref.getString(TAG_LOGGEDIN_USERIMAGE, "");
    }

    public void setLoggedInUserImage(String userImage) {
        try {
            editor.putString(TAG_LOGGEDIN_USERIMAGE, userImage);
            editor.commit();
        } catch (Exception e) {
        }
    }
}

