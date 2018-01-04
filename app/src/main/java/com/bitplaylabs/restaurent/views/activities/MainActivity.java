package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.CaptionRecyclerViewAdaptor;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView mLogout, mProfile;
    public TextView mUserName, mUserRole;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mStateListener;
    private DatabaseReference mRef;
    String userId;
    FirebaseUser user;
    private Sharedpreferences mPrefs;
    public RecyclerView mRecyclerView;
    private CaptionRecyclerViewAdaptor captionRecyclerViewAdaptor;
    private List<TableDetails> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();

        Log.d("MainActivity", "" + userId);

        mLogout = (ImageView) findViewById(R.id.act_main_logout_iv);
        mProfile = (ImageView) findViewById(R.id.act_main_profile_iv);
        mUserName = (TextView) findViewById(R.id.act_main_toolbar_name_tv);
        mUserRole = (TextView) findViewById(R.id.act_main_toolbar_role_tv);
        mRecyclerView=(RecyclerView)findViewById(R.id.act_home_caption_rv);
        mPrefs = Sharedpreferences.getUserDataObj(this);
        mPrefs.setUserId(userId);
        Utils.showProgress(this);

        initializeViews();

    }

    private void initializeViews() {
        data = new ArrayList<>();

       // data = new ArrayList<>();
        mLogout.setOnClickListener(this);
        mProfile.setOnClickListener(this);

        //  mUserName.setText(userName);
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

                Log.d("MainActivity","data1"+dataSnapshot.getValue());
                TableDetails list= new  TableDetails();
                list= dataSnapshot.getValue(TableDetails.class);
                data.add(list);
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
     /*   mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    Log.d("MainActivity","data"+dataSnapshot1.getValue().toString());

                    TableDetails list= new TableDetails();
                    list= dataSnapshot1.getValue(TableDetails.class);
                    data.add(list);
                  //  data.add(tableDetails);
//                    Log.d("MainActivity","data1"+dataSnapshot1.getValue(TableDetails.class));
                   *//* TableDetails value = dataSnapshot1.getValue(TableDetails.class);
                    TableDetails fire = new TableDetails();
                    String id = value.getTableId();

                    String tablename = value.getTableName();
                    fire.setTableId(id);
                    fire.setTableName(tablename);
                    data.add(fire);
                //  data.add(value);*//*

                    Log.d("MainActivity","list size"+data.size());
                }
            //    Log.d("MainActivity","data"+dataSnapshot1.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });*/

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        captionRecyclerViewAdaptor = new CaptionRecyclerViewAdaptor(this,data/*, data, new CaptionRecyclerViewAdaptor.ProceedButtonClick() {
            @Override
            public void onClicked(String tableid, String tableno, String headcount, String guestname, String phoneno) {

                mITableDetailPresenter.guestDetailsApiCall(Integer.valueOf(tableno), Integer.valueOf(headcount), guestname,
                        Integer.valueOf(phoneno));

                mPref.setTableId(tableid);

            }
        }*/);
        mRecyclerView.setAdapter(captionRecyclerViewAdaptor);

    }

    private void  showData(DataSnapshot dataSnapshot) {

        Utils.stopProgress(this);


        Log.d("MainActivity",""+dataSnapshot.child(userId).getValue().toString());
        String userName = dataSnapshot.child(userId).getValue(UserGetInformation.class).getName().toString();
        String userRole = dataSnapshot.child(userId).getValue(UserGetInformation.class).getSelectrole().toString();
        mUserName.setText(userName);
        mUserRole.setText(userRole);
        // Log.d("MainActivity", "username" + userName);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_main_logout_iv:
                mAuth.signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;

            case R.id.act_main_profile_iv:

                Intent intent1 = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent1);
                break;
        }


    }

}
