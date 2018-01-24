package com.bitplaylabs.restaurent.views.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.KDMainAdapter;
import com.bitplaylabs.restaurent.adapters.NewOrderKDAdapter;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.services.ReadyOrder;
import com.bitplaylabs.restaurent.views.activities.KDMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderKDFragment extends Fragment {


    private Context mContext;
    private KDMainActivity mKDMainActivity;
    private FragmentManager mFragmentManager;
    public RecyclerView mRecyclerView;
    private NewOrderKDAdapter mNewOrderKDAdapter;
    private List<SearchItemModel> newOrderList;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private List<TableDetails> data;
    private static int SPLASH_TIME_OUT = 3000;
    private Dialog kdDialougeBox;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    static String LoggedIN_User_Email;

    public NewOrderKDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order_kd, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frag_kd_new_order_rv);
        // kd = (TextView) view.findViewById(R.id.kd);
        initializeView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mKDMainActivity = (KDMainActivity) getActivity();
        mFragmentManager = mKDMainActivity.getSupportFragmentManager();


    }

    private void initializeView() {

        LoggedIN_User_Email = user.getEmail();
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
// This schedule a runnable task every  seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                data = new ArrayList<>();
                newOrderList = new ArrayList<SearchItemModel>();

                mRef = firebaseDatabase.getReference("tables");
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        TableDetails value = dataSnapshot.getValue(TableDetails.class);
                        TableDetails fire = new TableDetails();
                        String id = value.getTableid();
                        String tablename = value.getTablename();
                        String key = dataSnapshot.getKey().toString();
                        fire.setTableid(id);
                        fire.setTablename(tablename);
                        fire.setTablekey(key);
                        data.add(fire);

                        mRef = firebaseDatabase.getReference("booked").child("" + key);
                        mRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                SearchItemModel searchItemModel = dataSnapshot.getValue(SearchItemModel.class);
                                SearchItemModel list = new SearchItemModel();
                                String itemName = searchItemModel.getSearchItem();
                                int itemQuantity = searchItemModel.getItemQuantity();
                                String tableNo = searchItemModel.getTableNo();
                                String captainName = searchItemModel.getCaptainName();
                                list.setSearchItem(itemName);
                                list.setItemQuantity(itemQuantity);
                                list.setTableNo(tableNo);
                                list.setCaptainName(captainName);
                                newOrderList.add(list);

                                mRecyclerView.setHasFixedSize(true);
                                mNewOrderKDAdapter = new NewOrderKDAdapter(getContext(), newOrderList, new NewOrderKDAdapter.ReadyClick() {
                                    @Override
                                    public void onClicked(int position) {

                                        kdDialougeBox = new Dialog(mContext);
                                        kdDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        kdDialougeBox.setContentView(R.layout.item_meal_ready);
                                        kdDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        kdDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                        kdDialougeBox.show();

                                        TextView mCancelTV = (TextView) kdDialougeBox.findViewById(R.id.meal_ready_cancel_tv);
                                        TextView mReadyTV = (TextView) kdDialougeBox.findViewById(R.id.meal_ready_tv);

                                        mReadyTV.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                Intent service = new Intent(mContext, ReadyOrder.class);
                                                mContext.startService(service);

                                                // itemReadyPushNotification();
                                                kdDialougeBox.dismiss();
                                            }
                                        });

                                        mCancelTV.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                kdDialougeBox.dismiss();
                                            }
                                        });
                                    }
                                });
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mNewOrderKDAdapter);
                                mNewOrderKDAdapter.notifyDataSetChanged();

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
        }, 0, 3, TimeUnit.SECONDS);

    }

    private void itemReadyPushNotification() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                int SDK_INT = Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;
                    if (LoggedIN_User_Email.equalsIgnoreCase("" + LoggedIN_User_Email)) {
                        send_email = "prince@gmail.com";
                    } else {
                        send_email = "prince@gmail.com";
                    }

                    try {
                        String jsonResponse;
                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(false);
                        con.setDoInput(false);

                        con.setRequestProperty("Content-Type", "application/json: charset-UTF-8");
                        con.setRequestProperty("Authorization", "Basic MjlmNjRkMWQtM2RmYS00N2IwLTgwODAtMjhlYWM5MjNiMDg5");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\":\"8298bc49-6338-4e36-986a-fcddd9cb141d\","
                                + "\"filters\":[{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\":\"=\",\"value\": \"" + send_email + "\"}],"
                                + "\"data\": {\"foo\" : \"bar\"},"
                                + "\"contents\": {\"en\": \"English Message\"}"
                                + "}";

                        System.out.println("strJsonBody\n" + strJsonBody);
                        byte[] sendbytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendbytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendbytes);

                        int httpresponse = con.getResponseCode();
                        System.out.println("httpresponse\n" + httpresponse);

                        if (httpresponse >= HttpsURLConnection.HTTP_OK && httpresponse < HttpsURLConnection.HTTP_BAD_REQUEST) {

                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse\n" + jsonResponse);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        });


    }


}
