package com.bitplaylabs.restaurent.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.views.activities.TableDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptionCatogeryFragment extends Fragment {

    public RecyclerView mCatogeryRecyclerView, mSubSubCatogeryRv, mSubCatogeryRv;

    private Context mContext;
    private TableDetailsActivity mTableDetailsActivity;
    private FragmentManager mFragmentManager;
    private Sharedpreferences mPref;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    public CaptionCatogeryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_caption_catogery, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mCatogeryRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_caption_catogery_rv);
        mSubSubCatogeryRv = (RecyclerView) view.findViewById(R.id.fragment_caption_sub_sub_catogery_rv);
        mSubCatogeryRv = (RecyclerView) view.findViewById(R.id.fragment_caption_sub_catogery_rv);

        initializeView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mTableDetailsActivity = (TableDetailsActivity) getActivity();
        mFragmentManager = mTableDetailsActivity.getSupportFragmentManager();

    }

    private void initializeView() {
        mPref = Sharedpreferences.getUserDataObj(getActivity());

        mRef = firebaseDatabase.getReference("menulist");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {

        Log.d("CaptainCatogery",dataSnapshot.getKey());
    }


}
