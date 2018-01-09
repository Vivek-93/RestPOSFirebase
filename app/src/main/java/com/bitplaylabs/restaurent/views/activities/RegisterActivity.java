package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public EditText regMobileNumber, regUserName, regEmail, regPass;
    public Spinner mSelectType;
    public ImageView regBackIv;
    public Button registerBtn;
    private FirebaseAuth mAuth;

    String nameValue, passwordValue, phoneNumValue, emailValue, selectRole;

    private DatabaseReference mDatabase;


    UserInformation userInformation;
    // FirebaseUser user=firebaseAuth.getCurrentUser();
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        regUserName = (EditText) findViewById(R.id.reg_act_name_et);
        regMobileNumber = (EditText) findViewById(R.id.reg_act_mobileno_et);
        regEmail = (EditText) findViewById(R.id.reg_act_email_et);
        regPass = (EditText) findViewById(R.id.reg_act_passwo_et);
        registerBtn = (Button) findViewById(R.id.register_act_sign_up_button);
        mSelectType = (Spinner) findViewById(R.id.reg_act_select_type);

        initializeViews();


    }

    private void initializeViews() {
        mAuth = FirebaseAuth.getInstance();

       /* FirebaseUser user = mAuth.getCurrentUser();
        if(user.getUid()!= null) {
            userId = user.getUid();
        }
        else {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
        }*/

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_act_sign_up_button:
                attemptregister();
                break;

        }

    }

    private void attemptregister() {

        nameValue = regUserName.getText().toString().trim();
        passwordValue = regPass.getText().toString().trim();
        phoneNumValue = regMobileNumber.getText().toString().trim();
        emailValue = regEmail.getText().toString().trim();
        selectRole = mSelectType.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nameValue)) {

         //   progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                  //  progressBar.setVisibility(View.GONE);

                    if(task.isSuccessful()){
                        FirebaseUser user1 = mAuth.getCurrentUser();
                        Log.d("Register", "createUserWithEmail:success"+user1.getUid());
                        // userId = mDatabase.push().getKey();
                        UserInformation user = new UserInformation(nameValue, phoneNumValue, emailValue, passwordValue, selectRole);
                        mDatabase.child(user1.getUid()).setValue(user);
                        Log.d("Register", "" + userId);

                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();

                    }else {
                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(RegisterActivity.this, "You are already registered", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


           /* UserInformation user = new UserInformation(nameValue, phoneNumValue, emailValue, passwordValue, selectRole);
            mDatabase.child(userId).setValue(user);

            Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();*/

        }


    }


}
