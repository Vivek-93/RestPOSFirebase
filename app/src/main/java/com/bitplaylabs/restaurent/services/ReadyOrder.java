package com.bitplaylabs.restaurent.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.OrderStatus;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import okhttp3.Request;

public class ReadyOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference orders;

    public ReadyOrder() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db= FirebaseDatabase.getInstance();
        orders=db.getReference("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        OrderStatus request=dataSnapshot.getValue(OrderStatus.class);
        if(request.getStatus().equals("0")){
            showNotification(dataSnapshot.getKey(),request);
        }

    }

    private void showNotification(String key, OrderStatus request) {

        Intent intent=new Intent(getBaseContext(), OrderStatus.class );
        PendingIntent contentintent= PendingIntent.getActivities(getBaseContext(),0, new Intent[]{intent},0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentInfo("Ready")
                .setContentText("Ready meal")
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int randomint = new Random().nextInt(9999-1)+1;
        manager.notify(randomint,builder.build());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
