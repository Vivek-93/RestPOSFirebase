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
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.MenuCategoryAdapter;
import com.bitplaylabs.restaurent.extra.MenuList;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.views.activities.TableDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptainCategoryFragment extends Fragment {

    public RecyclerView mCatogeryRecyclerView, mSubSubCatogeryRv, mSubCatogeryRv;

    private Context mContext;
    private TableDetailsActivity mTableDetailsActivity;
    private FragmentManager mFragmentManager;
    private Sharedpreferences mPref;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private List<MenuList> cogetaryList;
    private MenuCategoryAdapter mMenuCategoryAdapter;
    private int pos;
    List<MenuList> items;

    public CaptainCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        cogetaryList = new ArrayList<>();
        mRef = firebaseDatabase.getReference("menulist");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                showData(dataSnapshot);
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private void showData(DataSnapshot dataSnapshot) {
        MenuList menuListFirebase = dataSnapshot.getValue(MenuList.class);
        MenuList menuList = new MenuList();
        String itemCategory = menuListFirebase.getCategory();
        menuList.setCategory(itemCategory);
        cogetaryList.add(menuList);
        mCatogeryRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mMenuCategoryAdapter=new MenuCategoryAdapter(mContext, pos, cogetaryList, new MenuCategoryAdapter.CatogeryonClick() {
            @Override
            public void onClicked(MenuList data, int pos) {
                Toast.makeText(mContext, "position"+pos, Toast.LENGTH_SHORT).show();

            }
        });
        mCatogeryRecyclerView.setLayoutManager(mLayoutManager);
        mCatogeryRecyclerView.setAdapter(mMenuCategoryAdapter);
    }

}
