package com.bitplaylabs.restaurent.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.GuestDetails;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.bitplaylabs.restaurent.views.fragments.CaptainCategoryFragment;
import com.bitplaylabs.restaurent.views.fragments.CaptainSearchFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TableDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    public FrameLayout mFramelayout;
    private FragmentManager mFragmentManager;
    public ImageView mBookedItems, mBackIv;

    private Context context;
    public TextView mGuestName, mGuestPhone, mGuestTable, mKotNumber;

    private Button mCetogery, mSearch;
    private Sharedpreferences mPrefs;
    String keyId, tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("guestdetails");
        mCetogery = (Button) findViewById(R.id.act_table_details_catogery_btn);
        mSearch = (Button) findViewById(R.id.act_table_details_search_btn);
        mFramelayout = (FrameLayout) findViewById(R.id.act_table_details_framelayout);
        mGuestName = (TextView) findViewById(R.id.guest_name_tv);
        mGuestPhone = (TextView) findViewById(R.id.guest_mobile_number_tv);
        mGuestTable = (TextView) findViewById(R.id.guest_table_number_tv);
        mKotNumber = (TextView) findViewById(R.id.guest_kot_number_tv);
        mBackIv = (ImageView) findViewById(R.id.act_table_details_back_iv);
        mBookedItems = (ImageView) findViewById(R.id.act_table_details_toolbar_iv);
        context = TableDetailsActivity.this;
        mPrefs = Sharedpreferences.getUserDataObj(this);
        keyId = mPrefs.getTableName();
        // keyId=getIntent().getExtras().getString("TableKey");
        //  tableNo=getIntent().getExtras().getString("TableNumber");

        initializeViews();
    }

    private void initializeViews() {
        mFragmentManager = getSupportFragmentManager();


        openCatogeryFragment();

        mBookedItems.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mCetogery.setOnClickListener(this);
        mBackIv.setOnClickListener(this);


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showData(DataSnapshot dataSnapshot) {


        Utils.stopProgress(this);


        Log.d("TableDetailsActivity", "" + dataSnapshot.child(mPrefs.getUserId()).child(keyId).getValue());
        String guestName = dataSnapshot.child(mPrefs.getUserId()).child(keyId).getValue(GuestDetails.class).getGuestname().toString();
        String guestPhone = dataSnapshot.child(mPrefs.getUserId()).child(keyId).getValue(GuestDetails.class).getGuestnumber().toString();
        String headCount = dataSnapshot.child(mPrefs.getUserId()).child(keyId).getValue(GuestDetails.class).getHeadcount().toString();
        String kot = dataSnapshot.child(mPrefs.getUserId()).child(keyId).getValue(GuestDetails.class).getKot().toString();
        //   Toast.makeText(context, ""+kot, Toast.LENGTH_SHORT).show();
        mGuestName.setText(guestName);
        mGuestPhone.setText(guestPhone);
        mGuestTable.setText(keyId);
        mKotNumber.setText(kot);
        mPrefs.setKot(kot);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_table_details_toolbar_iv:

                openBookedOrderActivityFun();
                break;

            case R.id.act_table_details_catogery_btn:
                openCatogeryFragment();
                break;

            case R.id.act_table_details_search_btn:
                openSearchFragment();
                break;

            case R.id.act_table_details_back_iv:
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }

    }

    private void openBookedOrderActivityFun() {

        Intent intent = new Intent(TableDetailsActivity.this, BookedOrderActivity.class);
        startActivity(intent);
    }

    private void openSearchFragment() {

        CaptainSearchFragment csf = new CaptainSearchFragment();
        mFragmentManager.beginTransaction().replace(R.id.act_table_details_framelayout, csf).addToBackStack(null).commit();
    }

    private void openCatogeryFragment() {

        CaptainCategoryFragment ccf = new CaptainCategoryFragment();
        mFragmentManager.beginTransaction().replace(R.id.act_table_details_framelayout, ccf).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        super.onBackPressed();
    }


}
