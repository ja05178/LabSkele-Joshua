package com.example.labskeletest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.labskeletest.Favorites;

public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                Favorites favorite = new Favorites();
                return favorite;
            case 1:
                Map map = new Map();
                return map;
            case 2:
                List list = new List();
                return list;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
