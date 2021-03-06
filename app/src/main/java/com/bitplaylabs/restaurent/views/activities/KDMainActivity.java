package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.KDMainAdapter;
import com.bitplaylabs.restaurent.adapters.KdHomeViewPagerAdapter;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.extra.UserGetInformation;
import com.bitplaylabs.restaurent.services.MyFirebaseMessaging1;
import com.bitplaylabs.restaurent.services.ReadyOrder;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class KDMainActivity extends AppCompatActivity implements View.OnClickListener {

    // FragmentManager Instance
    private FragmentManager mFragmentManager;

    //KDHomeViewPager Adapter
    private KdHomeViewPagerAdapter mKdHomeViewPagerAdapter;

    public TabLayout mKdTablayout;
    public ViewPager mKdViewPager;
    public RecyclerView mKdMainRv;
    public ImageView mLogout;

    private KDMainAdapter mKDMainAdapter;
    private TextView mName;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    String userId;
    FirebaseUser user;
    private List<TableDetails> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdmain);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();
      //  ReadyOrder.currentToken = FirebaseInstanceId.getInstance().getToken();
        mKdTablayout = (TabLayout) findViewById(R.id.act_kd_main_table_layout);
        mKdViewPager = (ViewPager) findViewById(R.id.act_kd_main_viewpager);
        mKdMainRv = (RecyclerView) findViewById(R.id.act_kd_main_rv);
        mName = (TextView) findViewById(R.id.act_kd_main_user_name_tv);
        mLogout=(ImageView)findViewById(R.id.act_kd_main_logout_iv);

        initializeViews();

    }

    private void initializeViews() {

        mLogout.setOnClickListener(this);
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
      //   Toast.makeText(this, ""+ReadyOrder.currentToken, Toast.LENGTH_SHORT).show();

        mFragmentManager = getSupportFragmentManager();

        settingUpTabLayout();
        settingUpViewPager();

        data = new ArrayList<>();
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
                TableDetails value = dataSnapshot.getValue(TableDetails.class);
                TableDetails fire = new TableDetails();
                String id = value.getTableid();
                String tablename = value.getTablename();
                String tablestatus = value.getStatus();
                String key = dataSnapshot.getKey().toString();
                fire.setTableid(id);
                fire.setTablename(tablename);
                fire.setTablekey(key);
                fire.setStatus(tablestatus);
                data.add(fire);
                //  mPrefs.setTableKey(key);
                mKdMainRv.setHasFixedSize(true);
                mKDMainAdapter = new KDMainAdapter(KDMainActivity.this, data);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(KDMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                mKdMainRv.setLayoutManager(mLayoutManager);
                mKdMainRv.setAdapter(mKDMainAdapter);

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

        Log.d("MainActivity", "" + dataSnapshot.child(userId).getValue().toString());
        String userName = dataSnapshot.child(userId).getValue(UserGetInformation.class).getName().toString();
        String userRole = dataSnapshot.child(userId).getValue(UserGetInformation.class).getSelectrole().toString();
        mName.setText(userName);
        // mUserRole.setText(userRole);
        // Log.d("MainActivity", "username" + userName);
    }

    private void settingUpViewPager() {

        mKdHomeViewPagerAdapter = new KdHomeViewPagerAdapter(mFragmentManager, mKdTablayout.getTabCount());
        mKdViewPager.setAdapter(mKdHomeViewPagerAdapter);
        mKdViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mKdTablayout));
        mKdViewPager.setOffscreenPageLimit(0);

    }

    private void settingUpTabLayout() {
        mKdTablayout.addTab(mKdTablayout.newTab().setText("New Order"));
        mKdTablayout.addTab(mKdTablayout.newTab().setText("Ready"));
        mKdTablayout.addTab(mKdTablayout.newTab().setText("Completed"));
        mKdTablayout.addTab(mKdTablayout.newTab().setText("Reports & Setting"));
        mKdTablayout.addTab(mKdTablayout.newTab().setText("Comolidated report"));
        mKdTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mKdViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mKdViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.act_kd_main_logout_iv:

                mAuth.signOut();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);

                return;
        }

    }
}
