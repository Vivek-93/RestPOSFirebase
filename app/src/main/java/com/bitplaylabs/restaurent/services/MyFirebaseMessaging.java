package com.bitplaylabs.restaurent.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.views.activities.KDMainActivity;
import com.bitplaylabs.restaurent.views.activities.LoginActivity;
import com.bitplaylabs.restaurent.views.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by anees on 01-02-2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()> 0){

            Map<String,String> payload= remoteMessage.getData();
            showNotification(payload);

        }


    }

    private void showNotification(Map<String,String> payload) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(payload.get("username")).setContentText(payload.get("email")).setAutoCancel(true).setContentIntent(pendingIntent).setSound(defaultSoundUri);

        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
