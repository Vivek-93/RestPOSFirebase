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
    public TextView mTotalBillPrice, mDoneTv;
    public ImageView mBack;
    private List<SearchItemModel> mBookedItemList;
    private List<SearchItemModel> mUpdateList;

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
        mUpdateList = new ArrayList<>();
        mBack.setOnClickListener(this);
        mDoneTv.setOnClickListener(this);

        mRef = firebaseDatabase.getReference("booked").child(mPrefs.getTableKey());
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Utils.stopProgress(BookedOrderActivity.this);
                String key = dataSnapshot.getKey();
                mRef = firebaseDatabase.getReference("booked").child(mPrefs.getTableKey()).child("" + key);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        SearchItemModel searchItemModel = dataSnapshot.getValue(SearchItemModel.class);
                        SearchItemModel searchBookedList = new SearchItemModel();

                        String tableNo = searchItemModel.getTableNo().toString();
                        String itemName = searchItemModel.getSearchItem().toString();
                        int itemQuantity = searchItemModel.getItemQuantity();
                        long itemPrice = searchItemModel.getItemPrice();
                        searchBookedList.setSearchItem(itemName);
                        searchBookedList.setItemQuantity(itemQuantity);
                        searchBookedList.setItemPrice(itemPrice);
                        searchBookedList.setCaptainName(searchItemModel.getCaptainName().toString());
                        searchBookedList.setTableNo(searchItemModel.getTableNo().toString());
                        mBookedItemList.add(searchBookedList);
                        sum = sum + (itemPrice * itemQuantity);
                        mTotalBillPrice.setText("" + sum);
                        mBookedRv.setHasFixedSize(true);
                        mBookedItemsAdapter = new BookedOrderAdapter(getApplication(), mBookedItemList, new BookedOrderAdapter.BookedActivityonClick() {
                            @Override
                            public void onClicked(List<SearchItemModel> data, int postion) {
                                SearchItemModel searchItemModel = new SearchItemModel();
                                searchItemModel.setSearchItem(data.get(postion).getSearchItem());
                                searchItemModel.setItemQuantity(data.get(postion).getItemQuantity());
                                searchItemModel.setCaptainName(data.get(postion).getCaptainName());
                                searchItemModel.setTableNo(data.get(postion).getTableNo());
                                searchItemModel.setItemPrice(data.get(postion).getItemPrice());
                                mUpdateList.add(searchItemModel);

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
                onBackPressed();
                break;

        }
    }

    private void bookedOrderFun() {

        mRef = firebaseDatabase.getReference("");
        mRef.child("tables").child(mPrefs.getTableKey()).child("status").setValue("1");
        mRef.child("tables").child(mPrefs.getTableKey()).child("totalprice").setValue("" + sum);
        mBookedRv.setHasFixedSize(true);
        mBookedItemList.clear();
        mBookedItemsAdapter = new BookedOrderAdapter(getApplication(), mUpdateList, new BookedOrderAdapter.BookedActivityonClick() {
            @Override
            public void onClicked(List<SearchItemModel> data, int postion) {

                Toast.makeText(BookedOrderActivity.this, "" + data.get(postion).getItemQuantity(), Toast.LENGTH_SHORT).show();
                //   Toast.makeText(BookedOrderActivity.this, ""+postion, Toast.LENGTH_SHORT).show();
                mRef.child("booked").child(mPrefs.getTableKey()).setValue(data);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        mBookedRv.setLayoutManager(mLayoutManager);
        mBookedRv.setAdapter(mBookedItemsAdapter);

        Toast.makeText(this, "Order placed", Toast.LENGTH_SHORT).show();

    }
}
