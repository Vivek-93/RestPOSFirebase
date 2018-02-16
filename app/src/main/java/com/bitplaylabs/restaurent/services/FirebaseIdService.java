package com.bitplaylabs.restaurent.services;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
       // ReadyOrder.currentToken=referesedToken;

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notifications");

        reference.child("token").setValue(referesedToken);
    }
}
