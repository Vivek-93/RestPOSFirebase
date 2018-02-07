package com.bitplaylabs.restaurent.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.UpdateUserInformation;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText mUpdateName, mUpdateNumber, mUpdateAdd, mUpdateFather, mUpdateAadhar, mUpdatePan;
    public Button mUpdateDone;
    public TextView mUpdateRole,mUpdateEmail;
    public ImageView mBack, mUpdatePhoto;
    private Sharedpreferences mPrefs;
    private String name, number, email, address, father, aadhar, pan, profileRole;
    public String updateName, updateEmail, updateRole, updateAdd, updateFather, updatePan, updateNumber;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Uri photoPath;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mPrefs = Sharedpreferences.getUserDataObj(this);
        mRef = firebaseDatabase.getReference("users");
        mUpdateName = (EditText) findViewById(R.id.act_user_profile_update_name_et);
        mUpdateNumber = (EditText) findViewById(R.id.act_user_profile_update_mobileno_et);
        mUpdateEmail = (TextView) findViewById(R.id.act_user_profile_update_email_et);
        mUpdateRole = (TextView) findViewById(R.id.act_user_profile_update_role_et);
        mUpdateAdd = (EditText) findViewById(R.id.act_user_profile_update_address_tv);
        mUpdateFather = (EditText) findViewById(R.id.act_user_profile_update_father_et);
        mUpdateAadhar = (EditText) findViewById(R.id.act_user_profile_update_aadhar_et);
        mUpdatePan = (EditText) findViewById(R.id.act_user_profile_update_pan_et);
        mUpdateDone = (Button) findViewById(R.id.act_user_profile_edit_done_btn);
        mBack = (ImageView) findViewById(R.id.act_user_profile_update_back_iv);
        mUpdatePhoto = (ImageView) findViewById(R.id.act_update_profile_iv);

        initializeViews();

    }


    private void initializeViews() {
        mBack.setOnClickListener(this);
        mUpdateDone.setOnClickListener(this);
        mUpdatePhoto.setOnClickListener(this);
        gettingPreviousDetails();


    }

    private void gettingPreviousDetails() {

        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("mobilenumber");
        email = getIntent().getStringExtra("email");
       /* address = getIntent().getStringExtra("address");
        father = getIntent().getStringExtra("father");
        aadhar = getIntent().getStringExtra("aadhar");
        pan = getIntent().getStringExtra("pan");*/
        profileRole = getIntent().getStringExtra("role");

        mUpdateName.setText(name);
        mUpdateNumber.setText(number);
        mUpdateEmail.setText(email);
        mUpdateRole.setText(profileRole);
      /*  mUpdateAdd.setText(address);
        mUpdateFather.setText(father);
        mUpdateAadhar.setText(aadhar);
        mUpdatePan.setText(pan);*/

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.act_user_profile_edit_done_btn:
                updateFuunction();
                break;

            case R.id.act_update_profile_iv:

                if (Utils.isStoragePermissionGranted(this)) {
                    selectAndCaptureImage();
                }

                break;
            case R.id.act_user_profile_update_back_iv:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("tag", "imagefile" + resultCode + "" + requestCode);
        Log.d("tag", "imagefile" + resultCode + "" + requestCode);

        if (resultCode == -1) {
            if (requestCode == 100) {

                photoPath = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoPath);
                    mUpdatePhoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == 101)//capture from camera
            {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                FileOutputStream fo;
                mUpdatePhoto.setImageBitmap(thumbnail);
                Log.e("capture", "capture" + destination.getAbsolutePath());
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    /**
     * Picking image
     **/
    //capture and select image from gallery
    private void selectAndCaptureImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 101);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select picture"), 100);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void updateFuunction() {

        Utils.showProgress(this);
        gettingDetails();
        uploadImage();
        FirebaseUser user1 = mAuth.getCurrentUser();
        Log.d("Register", "createUserWithEmail:success" + user1.getUid());
        // userId = mDatabase.push().getKey();
        UpdateUserInformation user = new UpdateUserInformation(updateName, updateNumber, updateEmail, updateRole);
        mRef.child(user1.getUid()).setValue(user);
//        mRef.child(user1.getUid()).setValue(FirebaseStorage.getInstance().getReferenceFromUrl("gs://demoproject-cdfc6.appspot.com/"));

        Utils.stopProgress(this);
        Intent intent = new Intent(UserProfileUpdateActivity.this, UserProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


        //  mPref.setUsername(updateName);
    }

    private void uploadImage() {

        if (photoPath != null) {

            Utils.showProgress(this);
            //    mRef = firebaseDatabase.getReference("users").child(mPrefs.getUserId());
            StorageReference reff = mStorageRef.child("users").child(mPrefs.getUserId()).child(photoPath.getLastPathSegment());
            String profileUrl = photoPath.getLastPathSegment();
            reff.putFile(photoPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Utils.stopProgress(UserProfileUpdateActivity.this);
                    final Uri imageurl=taskSnapshot.getDownloadUrl();
                    mRef = firebaseDatabase.getReference();
                    mRef.child("user").child(mPrefs.getUserId()).child("profile_pic").setValue(imageurl.toString());
                }
            });

           /* StorageReference ref= mStorageRef.child("image/"+ UUID.randomUUID().toString());
            ref.putFile(photoPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Utils.stopProgress(UserProfileUpdateActivity.this);
                    Toast.makeText(UserProfileUpdateActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Utils.stopProgress(UserProfileUpdateActivity.this);
                    Toast.makeText(UserProfileUpdateActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress= (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());


                }
            });*/


        }


    }

    private void gettingDetails() {
        updateName = mUpdateName.getText().toString();
        updateNumber = mUpdateNumber.getText().toString();
        updateEmail = mUpdateEmail.getText().toString();
        updateRole = mUpdateRole.getText().toString();
      /*  updateAdd = mUpdateAdd.getText().toString();
        updateFather = mUpdateFather.getText().toString();

        updatePan = mUpdatePan.getText().toString();*/
    }

}
