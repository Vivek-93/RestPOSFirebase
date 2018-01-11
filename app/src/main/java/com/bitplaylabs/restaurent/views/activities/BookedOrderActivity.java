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
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.BookedOrderAdapter;
import com.bitplaylabs.restaurent.extra.BookedDetailModel;
import com.bitplaylabs.restaurent.extra.SearchBookedList;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class BookedOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView mBookedRv;
    public LinearLayout mEmptyLl, mLl;
    private BookedOrderAdapter mBookedItemsAdapter;
    public TextView mTotalBillPrice;
    public ImageButton mRefresh;
    public ImageView mBack;
    private List<BookedDetailModel> mBookedItemList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private Sharedpreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_order);

        firebaseDatabase = FirebaseDatabase.getInstance();
        Utils.showProgress(BookedOrderActivity.this);
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
        mBookedItemList = new ArrayList<>();
        mRefresh.setOnClickListener(this);
        mBack.setOnClickListener(this);

      /*  mRef = mRef.child(mPrefs.getTableKey());*/

        mRef = firebaseDatabase.getReference("booked");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Utils.stopProgress(BookedOrderActivity.this);

                SearchItemModel searchItemModel = dataSnapshot.getValue(SearchItemModel.class);
                BookedDetailModel searchBookedList = new BookedDetailModel();

                String tableNo=searchItemModel.getTableNo().toString();
                String itemName = searchItemModel.getSearchItem().toString();
                int itemQuantity = searchItemModel.getItemQuantity();
                searchBookedList.setBookedItemName(itemName);
                searchBookedList.setBookedItemQuantity(itemQuantity);
                mBookedItemList.add(searchBookedList);
              /*  if(tableNo.equalsIgnoreCase(mPrefs.getTableKey())){
                    String itemName = searchItemModel.getSearchItem().toString();
                    int itemQuantity = searchItemModel.getItemQuantity();
                    searchBookedList.setBookedItemName(itemName);
                    searchBookedList.setBookedItemQuantity(itemQuantity);
                    mBookedItemList.add(searchBookedList);
                    Toast.makeText(BookedOrderActivity.this, "" +mPrefs.getTableKey(), Toast.LENGTH_SHORT).show();
                }*/

              /*  String[] strArray = itemName.split(" ");
                for (int i = 0; i < strArray.length; i++) {

                    if(i%2==0){

                      sum=  sum+Integer.parseInt(strArray[i]);
                      //  int number = Integer.parseInt(strArray[i]);
                     //   sum = sum + number;
                        Toast.makeText(BookedOrderActivity.this, "" +strArray[i]+""+sum , Toast.LENGTH_SHORT).show();
                    }
                  *//*  int number = Integer.parseInt(strArray[i]);
                    sum = sum + number;*//*
                  //  Toast.makeText(BookedOrderActivity.this, "" +strArray[i], Toast.LENGTH_SHORT).show();
                    // System.out.println(strArray[i]);
                }*/

           //     mTotalBillPrice.setText("" + dataSnapshot.getValue());
                Log.d("BookedOrderActivity", "" + dataSnapshot.getValue());

                mBookedRv.setHasFixedSize(true);
                mBookedItemsAdapter = new BookedOrderAdapter(getApplication(), mBookedItemList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
                mBookedRv.setLayoutManager(mLayoutManager);
                mBookedRv.setAdapter(mBookedItemsAdapter);

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
