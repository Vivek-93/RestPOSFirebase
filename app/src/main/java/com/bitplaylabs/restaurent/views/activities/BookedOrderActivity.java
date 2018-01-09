package com.bitplaylabs.restaurent.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.BookedOrderAdapter;
import com.bitplaylabs.restaurent.extra.BookedDetailModel;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookedOrderActivity extends AppCompatActivity implements View.OnClickListener{

    public RecyclerView mBookedRv;
    public LinearLayout mEmptyLl, mLl;
    private BookedOrderAdapter mBookedItemsAdapter;
    public TextView mTotalBillPrice;
    public ImageButton mRefresh;
    public ImageView mBack;
    private List<BookedDetailModel> mBookedItemList = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private Sharedpreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_order);
        mBookedRv = (RecyclerView) findViewById(R.id.act_booked_table_items_rv);
        mEmptyLl = (LinearLayout) findViewById(R.id.act_booked_item_empty_ll);
        mLl = (LinearLayout) findViewById(R.id.act_booked_item_ll);
        mTotalBillPrice = (TextView) findViewById(R.id.act_booked_items_totalprice_tv);
        mRefresh = (ImageButton) findViewById(R.id.act_booked_items_refresh_ib);
        mBack = (ImageView) findViewById(R.id.act_booked_back_iv);
        mPrefs = Sharedpreferences.getUserDataObj(this);
        initilizeView();

    }

    private void initilizeView() {
        mRefresh.setOnClickListener(this);
        mBack.setOnClickListener(this);

     /*   mRef = firebaseDatabase.getReference("tables").child(mPrefs.getTableKey())*//*.child("Booked")*//*;
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              *//*  Utils.stopProgress(MainActivity.this);
                TableDetails value = dataSnapshot.getValue(TableDetails.class);
                TableDetails fire = new TableDetails();
                String id = value.getTableid();
                String tablename = value.getTablename();
                String key = dataSnapshot.getKey().toString();
                fire.setTableid(id);
                fire.setTablename(tablename);
                fire.setTablekey(key);
                data.add(fire);*//*
                //  mPrefs.setTableKey(key);

                Log.d("BookedOrderActivity", "" + dataSnapshot.getValue());

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
        });
*/

        mBookedRv.setHasFixedSize(true);
        mBookedItemsAdapter = new BookedOrderAdapter(this, mBookedItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBookedRv.setLayoutManager(mLayoutManager);
        mBookedRv.setAdapter(mBookedItemsAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_booked_items_refresh_ib:
                break;


            case R.id.act_booked_back_iv:

                onBackPressed();
                break;


        }


    }
}
