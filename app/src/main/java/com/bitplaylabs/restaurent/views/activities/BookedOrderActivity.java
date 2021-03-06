package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
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
import com.bitplaylabs.restaurent.extra.BookedModel;
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
    public TextView mTotalBillPrice, mDoneTv;
    public ImageView mBack;
    private List<BookedModel> mBookedItemList;
    // private List<SearchItemModel> mUpdateList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private Sharedpreferences mPrefs;
    long sum = 0;

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
        mBack = (ImageView) findViewById(R.id.act_booked_back_iv);
        mDoneTv = (TextView) findViewById(R.id.act_booked_done_tv);
        mPrefs = Sharedpreferences.getUserDataObj(this);
        initilizeView();

    }

    private void initilizeView() {
        mBookedItemList = new ArrayList<>();
        //     mUpdateList = new ArrayList<>();
        mBack.setOnClickListener(this);
        mDoneTv.setOnClickListener(this);

        mRef = firebaseDatabase.getReference("bookedmain").child(mPrefs.getTableName());
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Utils.stopProgress(BookedOrderActivity.this);

                final String key = dataSnapshot.getKey();
                mRef = firebaseDatabase.getReference("bookedmain").child(mPrefs.getTableName()).child("" + key);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        BookedModel searchItemModel = dataSnapshot.getValue(BookedModel.class);
                        BookedModel searchBookedList = new BookedModel();

                        String tableNo = searchItemModel.getTableNo().toString();
                        String itemName = searchItemModel.getSearchItem().toString();
                        String order_time = searchItemModel.getTime().toString();
                        String key1 = key;
                        int itemQuantity = searchItemModel.getItemQuantity();
                        long itemPrice = searchItemModel.getItemPrice();
                        searchBookedList.setSearchItem(itemName);
                        searchBookedList.setItemQuantity(itemQuantity);
                        searchBookedList.setItemPrice(itemPrice);
                        searchBookedList.setCaptainName(searchItemModel.getCaptainName().toString());
                        searchBookedList.setTableNo(searchItemModel.getTableNo().toString());
                        searchBookedList.setKey(key1);
                        searchBookedList.setTime(order_time);
                        mBookedItemList.add(searchBookedList);
                        sum = sum + (itemPrice * itemQuantity);
                        mTotalBillPrice.setText("" + sum);
                        mBookedRv.setHasFixedSize(true);
                        mBookedItemsAdapter = new BookedOrderAdapter(getApplication(), mBookedItemList, new BookedOrderAdapter.BookedActivityonClick() {
                            @Override
                            public void onClicked(String captain_name, String tableNo, String itemName, long itemPrice, String order_time, String key, String quantity, int position) {

                                List<BookedModel> list = new ArrayList<>();
                                BookedModel searchItemm = new BookedModel();
                                searchItemm.setSearchItem(itemName);
                                searchItemm.setItemQuantity(Integer.parseInt(quantity));
                                searchItemm.setCaptainName(captain_name);
                                searchItemm.setTableNo(tableNo);
                                searchItemm.setItemPrice((long) itemPrice);
                                searchItemm.setTime(order_time);
                                list.add(searchItemm);

                                sum = sum + (itemPrice * Integer.parseInt(quantity));
                                mTotalBillPrice.setText("" + sum);

                            //    Toast.makeText(BookedOrderActivity.this, "" + quantity + key, Toast.LENGTH_SHORT).show();
                                firebaseDatabase.getReference("bookedmain").child(mPrefs.getTableName()).child("" + key).setValue(list);
                                firebaseDatabase.getReference("booked").child(mPrefs.getTableName()).child("" + key).setValue(list);
                                Toast.makeText(BookedOrderActivity.this, "Order updated", Toast.LENGTH_SHORT).show();


                            }

                        });
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

            case R.id.act_booked_done_tv:

                bookedOrderFun();

                break;

            case R.id.act_booked_back_iv:
                Intent intent=new Intent(this,TableDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }

    private void bookedOrderFun() {

        mRef = firebaseDatabase.getReference("");
        mRef.child("tables").child(mPrefs.getTableKey()).child("status").setValue("1");
        mRef.child("tables").child(mPrefs.getTableKey()).child("totalprice").setValue("" + sum);

        Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this,TableDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
