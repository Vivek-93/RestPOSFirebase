package com.bitplaylabs.restaurent.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BillPrintActivity extends AppCompatActivity {

    public TextView mPrintBillTv;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    String tableKey;

    private List<SearchItemModel> billingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_print);
        firebaseDatabase = FirebaseDatabase.getInstance();

        tableKey=getIntent().getExtras().getString("billingTableKey");
        mPrintBillTv=(TextView)findViewById(R.id.act_billprint_tv);


        initializeViews();

    }

    private void initializeViews() {
        billingList=new ArrayList<>();
        mRef = firebaseDatabase.getReference("booked").child(tableKey);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                SearchItemModel getItemsDetails=dataSnapshot.getValue(SearchItemModel.class);
                SearchItemModel searchItemModel=new SearchItemModel();
                String itemName= getItemsDetails.getSearchItem().toString();
                String itemPrice= getItemsDetails.getItemPrice().toString();
                String tableNumber= getItemsDetails.getTableNo().toString();
                String captainName= getItemsDetails.getCaptainName().toString();
                String itemQuantity= String.valueOf(getItemsDetails.getItemQuantity());
                searchItemModel.setSearchItem(itemName);
                searchItemModel.setItemQuantity(Integer.parseInt(itemQuantity));
                searchItemModel.setItemPrice(Long.valueOf(itemPrice));
                searchItemModel.setTableNo(tableNumber);
                searchItemModel.setCaptainName(captainName);
                billingList.add(searchItemModel);
                //   mPrintBillTv.setText(""+billingList);
                mPrintBillTv.setText(""+dataSnapshot.getValue()+"size"+billingList.size());
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

}
