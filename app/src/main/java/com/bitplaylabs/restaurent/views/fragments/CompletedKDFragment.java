package com.bitplaylabs.restaurent.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitplaylabs.restaurent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedKDFragment extends Fragment {


    public CompletedKDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_kd, container, false);
    }

}
