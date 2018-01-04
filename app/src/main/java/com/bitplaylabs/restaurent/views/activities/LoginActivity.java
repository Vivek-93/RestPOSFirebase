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
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

    public EditText mEmailET, mPasswordET;
    public Button mLoginButton;
    public TextView mForgotTv, mRegisterTv;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mStateListner;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
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
        mAuth = FirebaseAuth.getInstance();

        mRegisterTv.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    /*   @Override
       protected void onStart() {
           super.onStart();

           mAuth.addAuthStateListener(mStateListner);
       }

       @Override
       protected void onStop() {
           super.onStop();
       }
   */
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

                    Utils.stopProgress(LoginActivity.this);
                    if (task.isSuccessful()) {
                        finish();
                        Log.d("LoginActivity", "signInWithEmail:success");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    }else if(!task.isSuccessful()) {
                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

           /* Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);*/
        }


    }

}
