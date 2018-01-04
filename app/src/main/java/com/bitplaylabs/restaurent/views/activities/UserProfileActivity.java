package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.UserGetInformation;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView profileMobileNumber, profileUserName, profileEmail, profileAddress, profileFathers, profileAadharno, profilePan, profileRole;
    public ImageView mBackIv ,mEditImage,mUserProfile;

    private Sharedpreferences mPrefs;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("users");
        mPrefs = Sharedpreferences.getUserDataObj(this);
        profileUserName = (TextView) findViewById(R.id.act_user_profile_name_tv);
        profileMobileNumber = (TextView) findViewById(R.id.act_user_profile_mobileno_tv);
        profileEmail = (TextView) findViewById(R.id.act_user_profile_email_tv);
        profileAddress = (TextView) findViewById(R.id.act_user_profile_address_tv);
        profileFathers = (TextView) findViewById(R.id.act_user_profile_father_tv);
        profileAadharno = (TextView) findViewById(R.id.act_user_profile_aadhar_tv);
        profilePan = (TextView) findViewById(R.id.act_user_profile_pan_tv);
        profileRole = (TextView) findViewById(R.id.act_user_profile_role_tv);
        mBackIv = (ImageView) findViewById(R.id.act_user_profile_back_iv);
        mEditImage=(ImageView)findViewById(R.id.act_user_profile_edit_iv);
        mUserProfile=(ImageView)findViewById(R.id.act_user_profile_iv);

        Utils.showProgress(this);

        initializeViews();

    }

    private void initializeViews() {

        mBackIv.setOnClickListener(this);
        mEditImage.setOnClickListener(this);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showUserDetails(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showUserDetails(DataSnapshot dataSnapshot) {

        Utils.stopProgress(this);

        Log.d("UserProfileActivity",""+dataSnapshot.child(mPrefs.getUserId()).getValue().toString());
        String userName = dataSnapshot.child(mPrefs.getUserId()).getValue(UserGetInformation.class).getName().toString();
        String userRole = dataSnapshot.child(mPrefs.getUserId()).getValue(UserGetInformation.class).getSelectrole().toString();
        String userEmail= dataSnapshot.child(mPrefs.getUserId()).getValue(UserGetInformation.class).getEmail().toString();
        String userPhone= dataSnapshot.child(mPrefs.getUserId()).getValue(UserGetInformation.class).getNumber().toString();
       // String userImage= dataSnapshot.child(mPrefs.getUserId()).getValue(UserGetInformation.class).getProfilepic().toString();

       // Log.d("image","url"+userImage);
        profileUserName.setText(userName);
        profileRole.setText(userRole);
        profileEmail.setText(userEmail);
        profileMobileNumber.setText(userPhone);
        //  Glide.with(this).load(userImage).into(mUserProfile);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_user_profile_back_iv:
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.act_user_profile_edit_iv:
                userProfileEditFun();
                break;


        }

    }

    private void userProfileEditFun() {
        Intent intent=new Intent(UserProfileActivity.this,UserProfileUpdateActivity.class);
        intent.putExtra("name",profileUserName.getText().toString());
        intent.putExtra("mobilenumber",profileMobileNumber.getText().toString());
        intent.putExtra("email",profileEmail.getText().toString());
      /*  intent.putExtra("address",profileAddress.getText().toString());
        intent.putExtra("father",profileFathers.getText().toString());
        intent.putExtra("aadhar",profileAadharno.getText().toString());
        intent.putExtra("pan",profilePan.getText().toString());*/
        intent.putExtra("role",profileRole.getText().toString());
        startActivity(intent);

    }

}
