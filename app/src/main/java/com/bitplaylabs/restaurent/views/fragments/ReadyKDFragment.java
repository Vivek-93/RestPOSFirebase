package com.bitplaylabs.restaurent.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.NewOrderKDAdapter;
import com.bitplaylabs.restaurent.adapters.ReadyKDAdapter;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadyKDFragment extends Fragment {


    public RecyclerView mRecyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private ReadyKDAdapter mReadyKDAdapter;
    private List<SearchItemModel> newReadyList;

    public ReadyKDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ready_kd, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frag_ready_order_rv );
        initializeView();
        return view;
    }

    private void initializeView() {

        newReadyList= new ArrayList<>();

        mRef = firebaseDatabase.getReference("readylist");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key= dataSnapshot.getKey();
                mRef = firebaseDatabase.getReference("readylist").child(key);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                        SearchItemModel searchItemModel = dataSnapshot.getValue(SearchItemModel.class);
                        SearchItemModel list = new SearchItemModel();
                        String itemName = searchItemModel.getSearchItem();
                        int itemQuantity = searchItemModel.getItemQuantity();
                        String tableNo = searchItemModel.getTableNo();
                        String captainName = searchItemModel.getCaptainName();
                        String time = searchItemModel.getTime();
                        list.setSearchItem(itemName);
                        list.setItemQuantity(itemQuantity);
                        list.setTableNo(tableNo);
                        list.setCaptainName(captainName);
                        list.setTime(time);
                        newReadyList.add(list);


                        mRecyclerView.setHasFixedSize(true);
                        mReadyKDAdapter = new ReadyKDAdapter(getContext(), newReadyList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mReadyKDAdapter);
                       // mNewOrderKDAdapter.notifyDataSetChanged();

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

}
