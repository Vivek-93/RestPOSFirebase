package com.bitplaylabs.restaurent.services;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by anees on 01-02-2018.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String referesedToken = FirebaseInstanceId.getInstance().getToken();
        ReadyOrder.currentToken=referesedToken;
    }
}
