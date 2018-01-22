package com.bitplaylabs.restaurent.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.GuestDetails;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillPrintActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView mPrintBillTv, mGuestDetailsTv, mCaptainDetail;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private ImageView mBackIv;
    String tableKey, captainId;

    private List<SearchItemModel> billingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_print);
        firebaseDatabase = FirebaseDatabase.getInstance();

        tableKey = getIntent().getExtras().getString("billingTableKey");
        captainId = getIntent().getExtras().getString("captainID");
        mPrintBillTv = (TextView) findViewById(R.id.act_billprint_tv);
        mCaptainDetail = (TextView) findViewById(R.id.act_billprint_captain_tv);
        mGuestDetailsTv = (TextView) findViewById(R.id.act_billprint_guest_tv);
        mBackIv=(ImageView)findViewById(R.id.act_printbill_back_iv);


        initializeViews();

    }

    private void initializeViews() {
        billingList = new ArrayList<>();
mBackIv.setOnClickListener(this);
        mRef = firebaseDatabase.getReference("guestdetails").child(captainId).child(tableKey);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GuestDetails getGuestDetails = dataSnapshot.getValue(GuestDetails.class);

                String guestName = getGuestDetails.getGuestname();
                String guestPhone = getGuestDetails.getGuestnumber();
                String guestHead = getGuestDetails.getHeadcount();
                mGuestDetailsTv.setText("Name    : " + guestName + "\n" + "Phone   : " + guestPhone + "\n" + "Head Count  :" + guestHead);
                //  mGuestDetailsTv.setText(""+dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef = firebaseDatabase.getReference("booked").child(tableKey);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                SearchItemModel getItemsDetails = dataSnapshot.getValue(SearchItemModel.class);
                SearchItemModel searchItemModel = new SearchItemModel();
                String itemName = getItemsDetails.getSearchItem().toString();
                String itemPrice = getItemsDetails.getItemPrice().toString();
                String tableNumber = getItemsDetails.getTableNo().toString();
                String captainName = getItemsDetails.getCaptainName().toString();
                String itemQuantity = String.valueOf(getItemsDetails.getItemQuantity());
                searchItemModel.setSearchItem(itemName);
                searchItemModel.setItemQuantity(Integer.parseInt(itemQuantity));
                searchItemModel.setItemPrice(Long.valueOf(itemPrice));
                searchItemModel.setTableNo(tableNumber);
                searchItemModel.setCaptainName(captainName);
                billingList.add(searchItemModel);
                //   mPrintBillTv.setText(""+billingList);
                StringBuilder builder = new StringBuilder();
                long sum = 0;
                for (int i = 0; i < billingList.size(); i++) {

                    long totalprice = billingList.get(i).getItemQuantity() * billingList.get(i).getItemPrice();
                    builder.append("\n" + (billingList.get(i).getSearchItem().toString() + "  " + billingList.get(i).getItemQuantity() + "*" + billingList.get(i).getItemPrice() + "=" + totalprice+"Rs"));
                    sum = sum + totalprice;

                    //builder.append(details + "\n");
                }
                mPrintBillTv.setText(builder.toString()+ "\n\n     Total ammount : "+sum+"Rs");
                mCaptainDetail.setText("Captain : " + captainName + "              " + tableNumber);

                // mPrintBillTv.setText(""+dataSnapshot.getValue()+"size"+billingList.size());
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


       /* mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList<SearchItemModel> labels = new ArrayList<SearchItemModel>();
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    SearchItemModel dailyItem = data.getValue(SearchItemModel.class);

                    labels.add(dailyItem);



                }

              //  mPrintBillTv.setText(labels.size());
              *//*  title.setText(labels.get(position));

                // Do blabla with the title
                String title = title.getText().toString();*//*
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.act_printbill_back_iv:
                onBackPressed();
                finish();
                break;
        }
    }
}
