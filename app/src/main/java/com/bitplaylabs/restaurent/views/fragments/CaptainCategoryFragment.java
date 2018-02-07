package com.bitplaylabs.restaurent.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.MenuCategoryAdapter;
import com.bitplaylabs.restaurent.adapters.SubCategoryAdapter;
import com.bitplaylabs.restaurent.adapters.SubItemArrayAdapter;
import com.bitplaylabs.restaurent.extra.MenuList;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.utils.Utils;
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
import java.util.Iterator;
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
    // private List<MenuList> cogetaryList;
    private List<MenuList> subCogetaryList;
    private List<MenuList> subSubCogetaryList;
    private MenuCategoryAdapter mMenuCategoryAdapter;
    private int pos;
    List<MenuList> items;
    private SubCategoryAdapter mSubCategoryAdapter;
    private SubItemArrayAdapter mSubItemArrayAdapter;
    private List<SearchItemModel> searchDataList;
    private int position;
    String selected = null;

    List<String> myList = new ArrayList<>();

    {
        myList.add("Morning");
        myList.add("Afternoon");
        myList.add("Evening");
    }

    private String subCategorySelected = null;

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
        searchDataList = new ArrayList<>();
        cogetaryList = new ArrayList<>();
        subCogetaryList = new ArrayList<>();
        subSubCogetaryList = new ArrayList<>();
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
        settingCategoryRecyclerView();

    }

    private void settingCategoryRecyclerView() {

        mCatogeryRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mMenuCategoryAdapter = new MenuCategoryAdapter(mContext, pos, myList, new MenuCategoryAdapter.CatogeryonClick() {
            @Override
            public void onClicked(String data, int pos) {

                subCogetaryList.clear();
                selected = data;
                mRef = firebaseDatabase.getReference("menulist");
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MenuList menuListFirebase = dataSnapshot.getValue(MenuList.class);
                        MenuList menuList = new MenuList();
                        String itemCategory = menuListFirebase.getCategory();
                        if (itemCategory.equalsIgnoreCase(selected)) {
                            String itemSubCategory = menuListFirebase.getSubcategory();
                            menuList.setSubcategory(itemSubCategory);
                            subCogetaryList.add(menuList);
                            Iterator<MenuList> ite = subCogetaryList.iterator();
                            while (ite.hasNext()) {
                                MenuList iteValue = ite.next();
                                if (iteValue.getSubcategory().equals(menuListFirebase.getSubcategory()))
                                    ite.remove();
                            }
                            subCogetaryList.add(menuListFirebase);


                        } else {

                        }

                        mCatogeryRecyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        mSubCategoryAdapter = new SubCategoryAdapter(getContext(), position, subCogetaryList, new SubCategoryAdapter.SubCatogeryonClick() {
                            @Override
                            public void onClicked(MenuList data, int pos) {
                                subSubCogetaryList.clear();
                                Utils.showProgress(mContext);
                                subCategorySelected = data.getSubcategory();
                                mRef = firebaseDatabase.getReference("menulist");
                                mRef.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        Utils.stopProgress(mContext);
                                        MenuList menuListFirebase = dataSnapshot.getValue(MenuList.class);
                                        MenuList menuList = new MenuList();
                                        String itemSubCategory = menuListFirebase.getSubcategory();
                                        if (itemSubCategory.equalsIgnoreCase(subCategorySelected)) {
                                            String itemName = menuListFirebase.getItemname();
                                            Long itemPrice = menuListFirebase.getPrice();
                                            String mealType = menuListFirebase.getMealtype();
                                            menuList.setItemname(itemName);
                                            menuList.setPrice(itemPrice);
                                            menuList.setMealtype(mealType);
                                            //  subSubCogetaryList.clear();
                                            subSubCogetaryList.add(menuList);
                                        } else {

                                        }
                                        mSubSubCatogeryRv.setHasFixedSize(true);
                                        mSubItemArrayAdapter = new SubItemArrayAdapter(getContext(), subSubCogetaryList, new SubItemArrayAdapter.AddCartButtonClick() {

                                            @Override
                                            public void onClicked(String itemname, int quantity, float price, String time) {

                                                searchDataList.clear();
                                                SearchItemModel searchItemm = new SearchItemModel();
                                                searchItemm.setSearchItem(itemname);
                                                searchItemm.setItemQuantity(quantity);
                                                searchItemm.setCaptainName(mPref.getLoggedInUsername());
                                                searchItemm.setTableNo(mPref.getTableKey());
                                                searchItemm.setItemPrice((long) price);
                                                searchItemm.setTime(time);
                                                searchDataList.add(searchItemm);
                                                mRef = firebaseDatabase.getReference("");
                                                mRef.child("bookedmain").child(mPref.getTableKey()).push().setValue(searchDataList);
                                                mRef.child("bookingdetails").child(mPref.getTableKey()).child(mPref.getKot()).setValue(searchDataList);

                                                mRef = firebaseDatabase.getReference("bookedmain").child(mPref.getTableKey());
                                                mRef.addChildEventListener(new ChildEventListener() {
                                                    @Override
                                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                        String key = dataSnapshot.getKey();
                                                        firebaseDatabase.getReference("booked").child(mPref.getTableKey()).child(key).setValue(searchDataList);

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
                                        });
                                        mSubSubCatogeryRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                                        mSubSubCatogeryRv.setAdapter(mSubItemArrayAdapter);


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
                        });

                        mSubCatogeryRv.setLayoutManager(mLayoutManager);
                        mSubCatogeryRv.setAdapter(mSubCategoryAdapter);

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
        });
        mCatogeryRecyclerView.setLayoutManager(mLayoutManager);
        mCatogeryRecyclerView.setAdapter(mMenuCategoryAdapter);
    }

}
