package com.bitplaylabs.restaurent.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.views.activities.KDMainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by anees on 01-02-2018.
 */

public class MyFirebaseMessaging1 {


    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;


    public MyFirebaseMessaging1(){

        mRef = firebaseDatabase.getReference("notificationa");

    }

    public static void openActivityNotification(Context context){


        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
        NotificationManager notificationManager= (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(context, KDMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("FDDd").setContentText("FFASFSfs").setAutoCancel(true).setContentIntent(pendingIntent);


        notificationManager.notify(0,builder.build());

    }
}
