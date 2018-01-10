package com.bitplaylabs.restaurent.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bitplaylabs.restaurent.views.fragments.ComolidatedReportKDFragment;
import com.bitplaylabs.restaurent.views.fragments.CompletedKDFragment;
import com.bitplaylabs.restaurent.views.fragments.NewOrderKDFragment;
import com.bitplaylabs.restaurent.views.fragments.ReadyKDFragment;
import com.bitplaylabs.restaurent.views.fragments.ReportSettingKDFragment;

/**
 * Created by Vivek on 01-12-2017.
 */

public class KdHomeViewPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public KdHomeViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                NewOrderKDFragment mNewOrderKDFragment = new NewOrderKDFragment ();
                return mNewOrderKDFragment ;
            case 1:
                ReadyKDFragment mReadyKDFragment = new ReadyKDFragment ();
                return mReadyKDFragment ;

            case 2:
                CompletedKDFragment mCompletedKDFragment= new CompletedKDFragment ();
                return mCompletedKDFragment;
            case 3:
                ReportSettingKDFragment mReportSettingKDFragment= new ReportSettingKDFragment ();
                return mReportSettingKDFragment;

            case 4:
                ComolidatedReportKDFragment mComolidatedReportKDFragment= new ComolidatedReportKDFragment ();
                return mComolidatedReportKDFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
