package com.bitplaylabs.restaurent.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.GuestDetails;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.bitplaylabs.restaurent.utils.Utils.context;

public class BillPrintActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView mPrintBillTv, mGuestDetailsTv, mCaptainDetail, mBillPrintDoneTv;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private ImageView mBackIv;
    String tableKey, captainId;
    //  public PDFView mPdf;

    private List<SearchItemModel> billingList;
    private Sharedpreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_print);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mPrefs = Sharedpreferences.getUserDataObj(this);
        tableKey = getIntent().getExtras().getString("billingTableKey");
        captainId = getIntent().getExtras().getString("captainID");
        mPrintBillTv = (TextView) findViewById(R.id.act_billprint_tv);
        mCaptainDetail = (TextView) findViewById(R.id.act_billprint_captain_tv);
        mGuestDetailsTv = (TextView) findViewById(R.id.act_billprint_guest_tv);
        mBillPrintDoneTv = (TextView) findViewById(R.id.act_printbill_done_tv);
        mBackIv = (ImageView) findViewById(R.id.act_printbill_back_iv);
        //   mPdf=(PDFView)findViewById(R.id.pdfView);


        initializeViews();


    }

    private void initializeViews() {

        billingList = new ArrayList<>();
        mBackIv.setOnClickListener(this);
        mBillPrintDoneTv.setOnClickListener(this);
        mRef = firebaseDatabase.getReference("guestdetails").child(captainId).child(tableKey);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GuestDetails getGuestDetails = dataSnapshot.getValue(GuestDetails.class);

                String guestName = getGuestDetails.getGuestname();
                String guestPhone = getGuestDetails.getGuestnumber();
                String guestHead = getGuestDetails.getHeadcount();
                mGuestDetailsTv.setText("Name    : " + guestName + "\n" + "Phone   : " + guestPhone + "\n" + "Head Count  :" + guestHead);
                //mGuestDetailsTv.setText(""+dataSnapshot);
                mPrefs.setGuestBillPrintDetails(mGuestDetailsTv.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRef = firebaseDatabase.getReference("bookedmain").child(tableKey);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();

                Toast.makeText(BillPrintActivity.this, ""+key, Toast.LENGTH_SHORT).show();

                mRef = firebaseDatabase.getReference("bookedmain").child(tableKey).child("" + key);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Utils.stopProgress(BillPrintActivity.this);
                        if (dataSnapshot.getValue() == null) {

                            Toast.makeText(BillPrintActivity.this, "No Order is placed", Toast.LENGTH_SHORT).show();

                        } else {
                            SearchItemModel getItemsDetails = dataSnapshot.getValue(SearchItemModel.class);
                            SearchItemModel searchItemModel = new SearchItemModel();
                            String itemName = getItemsDetails.getSearchItem().toString();
                            String itemPrice = getItemsDetails.getItemPrice().toString();
                            String tableNumber = getItemsDetails.getTableNo().toString();
                            String captainName = getItemsDetails.getCaptainName().toString();
                            String itemQuantity = String.valueOf(getItemsDetails.getItemQuantity());
                            searchItemModel.setSearchItem(itemName);
                            searchItemModel.setItemQuantity(Integer.parseInt(itemQuantity));
                            searchItemModel.setItemPrice(Long.valueOf(itemPrice));
                            searchItemModel.setTableNo(tableNumber);
                            searchItemModel.setCaptainName(captainName);
                            billingList.add(searchItemModel);
                            //   mPrintBillTv.setText(""+billingList);
                            StringBuilder builder = new StringBuilder();
                            long sum = 0;
                            for (int i = 0; i < billingList.size(); i++) {

                                long totalprice = billingList.get(i).getItemQuantity() * billingList.get(i).getItemPrice();
                                builder.append("\n" + (billingList.get(i).getSearchItem().toString() + "  " + billingList.get(i).getItemQuantity() + "*" + billingList.get(i).getItemPrice() + "=" + totalprice + "Rs"));
                                sum = sum + totalprice;

                                //builder.append(details + "\n");
                            }
                            mPrintBillTv.setText(builder.toString() + "\n\n     Total ammount : " + sum + "Rs");
                            mCaptainDetail.setText("Captain : " + captainName + "              " + tableNumber);

                            mPrefs.setCaptainBillPrintDetails(mCaptainDetail.getText().toString());
                            mPrefs.setBillPrintDetails(mPrintBillTv.getText().toString());

                        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.act_printbill_done_tv:
                billPrintFunction();
                break;

            case R.id.act_printbill_back_iv:
                onBackPressed();
                finish();
                break;
        }
    }

    private void billPrintFunction() {

        AbstractViewRenderer page = new AbstractViewRenderer(this, R.layout.content_bill_print) {
            private String _text = mPrefs.getGuestBillPrintDetails();
            private String _text_captain = mPrefs.getCaptainBillPrintDetails();
            private String _text_bill = mPrefs.getBillPrintDetails();

            public void setText(String text, String text_captain, String text_bill) {
                _text = text;
                _text_captain = text_captain;
                _text_bill = text_bill;
            }

            @Override
            protected void initView(View view) {
                TextView tv_hello = (TextView) view.findViewById(R.id.act_billprint_guest_tv);
                TextView tv_captain = (TextView) view.findViewById(R.id.act_billprint_captain_tv);
                TextView tv_bill = (TextView) view.findViewById(R.id.act_billprint_tv);
                tv_hello.setText(_text);
                tv_captain.setText(_text_captain);
                tv_bill.setText(_text_bill);
            }
        };

// you can reuse the bitmap if you want
        page.setReuseBitmap(true);


        new PdfDocument.Builder(this).addPage(page).orientation(PdfDocument.A4_MODE.LANDSCAPE)
                .progressMessage(R.string.gen_pdf_file).progressTitle(R.string.gen_please_wait)
                .renderWidth(1505).renderHeight(2000)
                .saveDirectory(this.getExternalFilesDir(null))
                .filename("Bill" +mPrefs.getCaptainBillPrintDetails())
                .listener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);

                     }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                    }
                }).create().createPdf(this);


        firebaseDatabase.getReference("tables").child(tableKey).child("status").setValue("0");
        firebaseDatabase.getReference("booked").child(tableKey).removeValue();
    }
}
