package com.nearme.ui.places;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by xnorcode on 08/04/2018.
 */

public class PlacesPageAdapter extends FragmentStatePagerAdapter {


    // number of tabs
    private int mNumOfTabs;


    /**
     * Constructor
     *
     * @param fm        activity's fragment manager
     * @param numOfTabs in tab layout
     */
    public PlacesPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }


    /**
     * Get Fragment by tab
     *
     * @param position of tab
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListFragment();
            case 1:
                return new MapFragment();
            default:
                return null;
        }
    }


    /**
     * Get number of tabs
     *
     * @return number of tabs
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
