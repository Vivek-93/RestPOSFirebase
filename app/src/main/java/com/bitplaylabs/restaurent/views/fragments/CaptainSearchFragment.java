package com.bitplaylabs.restaurent.views.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.adapters.CaptainSearchAdapter;
import com.bitplaylabs.restaurent.adapters.MenuCategoryAdapter;
import com.bitplaylabs.restaurent.extra.MenuList;
import com.bitplaylabs.restaurent.extra.SearchItemModel;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.views.activities.TableDetailsActivity;
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
public class CaptainSearchFragment extends Fragment implements View.OnClickListener {


    private Context mContext;
    private TableDetailsActivity mTableDetailsActivity;
    private FragmentManager mFragmentManager;
    public AutoCompleteTextView mSearchEt;
    public ImageView mSearchIv;
    public RecyclerView mCaptionSearchRv;
    public Button mBookItems;

    public String search;
    private Dialog additemsDialog;
    private Spinner itemSpinner;
    private ArrayList<String> addQuantity;
    // private SearchData mSearchData;
    private Sharedpreferences mPrefs;

    private List<String> searchList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private CaptainSearchAdapter mCaptainSearchAdapter;

    private List<SearchItemModel> searchDataList;

    public CaptainSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        View view = inflater.inflate(R.layout.fragment_caption_search, container, false);
        mPrefs = Sharedpreferences.getUserDataObj(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        mSearchEt = (AutoCompleteTextView) view.findViewById(R.id.fragment_caption_search_et);
        mSearchIv = (ImageView) view.findViewById(R.id.fragment_caption_search_iv);
        mCaptionSearchRv = (RecyclerView) view.findViewById(R.id.fragment_caption_search_rv);
        mBookItems = (Button) view.findViewById(R.id.fragment_caption_confirm);

        initilizeView();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mTableDetailsActivity = (TableDetailsActivity) getActivity();
        mFragmentManager = mTableDetailsActivity.getSupportFragmentManager();


    }

    private void initilizeView() {

        mBookItems.setOnClickListener(this);
        Log.d("CaptainSearchFragment", "item name");
        searchList = new ArrayList<>();
        searchDataList = new ArrayList<>();
        mRef = firebaseDatabase.getReference("menulist");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("CaptainSearchFragment", "Main" + dataSnapshot.getValue());
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


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Main", "" + dataSnapshot.getValue());

/*
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    entries.add(ds.getValue(LogEntry.class));
                }
*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };

        mSearchIv.setOnClickListener(this);

    }

    private void showData(DataSnapshot dataSnapshot) {

        MenuList menuListFirebase = dataSnapshot.getValue(MenuList.class);
     /*   MenuList menuList = new MenuList();
        String itemName = menuListFirebase.getItemname();
        menuList.setCategory(itemName);
        Log.d("CaptainSearchFragment", "item name" + itemName);
        searchList.add(menuList);
        mSearchEt.setText("" + dataSnapshot);*/

        MenuList menuList = new MenuList();
        String itemName = menuListFirebase.getItemname();
        searchList.add(itemName);
      /*  mCaptionSearchRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mCaptionSearchRv.setLayoutManager(layoutManager);
        mCaptainSearchAdapter = new CaptainSearchAdapter(getContext(), searchList);
        mCaptionSearchRv.setAdapter(mCaptainSearchAdapter);
*/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, searchList);

        mSearchEt.setThreshold(0);
        mSearchEt.setAdapter(arrayAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_caption_search_iv:
                searchFunction();
                break;

            case R.id.fragment_caption_confirm:

                mRef = firebaseDatabase.getReference("tables");
                mRef.child(mPrefs.getTableKey()).child("booked").setValue(searchDataList);

                break;
        }
    }

    private void searchFunction() {

        search = mSearchEt.getText().toString();
        additemsDialog = new Dialog(getContext());
        additemsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        additemsDialog.setContentView(R.layout.item_table_detail_item_quantity_bill);
        additemsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        additemsDialog.getWindow().setGravity(Gravity.CENTER);
        additemsDialog.show();

        TextView itemQuality = (TextView) additemsDialog.findViewById(R.id.item_table_details_quantity_tv);
        Button addBucket = (Button) additemsDialog.findViewById(R.id.item_table_details_quantity_button);
        itemSpinner = (Spinner) additemsDialog.findViewById(R.id.item_table_details_quantity_spinner);

        itemQuality.setText(search);
        addQuantity = new ArrayList<String>();
        addQuantity.add("" + 1);
        addQuantity.add("" + 2);
        addQuantity.add("" + 3);
        addQuantity.add("" + 4);
        addQuantity.add("" + 5);
        addQuantity.add("" + 6);
        addQuantity.add("" + 7);
        addQuantity.add("" + 8);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, addQuantity);
        itemSpinner.setAdapter(adapter);
        addBucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  for (int i = 0; i < searchDataList.size(); i++) {
                    searchItemm = searchDataList.get(i).toString();

                }*/
                SearchItemModel searchItemm = new SearchItemModel();
                searchItemm.setSearchItem(search);
                searchItemm.setItemQuantity(Integer.parseInt(itemSpinner.getSelectedItem().toString()));
                searchDataList.add(searchItemm);

                mCaptionSearchRv.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                mCaptionSearchRv.setLayoutManager(layoutManager);
                mCaptainSearchAdapter = new CaptainSearchAdapter(getContext(), searchDataList);
                mCaptionSearchRv.setAdapter(mCaptainSearchAdapter);
                mSearchEt.setText("");
                additemsDialog.dismiss();
            }
        });


    }

}
