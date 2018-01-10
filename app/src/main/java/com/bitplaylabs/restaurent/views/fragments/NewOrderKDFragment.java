package com.bitplaylabs.restaurent.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.NewOrderKDAdapter;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.views.activities.KDMainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    TextView kd;

    public NewOrderKDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order_kd, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frag_kd_new_order_rv);
        kd=(TextView)view.findViewById(R.id.kd);
        initializeView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mKDMainActivity= (KDMainActivity) getActivity();
        mFragmentManager = mKDMainActivity.getSupportFragmentManager();


    }

    private void initializeView() {
        newOrderList = new ArrayList<SearchItemModel>();
        mRef = firebaseDatabase.getReference("booked");
      /*  mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                   // entries.add(ds.getValue(LogEntry.class));
                    Toast.makeText(mContext, ""+ds, Toast.LENGTH_LONG).show();
                    kd.setText(""+ds);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("NOF", "Main" + dataSnapshot.getValue());
             //   showData(dataSnapshot);
               /* for(DataSnapshot ds : dataSnapshot.getChildren()){
                    entries.add(ds.getValue(LogEntry.class));
                    Toast.makeText(mContext, ""+ds, Toast.LENGTH_LONG).show();
                    kd.setText(""+ds);
                }*/

                SearchItemModel searchItemModel=dataSnapshot.getValue(SearchItemModel.class);
                SearchItemModel list=new SearchItemModel();
                String itemName = searchItemModel.getSearchItem();
                int itemQuantity= searchItemModel.getItemQuantity();
                String tableNo= searchItemModel.getTableNo();
                String captainName= searchItemModel.getCaptainName();
                list.setSearchItem(itemName);
                list.setItemQuantity(itemQuantity);
                list.setTableNo(tableNo);
                list.setCaptainName(captainName);
                newOrderList.add(list);
                Toast.makeText(mContext, ""+dataSnapshot.getValue(), Toast.LENGTH_LONG).show();
               // kd.setText(""+itemName);

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


        settingUpRecyclerView();

    }
    private void settingUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mNewOrderKDAdapter = new NewOrderKDAdapter(getContext(),newOrderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mNewOrderKDAdapter);

    }


}
