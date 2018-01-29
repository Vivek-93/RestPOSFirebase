package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.UserGetInformation;
import com.bitplaylabs.restaurent.extra.UserInformation;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText mEmailET, mPasswordET;
    public Button mLoginButton;
    public TextView mForgotTv, mRegisterTv;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mStateListner;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;
    private Sharedpreferences mPrefs;
    private String userRole;
    FirebaseUser user;
    String Loggedin_User_Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mPrefs = Sharedpreferences.getUserDataObj(this);
     /*   if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/
        mEmailET = (EditText) findViewById(R.id.login_act_email_et);
        mPasswordET = (EditText) findViewById(R.id.login_act_password_et);
        mLoginButton = (Button) findViewById(R.id.login_act_login_button);
        mForgotTv = (TextView) findViewById(R.id.login_act_forgot_password_tv);
        mRegisterTv = (TextView) findViewById(R.id.login_act_sign_up_tv);

        initilizeView();


    }

    private void initilizeView() {


        mRegisterTv.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
      /*  Loggedin_User_Email = user.getEmail();
        OneSignal.sendTag("User_ID", Loggedin_User_Email);*/

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login_act_login_button:
                login_func();
                break;

            case R.id.login_act_sign_up_tv:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void login_func() {
        Utils.showProgress(this);
        String name = mEmailET.getText().toString();
        String password = mPasswordET.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {

            mAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        final FirebaseUser user1 = mAuth.getCurrentUser();
                        mRef = firebaseDatabase.getReference("users");
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Utils.stopProgress(LoginActivity.this);

                                userRole = dataSnapshot.child(user1.getUid()).getValue(UserGetInformation.class).getSelectrole().toString();

                                if (userRole.equalsIgnoreCase("Captain")) {

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                } else if (userRole.equalsIgnoreCase("Kitchen Display")) {

                                    Intent intent = new Intent(LoginActivity.this, KDMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    } else if (!task.isSuccessful()) {
                        Utils.stopProgress(LoginActivity.this);
                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

}
