package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.CaptionRecyclerViewAdaptor;
import com.bitplaylabs.restaurent.adapters.CashierRecyclerViewAdaptor;
import com.bitplaylabs.restaurent.extra.GuestDetails;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.extra.UserGetInformation;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CashierMainActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView mLogout, mProfile;
    public TextView mUserName, mUserRole;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    String userId;
    FirebaseUser user;
    private Sharedpreferences mPrefs;
    public RecyclerView mRecyclerView;
    private CashierRecyclerViewAdaptor mCashierRecyclerViewAdaptor;
    private List<TableDetails> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        Log.d("MainActivity", "" + userId);

        mLogout = (ImageView) findViewById(R.id.act_cashier_main_logout_iv);
        mProfile = (ImageView) findViewById(R.id.act_cashier_main_profile_iv);
        mUserName = (TextView) findViewById(R.id.act_cashier_main_toolbar_name_tv);
        mUserRole = (TextView) findViewById(R.id.act_cashier_main_toolbar_role_tv);
        mRecyclerView=(RecyclerView)findViewById(R.id.act_home_cashier_rv);
        mPrefs = Sharedpreferences.getUserDataObj(this);
        mPrefs.setUserId(userId);
      //  Utils.showProgress(this);

        initializeViews();


    }
    @Override
    protected void onResume() {
        super.onResume();
        showUserProfile();


    }


    private void initializeViews() {
        data = new ArrayList<>();
        mLogout.setOnClickListener(this);
        mProfile.setOnClickListener(this);

        mRef = firebaseDatabase.getReference("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef = firebaseDatabase.getReference("tables");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Utils.stopProgress(CashierMainActivity.this);
                TableDetails value = dataSnapshot.getValue(TableDetails.class);
                TableDetails fire = new TableDetails();
                String id = value.getTableid();
                final String tablename = value.getTablename();
                String tablestatus = value.getStatus();
                String totalprice = value.getTotalprice();
                String key = dataSnapshot.getKey().toString();
                fire.setTableid(id);
                fire.setTablename(tablename);
                fire.setTablekey(key);
                fire.setStatus(tablestatus);
                fire.setTotalprice(totalprice);
                data.add(fire);

                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new GridLayoutManager(CashierMainActivity.this, 2));
                mCashierRecyclerViewAdaptor = new CashierRecyclerViewAdaptor(CashierMainActivity.this, data/*, new CaptionRecyclerViewAdaptor.ProceedButtonClick() {
                    @Override
                    public void onClicked(String tablekey, String tablename, String headcount, String guestname, String phoneno, String kot) {
                        //  mPrefs.setTableKey(tablekey);
                        try {

                             GuestDetails guestDetails = new GuestDetails(guestname, phoneno, headcount, kot);
                            firebaseDatabase.getReference().child("guestdetails").child(userId).child(tablename).setValue(guestDetails);
                            Intent intent = new Intent(CashierMainActivity.this, TableDetailsActivity.class);
                            intent.putExtra("TableKey", tablekey);
                            // intent.putExtra("TableNumber", tableno);
                            startActivity(intent);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }*/);
                mRecyclerView.setAdapter(mCashierRecyclerViewAdaptor);

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

        showUserProfile();
    }


    private void showUserProfile() {



        mRef = firebaseDatabase.getReference("user").child(mPrefs.getUserId());
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Picasso.with(getApplicationContext()).load("" + dataSnapshot.getValue()).placeholder(R.drawable.userprofile).into(mProfile);
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

    private void showData(DataSnapshot dataSnapshot) {

      //  Log.d("MainActivity", "" + dataSnapshot.child(userId).getValue().toString());
        String userName = dataSnapshot.child(userId).getValue(UserGetInformation.class).getName().toString();
        String userRole = dataSnapshot.child(userId).getValue(UserGetInformation.class).getSelectrole().toString();
        mUserName.setText(userName);
      //   mPrefs.setLoggedInUsername(userName);
        mUserRole.setText(userRole);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_cashier_main_logout_iv:
                mAuth.signOut();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.act_cashier_main_profile_iv:

                Intent intent1 = new Intent(this, UserProfileActivity.class);
                intent1.putExtra("fommingfrom","Cashier");
                startActivity(intent1);
                break;
        }

    }
}
